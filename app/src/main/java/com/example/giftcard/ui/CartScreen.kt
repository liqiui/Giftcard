package com.example.giftcard.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.giftcard.repository.CartItem

@Composable
fun CartScreen(cartViewModel: CartViewModel, navController: NavHostController) {
    val cartItemsState = remember { mutableStateOf(cartViewModel.getCartItems()) }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(cartItemsState.value) { cartItem ->
                CartItemRow(cartItem, cartViewModel, cartItemsState)
                Divider()
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .height(50.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    navController.navigate("confirmation")
                },
                modifier = Modifier.weight(1f),
                enabled = cartItemsState.value.isNotEmpty()
            ) {
                Text(text = "Checkout")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.weight(1f),
            ) {
                Text(text = "Continue Shopping")
            }
        }
    }
}

@Composable
fun CartItemRow(cartItem: CartItem, cartViewModel: CartViewModel, cartItemsState: MutableState<List<CartItem>>) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(cartItem.image),
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = cartItem.id,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${cartItem.price} ${cartItem.currency}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Quantity: ${cartItem.quantity}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }

            IconButton(
                onClick = {
                    cartViewModel.removeItem(cartItem)
                    cartItemsState.value = cartViewModel.getCartItems()
                },
                modifier = Modifier.padding(start = 16.dp, end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove item"
                )
            }
        }
    }
}
