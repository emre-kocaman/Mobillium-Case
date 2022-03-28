package com.example.mobilliumchallenge.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobilliumchallenge.model.api.MovieApi
import com.example.mobilliumchallenge.model.entities.details.MovieDetailsResponse
import com.example.mobilliumchallenge.model.entities.upcoming.UpcomingResponse
import com.example.mobilliumchallenge.utils.Resource
import com.example.mobilliumchallenge.utils.Status
import com.example.mobilliumchallenge.utils.extensions.makeApiCall
import com.example.talentbase_android.utils.Listener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
@SuppressLint("StaticFieldLeak")
class MovieDetailViewModel @Inject constructor(private val movieApi: MovieApi): ViewModel() {

    private val _movieDetails = MutableLiveData<Resource<MovieDetailsResponse>>()
    val movieDetails: LiveData<Resource<MovieDetailsResponse>> get() = _movieDetails


    fun getMovieDetails(
        movieId: Int,
        api_key: String
    ) {
        _movieDetails.postValue(Resource(Status.LOADING,null,null))
        val call = movieApi.getMovieDetails(movieId, api_key)
        call.makeApiCall(object : Listener<Resource<MovieDetailsResponse>> {
            override fun onDone(t: Resource<MovieDetailsResponse>) {
                _movieDetails.postValue(t)
            }
        })
    }
}