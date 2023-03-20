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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GiftCardDetailViewModel @Inject constructor(
    private val giftCardRepository: GiftCardRepository,
    private val cardRepository:CartRepository) : ViewModel() {

    private val _state = mutableStateOf(GiftCardDetailViewState(giftCard = null, denominations = emptyList()))
    val state: State<GiftCardDetailViewState> = _state

    suspend fun loadGiftCard(giftCardId: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val giftCard = giftCardRepository.getGiftCardById(giftCardId)
                    if (giftCard != null) {
                        val denominations = giftCard.denominations
                        val newState = state.value.copy(
                            giftCard = giftCard,
                            denominations = denominations,
                            selectedDenomination = denominations.firstOrNull()?.let { 0 },
                             isLoading = true
                        )
                        _state.value = newState
                    } else {
                        // handle the case where the gift card with the given id is not found
                        _state.value = state.value.copy(
                            isLoading = false,
                            error = "Gift card not found"
                        )
                    }
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
        cardRepository.addItem(cartItem)
    }
}

data class GiftCardDetailViewState(
    val giftCard: GiftCard?,
    val selectedDenomination: Int? = null,
    val denominations: List<Denominations>,
    val isLoading: Boolean = false,
    val error: String = ""
)
