package com.clean.merchshop.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

sealed class Screen(val route: String) {
    object Products: Screen("products")
    object ShoppingCart: Screen("shoppingcart")
}

class MerchShopActions(navController: NavController) {
    val navigateToProducts: () -> Unit = {
         navController.navigate(Screen.Products.route) {
             // prevent stack creation of several Home(Products) destinations, for example
             popUpTo(navController.graph.findStartDestination().id) {
                 saveState = true
             }
             launchSingleTop = true
             restoreState = true
         }
    }

    val navigateToShoppingCart: () -> Unit = {
        navController.navigate(Screen.ShoppingCart.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }
}