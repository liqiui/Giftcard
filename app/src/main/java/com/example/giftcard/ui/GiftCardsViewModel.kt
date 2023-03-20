package com.example.giftcard.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giftcard.data.GiftCard
import com.example.giftcard.repository.GiftCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class GiftCardsViewModel @Inject constructor(private val giftCardRepository: GiftCardRepository) : ViewModel() {
    private val _state = mutableStateOf(GiftCardsState())
    val state: State<GiftCardsState> = _state
    // The internal MutableLiveData Data that stores the most recent data
    private val _result = MutableLiveData<List<GiftCard>>()
    init {
        loadGiftCards()
    }

    private fun loadGiftCards() {
        viewModelScope.launch {
            _state.value = GiftCardsState(isLoading = true)

            try {
                val giftCards = giftCardRepository.getGiftCards()
                _state.value = GiftCardsState(giftCards = giftCards)
            } catch (e: IOException) {
                _state.value = GiftCardsState(error = "Failed to fetch gift cards")
            }
        }
    }
}
data class GiftCardsState(
    val giftCards: List<GiftCard> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
