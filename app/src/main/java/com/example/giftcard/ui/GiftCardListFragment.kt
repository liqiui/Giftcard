package com.example.giftcard.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.giftcard.R
import com.example.giftcard.data.GiftCard

@Composable
fun GiftCardsList(
    giftCards: List<GiftCard>,
    onGiftCardSelected: (GiftCard) -> Unit,
    navController: NavController
) {

    LazyColumn {
        items(giftCards) { giftCard ->
            GiftCardItem(
                giftCard = giftCard,
                onClick = {onGiftCardSelected(giftCard)},
                navController = navController
            )
            Divider()
        }
    }
}

@Composable
fun GiftCardItem(giftCard: GiftCard, onClick: () -> Unit,
                 navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = rememberImagePainter(
                data = giftCard.image,
                builder = {
                    placeholder(R.drawable.placeholder_image)
                    error(R.drawable.error_image)
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(Modifier.width(16.dp))
        Column {
            Text(text = giftCard.brand, fontWeight = FontWeight.Bold)
            Text(text = "${giftCard.discount}% off")
            Text(text = giftCard.vendor)
        }
        Spacer(Modifier.weight(1f))
        Button(onClick = { navController.navigate("giftCardDetail/${giftCard.id}") }) {
            Text(text = "Detail")
        }
    }
}



