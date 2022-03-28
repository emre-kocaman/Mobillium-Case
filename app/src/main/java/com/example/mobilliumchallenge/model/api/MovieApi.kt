package com.example.mobilliumchallenge.model.api

import com.example.mobilliumchallenge.model.entities.details.MovieDetailsResponse
import com.example.mobilliumchallenge.model.entities.nowplaying.NowPlayingMoviesResponse
import com.example.mobilliumchallenge.model.entities.upcoming.UpcomingResponse
import com.example.mobilliumchallenge.utils.Routes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET(Routes.Movie.getUpcomingMovies)
    fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("api_key") api_key: String
    ): Call<UpcomingResponse>

    @GET(Routes.Movie.getNowPlayingMovies)
    fun getNowPlayingMovies(
        @Query("page") page: Int,
        @Query("api_key") api_key: String
    ): Call<NowPlayingMoviesResponse>

    @GET(Routes.Movie.getMovieDetails)
    fun getMovieDetails(
        @Query("api_key") api_key: String,
        @Path("movie_id") movie_id: Int
    ): Call<MovieDetailsResponse>

}