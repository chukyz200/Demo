package com.example.movieApp.domain

import com.example.movieApp.application.AppConstants
import com.example.movieApp.application.AppConstants.API_KEY_VALUE
import com.example.movieApp.data.model.MovieList
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query(API_KEY_VALUE) apiKey: String): MovieList

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query(API_KEY_VALUE) apiKey: String): MovieList

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query(API_KEY_VALUE) apiKey: String): MovieList
}