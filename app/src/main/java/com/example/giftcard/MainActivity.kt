package com.example.giftcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.giftcard.ui.CartScreen
import com.example.giftcard.ui.ConfirmationScreen
import com.example.giftcard.ui.GiftCardDetailScreen
import com.example.giftcard.ui.GiftCardsList
import com.example.giftcard.ui.login.ScreenLogin
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "login") {
                composable("login") {
                    ScreenLogin(onSubmit = {
                        navController.navigate("GiftCardsList")
                    }, application = application)
                }
                composable("GiftCardsList") {
                        GiftCardsList(
                            onGiftCardSelected = {  },
                            navController = navController

                        )
                }
                composable("giftCardDetail/{id}") { backStackEntry ->
                    backStackEntry.arguments?.getString("id")?.let { giftCardId ->
                        // Fetch gift card details using giftCardId
                        GiftCardDetailScreen(giftCardId =giftCardId,
                            navController = navController)
                    }
                }
                composable("confirmation") {
                    ConfirmationScreen()
                }
                composable("cart") {
                    CartScreen( navController =  navController) }
            }
        }
    }
}

