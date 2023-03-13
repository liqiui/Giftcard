package com.example.giftcard.ui

import androidx.lifecycle.ViewModel
import com.example.giftcard.repository.CartItem
import com.example.giftcard.repository.CartRepository

class CartViewModel : ViewModel() {

    fun getCartItems(): List<CartItem> {
        return CartRepository.getItems()
    }

    fun removeItem(item: CartItem) {
        return CartRepository.removeItem(item)
    }

    fun clearCart() {
        return CartRepository.clear()
    }
}
