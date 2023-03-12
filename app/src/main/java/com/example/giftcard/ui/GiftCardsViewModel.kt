package com.example.giftcard.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giftcard.data.GiftCard
import com.example.giftcard.repository.GiftCardRepository
import kotlinx.coroutines.launch

class GiftCardsViewModel : ViewModel() {

    private val _state = mutableStateOf(GiftCardsState())
    val state: State<GiftCardsState> = _state
    // The internal MutableLiveData Data that stores the most recent data
    private val _result = MutableLiveData<List<GiftCard>>()

    init {
        fetchGiftCards()
    }

    private fun fetchGiftCards() {
        viewModelScope.launch {
            try {
                GiftCardRepository.getGiftCards()
                _result.value = GiftCardRepository.giftCardList
                _state.value = GiftCardsState(giftCards = GiftCardRepository.giftCardList)
            }catch (e:Exception){
                Log.e(TAG, "Failed to get gift cards", e)
            }
        }
    }
}

data class GiftCardsState(
    val giftCards: List<GiftCard> = emptyList()
)
