package com.example.mobilliumchallenge.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobilliumchallenge.model.api.MovieApi
import com.example.mobilliumchallenge.model.entities.nowplaying.NowPlayingMoviesResponse
import com.example.mobilliumchallenge.model.entities.upcoming.UpcomingResponse
import com.example.mobilliumchallenge.utils.Resource
import com.example.mobilliumchallenge.utils.Status
import com.example.mobilliumchallenge.utils.extensions.makeApiCall
import com.example.talentbase_android.utils.Listener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
@SuppressLint("StaticFieldLeak")
class HomeFragmentViewModel @Inject constructor(private val movieApi: MovieApi): ViewModel()  {
    private var _upComingMovies = MutableLiveData<Resource<UpcomingResponse>>()
    val upComingMovies:LiveData<Resource<UpcomingResponse>> get() = _upComingMovies

    private var _nowPlayingMovies = MutableLiveData<Resource<NowPlayingMoviesResponse>>()
    val nowPlayingMovies:LiveData<Resource<NowPlayingMoviesResponse>> get() = _nowPlayingMovies

    fun getUpcomingMovies(
        page: Int,
        api_key: String
    ) {
        val call = movieApi.getUpcomingMovies(page, api_key)
        call.makeApiCall(object : Listener<Resource<UpcomingResponse>>{
            override fun onDone(t: Resource<UpcomingResponse>) {
                _upComingMovies.postValue(t)
            }
        })
    }

    fun getNowPlayingMovies(page:Int, api_key: String){
        val call = movieApi.getNowPlayingMovies(page, api_key)
        call.makeApiCall(object : Listener<Resource<NowPlayingMoviesResponse>>{
            override fun onDone(t: Resource<NowPlayingMoviesResponse>) {
                _nowPlayingMovies.postValue(t)
            }
        })
    }

    fun removeObserving(){
        _upComingMovies.postValue(Resource(Status.DEF,null,null))
        _nowPlayingMovies.postValue(Resource(Status.DEF,null,null))
    }

}