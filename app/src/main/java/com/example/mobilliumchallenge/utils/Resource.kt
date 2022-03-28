package com.example.mobilliumchallenge.utils

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Resource<T>(var status: Status, val data: T?, val message: String?){

    companion object {
        fun <T> success(data: T?,message:String?): Resource<T> {
            return Resource(Status.SUCCESS, data, message)
        }

        fun <T> error(msg: String): Resource<T> {
            return Resource(Status.ERROR, null, msg)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    DEF
}
