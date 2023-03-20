package com.example.giftcard.repository

import javax.inject.Inject
import javax.inject.Singleton

data class CartItem(
    val id: String,
    val price: Double,
    val currency: String,
    var quantity: Int,
    val image: String = "",
)

@Singleton
class CartRepository @Inject constructor() {

    private val items: MutableList<CartItem> = mutableListOf()

    fun addItem(item: CartItem) {
        // Check if the item already exists in the cart and update its quantity
        val existingItem = items.find { it.id == item.id && it.price == item.price }
        if (existingItem != null) {
            existingItem.quantity += item.quantity
        } else {
            items.add(item)
        }
    }

    fun getItems(): List<CartItem> {
        return items.toList()
    }

    fun removeItem(item: CartItem) {
        items.remove(item)
    }

    fun clear() {
        items.clear()
    }
}