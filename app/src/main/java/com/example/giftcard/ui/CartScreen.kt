package com.example.giftcard.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.giftcard.repository.CartItem

@Composable
fun CartScreen(
    cartViewModel: CartViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by cartViewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            when (uiState) {
                CartViewModel.UiState.Loading -> item { LoadingRow() }
                CartViewModel.UiState.Empty -> item { EmptyCartRow() }
                is CartViewModel.UiState.Success<*> -> {
                    val cartItems = (uiState as CartViewModel.UiState.Success<List<CartItem>>).data
                    items(cartItems) { cartItem ->
                        CartItemRow(cartItem, cartViewModel, uiState)
                        Divider()
                    }
                }
                is CartViewModel.UiState.Error -> {
                    val message = (uiState as CartViewModel.UiState.Error).message
//                    item { ErrorRow(message = message, onClick = { cartViewModel.retry() }) }
                }
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
                enabled = uiState is CartViewModel.UiState.Success<*> && (uiState as CartViewModel.UiState.Success<List<CartItem>>).data.isNotEmpty()
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
fun CartItemRow(cartItem: CartItem, cartViewModel: CartViewModel,  uiState: CartViewModel.UiState<List<CartItem>>) {
    when (uiState) {
        is CartViewModel.UiState.Success<*> ->  {
            val cartItems = (uiState as CartViewModel.UiState.Success<List<CartItem>>).data
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
        else -> {
            // Handle other UI states (Loading, Error, Empty) here
        }
    }
}

@Composable
fun LoadingRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun EmptyCartRow() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Your cart is empty!")
    }
}

//@Composable
//fun ErrorRow(errorMessage: String, onRetry: () -> Unit) {
//    Column(modifier = Modifier.fillMaxWidth()) {
//        Text(errorMessage, modifier = Modifier.padding(8.dp))
//        Button(onClick = onRetry) {
//            Text("Retry")
//        }
//    }
//}
