package com.example.giftcard.repository

import com.example.giftcard.data.GiftCard
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private val BASE_URL = "https://zip.co/giftcards/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface GiftCardApi {
    @GET("giftcards")
    suspend fun getGiftCards(): Response<List<GiftCard>>
}

object Api {
    val retrofitService: GiftCardApi by lazy {
        retrofit.create(GiftCardApi::class.java)
    }
}
