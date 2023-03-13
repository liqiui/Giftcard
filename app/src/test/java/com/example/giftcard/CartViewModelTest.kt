package com.example.giftcard

import com.example.giftcard.repository.CartItem
import com.example.giftcard.repository.CartRepository
import com.example.giftcard.ui.CartViewModel
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class CartViewModelTest {

    private lateinit var viewModel: CartViewModel

    @Before
    fun setUp() {
        viewModel = CartViewModel()

        // Mock CartRepository
        mockkObject(CartRepository)
    }

    @Test
    fun `getCartItems should return the list of items from the repository`() {
        // Arrange
        val items = listOf(
            CartItem("1", 50.0, "AUD", 1),
            CartItem("2", 100.0, "AUD", 2),
            CartItem("3", 200.0, "AUD", 1),
        )
        every { CartRepository.getItems() } returns items

        // Act
        val result = viewModel.getCartItems()

        // Assert
        assert(result == items)
    }

    @Test
    fun `removeItem should call the removeItem method on the repository with the given item`() {
        // Arrange
        val item = CartItem("1", 50.0, "USD", 1)

        // Act
        viewModel.removeItem(item)

        // Assert
        verify { CartRepository.removeItem(item) }
    }

    @Test
    fun `clearCart should call the clear method on the repository`() {
        // Act
        viewModel.clearCart()

        // Assert
        verify { CartRepository.clear() }
    }
}
