package com.example.movieApp.di

import android.content.Context
import androidx.room.Room
import com.example.movieApp.application.AppConstants
import com.example.movieApp.data.local.AppDatabase
import com.example.movieApp.domain.MovieRepository
import com.example.movieApp.domain.MovieRepositoryImpl
import com.example.movieApp.domain.WebService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideRoomInstance(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "movie_table"
    ).build()

    @Singleton
    @Provides
    fun provideMovieDao(db: AppDatabase) = db.movieDao()

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    @Singleton
    @Provides
    fun provideWebService(retrofit: Retrofit): WebService = retrofit.create(WebService::class.java)


    @Singleton
    @Provides
    fun provideRepository(repositoryImpl: MovieRepositoryImpl): MovieRepository = repositoryImpl
}