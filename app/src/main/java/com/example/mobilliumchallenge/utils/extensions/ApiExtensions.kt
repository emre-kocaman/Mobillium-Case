package com.example.mobilliumchallenge.utils.extensions

import com.example.mobilliumchallenge.utils.Resource
import com.example.mobilliumchallenge.utils.Status
import com.example.talentbase_android.utils.Listener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T> Call<T>.makeApiCall(listener: Listener<Resource<T>>?) {
    this.enqueue(object : Callback<T> {
        override fun onResponse(
            call: Call<T>,
            response: Response<T>?
        ) {
            response?.let {
                listener?.onDone(response.handleResponse())
            } ?: run {
                listener?.onDone(Resource.error("Beklenmedik hata"))
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            listener?.onDone(Resource(Status.ERROR, null, t.localizedMessage))
        }
    })
}

fun <T> Call<T>.makeApiCall(): Resource<T> {
    return this.execute().handleResponse()
}


fun <T> Response<T>.handleResponse(): Resource<T> {

    if (this.isSuccessful) {
        val data = this.body()
        return Resource.success(data, "Successful")
    } else {

        return when {
            //We can handle all error codes here
            this.code() == 408 -> {
                Resource.error("Timeout")
            }
            this.code() == 402 -> {
                Resource.error("Payment Required")
            }
            this.code() == 400 -> {
                Resource.error("Bad Request")
            }
            else -> {
                Resource.error("Unexpected error")
            }
        }
    }
}



