package com.koose.services

import com.koose.controllers.AMAZON_TRACE_ID
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Header


@Service
class GooService(@Value("\${service.goose.url:http://localhost:8080}") val gooseUrl : String) {

    private var gooFacade: GooFacade

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(gooseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        gooFacade = retrofit.create(GooFacade::class.java)
    }

    fun goo(@Header(AMAZON_TRACE_ID) traceId: String?): GooResponse? {
        val gooResponse = gooFacade.goo().execute()
        val body = gooResponse.body()
        return if(gooResponse.isSuccessful && body != null){
            body
        }else{
            null
        }
    }
}