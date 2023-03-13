package com.example.giftcard.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giftcard.data.Denominations
import com.example.giftcard.data.GiftCard
import com.example.giftcard.repository.CartItem
import com.example.giftcard.repository.CartRepository
import com.example.giftcard.repository.GiftCardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GiftCardDetailViewModel(private val giftCardId: String) : ViewModel() {

    private val _state = mutableStateOf(GiftCardDetailViewState(giftCard= null, denominations = emptyList()))
    val state: State<GiftCardDetailViewState> = _state
    lateinit var giftCard: GiftCard

    val data = GiftCardRepository.giftCardList
    init {
        loadGiftCard()
    }

    fun getGiftCardById(): GiftCard {
        return data.find { it.id == giftCardId }
            ?: throw IllegalArgumentException("Gift card with id $giftCardId not found.")
    }

    private fun loadGiftCard() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    // code to load gift card with the given id
                    giftCard = getGiftCardById()

                    val denominations = giftCard.denominations
                    val newState = state.value.copy(
                        giftCard = giftCard,
                        denominations = denominations,
                        selectedDenomination = denominations.firstOrNull()?.let { 0 }
                    )
                    _state.value = newState
//                    _data.postValue(giftCard)
                }
            } catch (e: Exception) {
                // handle the exception
            }
        }
    }

    fun addToCart(giftCard: GiftCard, selectedDenominationIndex: Int) {
        val denomination = giftCard.denominations[selectedDenominationIndex]
        val cartItem = CartItem(id = giftCard.id, price = denomination.price,
            currency = denomination.currency, quantity = 1,giftCard.image)
        CartRepository.addItem(cartItem)
    }
}

data class GiftCardDetailViewState(
    val giftCard: GiftCard?,
    val selectedDenomination: Int? = null,
    val denominations: List<Denominations>,
    val isLoading: Boolean = false,
    val error: String = ""
)

