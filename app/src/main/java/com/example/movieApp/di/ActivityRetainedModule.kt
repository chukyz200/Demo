package com.example.movieApp.di

import com.example.movieApp.domain.MovieRepository
import com.example.movieApp.domain.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class ActivityRetainedModule {

    @Binds
    abstract fun bindDataSource(impl: MovieRepositoryImpl): MovieRepository

}