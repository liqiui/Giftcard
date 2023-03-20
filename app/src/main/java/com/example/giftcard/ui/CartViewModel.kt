package com.example.giftcard.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giftcard.repository.CartItem
import com.example.giftcard.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<CartItem>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<CartItem>>> = _uiState

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val items = cartRepository.getItems()
                if (items.isEmpty()) {
                    _uiState.value = UiState.Empty
                } else {
                    _uiState.value = UiState.Success(items)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun removeItem(item: CartItem) {
        viewModelScope.launch {
            try {
                cartRepository.removeItem(item)
                val items = cartRepository.getItems()
                if (items.isEmpty()) {
                    _uiState.value = UiState.Empty
                } else {
                    _uiState.value = UiState.Success(items)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            try {
                cartRepository.clear()
                _uiState.value = UiState.Empty
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    sealed class UiState<out T> {
        data class Success<out T>(val data: T) : UiState<T>()
        object Empty : UiState<Nothing>()
        data class Error(val message: String) : UiState<Nothing>()
        object Loading : UiState<Nothing>()
    }
}


