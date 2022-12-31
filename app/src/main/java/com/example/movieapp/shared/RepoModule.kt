package com.example.movieapp.shared

import com.example.movieapp.movieslist.model.repo.MovieListRepo
import com.example.movieapp.movieslist.model.repo.MovieListReposing
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun getMoviesRepo(
        repo: MovieListRepo
    ): MovieListReposing

}
