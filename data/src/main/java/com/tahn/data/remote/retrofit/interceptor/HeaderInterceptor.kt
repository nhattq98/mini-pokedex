package com.tahn.data.remote.retrofit.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

internal class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        val modifiedRequest =
            originalRequest
                .newBuilder()
                .header("Content-Type", "application/json")
                .method(originalRequest.method, originalRequest.body)

        return chain.proceed(modifiedRequest.build())
    }
}
