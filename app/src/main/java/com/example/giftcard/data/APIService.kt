package com.example.giftcard.repository

import com.example.giftcard.data.GiftCard
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Inject


interface GiftCardApi {
    @GET("giftcards")
    suspend fun getGiftCards(): Response<List<GiftCard>>
}

class ApiService @Inject constructor(private val giftCardApi: GiftCardApi) {
    suspend fun getGiftCards(): Response<List<GiftCard>> = giftCardApi.getGiftCards()

    suspend fun getGiftCardById(id: String): GiftCard? {
        val response = getGiftCards()
        if (response.isSuccessful) {
            val giftCards = response.body() ?: emptyList()
            return giftCards.find { it.id == id }
        }
        return null
    }
}