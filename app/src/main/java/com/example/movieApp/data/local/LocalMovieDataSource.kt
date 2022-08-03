package com.example.movieApp.data.local

import com.example.movieApp.data.model.MovieEntity
import com.example.movieApp.data.model.MovieList
import com.example.movieApp.data.model.toMovieList
import javax.inject.Inject

class LocalMovieDataSource @Inject constructor(private val movieDao: MovieDao) {

    suspend fun getUpcomingMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == "upcoming" }.toMovieList()
    }

    suspend fun getTopRatedMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == "toprated" }.toMovieList()
    }

    suspend fun getPopularMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == "popular" }.toMovieList()
    }

    suspend fun saveMovie(movie: MovieEntity) {
        movieDao.saveMovie(movie)
    }
}