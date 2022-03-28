package com.example.mobilliumchallenge.utils

object Routes {

    object Movie {
        private const val movieEndpoint = "movie"
        const val getUpcomingMovies = "$movieEndpoint/upcoming"
        const val getNowPlayingMovies = "$movieEndpoint/now_playing"
        const val getMovieDetails = "$movieEndpoint/{movie_id}"
    }

}