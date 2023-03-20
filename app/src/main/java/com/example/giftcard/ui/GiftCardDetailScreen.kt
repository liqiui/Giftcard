package com.example.giftcard.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.giftcard.data.Denominations

@Composable
fun GiftCardDetailScreen(giftCardId: String, navController: NavHostController,
                         viewModel: GiftCardDetailViewModel = hiltViewModel<GiftCardDetailViewModel>(),
) {
    val selectedDenominationIndex = remember { mutableStateOf(0) }

    LaunchedEffect(giftCardId) {
        viewModel.loadGiftCard(giftCardId)
    }

    val giftCard = viewModel.state.value.giftCard
    if (viewModel.state.value.isLoading) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Image(
                    painter = rememberImagePainter(data = giftCard?.image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f)
                        .padding(horizontal = 16.dp)
                )
            }
            giftCard?.let { card ->
                item {
                    Text(
                        text = "${card.brand} - ${card.discount}% off",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth()
                    )
                }
            }
            item {
                giftCard?.terms?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth()
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            val selectedDenomination =
                giftCard?.denominations?.getOrNull(selectedDenominationIndex.value)

            if (selectedDenomination != null) {
                item {
                    Text(
                        text = "Selected denomination: ${selectedDenomination.price} ${viewModel.state.value.denominations[selectedDenominationIndex.value].currency}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
//        } else {
                // handle the case where giftCard or selectedDenomination is null
            }
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    itemsIndexed(viewModel.state.value.denominations) { index, denomination ->
                        DenominationChip(
                            denomination = denomination,
                            selected = selectedDenominationIndex.value == index,
                            onSelected = {
                                selectedDenominationIndex.value = index
                            }
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            navController.navigate("confirmation")
                        },
                        modifier = Modifier.weight(1f),
                        enabled = selectedDenominationIndex.value >= 0
                    ) {
                        Text(text = "Buy now")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            giftCard?.let {
                                viewModel.addToCart(
                                    it,
                                    selectedDenominationIndex.value
                                )
                            }
                            navController.navigate("cart")
                        },
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(text = "Add to Cart")
                    }
                }
            }
        }
    }
}


@Composable
fun DenominationChip(denomination: Denominations, selected: Boolean, onSelected: () -> Unit) {
    val cardColors = CardDefaults.cardColors(
        containerColor =
        if (selected) MaterialTheme.colorScheme.primaryContainer
        else
            MaterialTheme.colorScheme.surfaceVariant)
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = cardColors,
        modifier = Modifier
            .clickable(onClick = onSelected)
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "${denomination.price} ${denomination.currency}",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
            ),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

