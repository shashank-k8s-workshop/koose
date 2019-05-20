package com.koose.utils

import com.amazonaws.xray.AWSXRay
import com.amazonaws.xray.entities.Namespace
import com.amazonaws.xray.entities.Subsegment
import com.amazonaws.xray.exceptions.SegmentNotFoundException
import okhttp3.Interceptor
import okhttp3.Response

class OkHttpXrayInterceptor(val segmentName: String): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return try {
            val recorder = AWSXRay.getGlobalRecorder()

            val segment: Subsegment = recorder.beginSubsegment(segmentName).apply {
                namespace = Namespace.REMOTE.toString()
                putHttp("request", mapOf("url" to request.url().toString(), "method" to request.method()))
            }

            val response = chain.proceed(request)

            segment.apply {
                isError = response.code() / 100 == 4
                isFault = response.code() / 100 == 5
                isThrottle = response.code() == 429
                putHttp("response", mapOf("status" to response.code(), "content_length" to response.body()?.contentLength()))
            }

            recorder.endSubsegment()
            response
        } catch (e: SegmentNotFoundException) {
            println("Warning: Segment not found for tracing")
            chain.proceed(request)
        }
    }
}