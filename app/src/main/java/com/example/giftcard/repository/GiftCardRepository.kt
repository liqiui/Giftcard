package com.example.giftcard.repository

import com.example.giftcard.data.GiftCard
import com.example.giftcard.data.sampleData
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.IOException

class GiftCardRepository() {
    companion object {
        lateinit var giftCardList: List<GiftCard>

//         suspend fun getGiftCards() = getDataFromJson()
        suspend fun getGiftCards() = getDataFromApi()
        private suspend fun getDataFromApi() {
            // Fetch data from network or database
            // Example network call using Retrofit and Moshi

            val response = Api.retrofitService.getGiftCards()
            if (response.isSuccessful) {
                giftCardList = response.body() ?: emptyList()
            } else {
                // Handle error
                throw IOException("Failed to fetch gift cards")
            }
        }

        //getting data from local data for testing
        private suspend fun getDataFromJson() {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val type = Types.newParameterizedType(List::class.java, GiftCard::class.java)
            val jsonAdapter = moshi.adapter<List<GiftCard>>(type)
            val sampleData = jsonAdapter.fromJson(sampleData) ?: emptyList()

            giftCardList = sampleData
        }
    }
}
