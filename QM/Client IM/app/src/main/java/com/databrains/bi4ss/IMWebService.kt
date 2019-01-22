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
        const val BASE_URL_IP = "http://192.168.43.90/"
        private const val BASE_URL = "$BASE_URL_IP/"
        private const val SUBSCRIBE = "QM/Server/clients"

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    }

    @FormUrlEncoded
    @POST(SUBSCRIBE)
    fun registerClient(@Field(value = "society") society: String,
                       @Field(value = "civility") civility: String,
                       @Field(value = "fName") fName: String,
                       @Field(value = "lName") lName: String,
                       @Field(value = "address") address: String,
                       @Field(value = "postal") postal: String,
                       @Field(value = "city") city: String,
                       @Field(value = "country") country: String,
                       @Field(value = "phone") phone: String,
                       @Field(value = "mobile") mobile: String,
                       @Field(value = "fax") fax: String,
                       @Field(value = "email") email: String,
                       @Field(value = "type") type: String,
                       @Field(value = "livreSameAddress") sameAddress: Boolean,
                       @Field(value = "exmeptTVA") exmeptTVA: Boolean,
                       @Field(value = "facSameAddress") facSameAddress: Boolean,
                       @Field(value = "observation") observation: String): Call<Response>
}