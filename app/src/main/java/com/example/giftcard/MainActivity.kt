package com.example.giftcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.giftcard.ui.GiftCardsList
import com.example.giftcard.ui.GiftCardsViewModel
import com.example.giftcard.ui.login.ScreenLogin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "login") {
                composable("login") {
                    ScreenLogin(onSubmit = {
                        navController.navigate("GiftCardsList")
                    })
                }
                composable("GiftCardsList") {
                    val viewModel = viewModel<GiftCardsViewModel>()
                    val state = viewModel.state
                    GiftCardsList(
                        giftCards = state.value.giftCards,
                        onGiftCardSelected = { /* TODO */ },navController

                    )
                }
            }
        }
    }
}

