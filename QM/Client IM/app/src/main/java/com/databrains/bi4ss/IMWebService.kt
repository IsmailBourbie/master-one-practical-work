package com.databrains.bi4ss

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface IMWebService {

    companion object {
        const val BASE_URL_IP = "http://192.168.1.39/"
        private const val BASE_URL = "$BASE_URL_IP/"

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}