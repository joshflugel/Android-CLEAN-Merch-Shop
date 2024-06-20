package com.clean.merchshop.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.clean.merchshop.ui.theme.MerchShopTheme

@Composable
fun CleanMerchShopApp() {
    MerchShopTheme {
        val navController = rememberNavController()
        val navigationActions = remember(navController) {
            MerchShopActions(navController)
        }

        MerchShopNavGraph(
            navController = navController,
            navigateToProducts =  navigationActions.navigateToProducts,
            navigateToShoppingCart = navigationActions.navigateToShoppingCart
        )
    }
}