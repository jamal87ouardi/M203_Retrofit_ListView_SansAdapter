package com.example.m203_retrofit_listview_wthoutadapter

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

// Interface Retrofit pour les appels API
interface ApiService {
    @GET("SmartphoneAPI/readAll.php")
    fun getSmartphones(): Call<List<Smartphone>>

    @POST("SmartphoneAPI/create.php")
    fun addSmartphone(@Body smartphone: Smartphone): Call<AddResponse>
}