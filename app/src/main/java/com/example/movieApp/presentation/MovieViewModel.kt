package com.example.movieApp.presentation

import androidx.lifecycle.*
import com.example.movieApp.core.Resource
import com.example.movieApp.data.model.MovieList
import com.example.movieApp.domain.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repo: MovieRepository,
) : ViewModel() {


    private val _movies: MutableLiveData<Resource<Triple<MovieList, MovieList, MovieList>>> =
        MutableLiveData(Resource.Loading())
    val movies: LiveData<Resource<Triple<MovieList, MovieList, MovieList>>> = _movies

    init {
        fetchMainScreenMovies()
    }

    private fun fetchMainScreenMovies() =
        viewModelScope.launch(viewModelScope.coroutineContext + Dispatchers.IO) {
            kotlin.runCatching {
                Resource.Success(
                    Triple(
                        repo.getUpcomingMovies(),
                        repo.getTopRatedMovies(),
                        repo.getPopularMovies()
                    )
                )
            }.onSuccess {
                _movies.postValue(it)
            }.onFailure {
                _movies.postValue(Resource.Failure(Exception(it)))
            }
        }
}