package com.koose.services

import com.koose.utils.OkHttpXrayInterceptor
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Service
class GooService(@Value("\${service.goose.url:http://localhost:8080}") val gooseUrl : String) {

    private lateinit var gooFacade: GooFacade

    init {
        val client = OkHttpClient.Builder()
                .addInterceptor(OkHttpXrayInterceptor("Koose-Goose-Egress"))
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(gooseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        gooFacade = retrofit.create(GooFacade::class.java)
    }

    fun goo(): GooResponse? {
        val gooResponse = gooFacade.goo().execute()
        val body = gooResponse.body()
        return if(gooResponse.isSuccessful && body != null){
            body
        }else{
            null
        }
    }
}