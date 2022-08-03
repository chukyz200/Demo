package com.example.movieApp.data.remote

import com.example.movieApp.application.AppConstants
import com.example.movieApp.data.model.MovieList
import com.example.movieApp.domain.WebService
import javax.inject.Inject

class RemoteMovieDataSource @Inject constructor(private val webService: WebService) {

    suspend fun getUpcomingMovies(): MovieList {
        return webService.getUpcomingMovies(AppConstants.API_KEY)
    }

    suspend fun getTopRatedMovies(): MovieList {
        return webService.getTopRatedMovies(AppConstants.API_KEY)
    }

    suspend fun getPopularMovies(): MovieList {
        return webService.getPopularMovies(AppConstants.API_KEY)
    }
}