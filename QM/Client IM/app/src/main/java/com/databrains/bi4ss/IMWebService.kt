package com.databrains.bi4ss

import com.databrains.bi4ss.models.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IMWebService {

    companion object {
        const val BASE_URL_IP = "http://192.168.1.39/"
        private const val BASE_URL = "$BASE_URL_IP/"

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    }

    @FormUrlEncoded
    @POST
    fun registerClient(@Field(value = "")society: String,
                       @Field(value = "")civility: String,
                       @Field(value = "")fName: String,
                       @Field(value = "")lName: String,
                       @Field(value = "")address: String,
                       @Field(value = "")postal: String,
                       @Field(value = "")country: String,
                       @Field(value = "")phone: String,
                       @Field(value = "")mobile: String,
                       @Field(value = "")fax: String,
                       @Field(value = "")email: String,
                       @Field(value = "")type: String): Call<Response>
}