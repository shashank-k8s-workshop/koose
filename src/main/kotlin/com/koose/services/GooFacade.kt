package com.koose.services

import retrofit2.Call
import retrofit2.http.GET

interface GooFacade {

    @GET("goo")
    fun goo(): Call<GooResponse>
}