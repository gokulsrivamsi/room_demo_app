package com.vr.room_demo_app.network_calls

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIClient {
    private var retrofit: Retrofit? = null

    fun getInstance(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://projects.propublica.org/nonprofits/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient())
                .build()
        }
        return retrofit ?: Retrofit.Builder()
            .baseUrl("https://projects.propublica.org/nonprofits/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }


    private fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okhttpClientBuilder = OkHttpClient.Builder()
        val timeOutSec = 45
        okhttpClientBuilder.connectTimeout(timeOutSec.toLong(), TimeUnit.SECONDS)
        okhttpClientBuilder.readTimeout(timeOutSec.toLong(), TimeUnit.SECONDS)
        okhttpClientBuilder.writeTimeout(timeOutSec.toLong(), TimeUnit.SECONDS)
        okhttpClientBuilder.addInterceptor(loggingInterceptor)


        return okhttpClientBuilder.build()
    }

}