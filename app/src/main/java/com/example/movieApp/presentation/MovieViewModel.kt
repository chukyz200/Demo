package com.example.movieApp.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.movieApp.core.Resource
import com.example.movieApp.data.model.MovieList
import com.example.movieApp.domain.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MovieViewModel @ViewModelInject constructor(
    private val repo: MovieRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
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