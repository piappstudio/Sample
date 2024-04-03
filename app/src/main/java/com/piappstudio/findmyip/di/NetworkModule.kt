package com.piappstudio.findmyip.di

import com.google.gson.GsonBuilder
import com.piappstudio.findmyip.network.IPApi
import retrofit2.converter.gson.GsonConverterFactory
import dagger.Provides
import retrofit2.Retrofit

object NetworkModule {

    const val BASE_URL = "https://ipapi.co/"
    @Provides
    fun provideRetrofit():Retrofit {
       return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(
            GsonBuilder().setLenient().create())).build()
    }

    @Provides
    fun provideIpApi(retrofit: Retrofit):IPApi {
        return retrofit.create(IPApi::class.java)
    }
}