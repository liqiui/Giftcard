package com.example.giftcard.repository

import com.example.giftcard.data.GiftCard
import java.io.IOException
import javax.inject.Inject

class GiftCardRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getGiftCards(): List<GiftCard> {
        val response = apiService.getGiftCards()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw IOException("Failed to fetch gift cards")
        }
    }

    suspend fun getGiftCardById(id: String): GiftCard? {
        return apiService.getGiftCardById(id)
    }
}