package com.tahn.data.remote.retrofit.helper

import com.tahn.data.BuildConfig
import com.tahn.data.remote.retrofit.PokemonApiServices
import com.tahn.data.remote.retrofit.interceptor.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal object NetworkBuilder {
    fun buildOkkHttpClient(
        headerInterceptor: HeaderInterceptor,
        isDebug: Boolean,
    ): OkHttpClient {
        val loggerInterceptor = HttpLoggingInterceptor()
        if (isDebug) {
            loggerInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            loggerInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        return OkHttpClient
            .Builder()
            .addInterceptor(loggerInterceptor)
            .addInterceptor(headerInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    fun buildService(okHttpClient: OkHttpClient): PokemonApiServices =
        Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonApiServices::class.java)
}
