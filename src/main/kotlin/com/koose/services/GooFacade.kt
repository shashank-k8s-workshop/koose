package com.koose.services

import com.koose.controllers.AMAZON_REQ_ID
import com.koose.controllers.AMAZON_TRACE_ID
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface GooFacade {

    @GET("goo")
    fun goo(@Header(AMAZON_TRACE_ID) traceId: String?, @Header(AMAZON_REQ_ID) reqId: String?): Call<GooResponse>
}