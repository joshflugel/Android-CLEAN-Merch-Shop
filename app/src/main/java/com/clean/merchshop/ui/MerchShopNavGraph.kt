package com.clean.merchshop.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.clean.merchshop.ui.products.ProductsScreen
import com.clean.merchshop.ui.shoppingcart.ShoppingCartScreen

//@Preview
@Composable
fun MerchShopNavGraph(
  modifier: Modifier = Modifier,
  navigateToProducts: () -> Unit,
  navigateToShoppingCart: () -> Unit,
  navController: NavHostController = rememberNavController(),
  startDestination: String = Screen.Products.route
) {

    NavHost (
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.Products.route) {
            ProductsScreen(
                onItemClicked = { navigateToShoppingCart() },
                navController = navController
            )
        }
        composable(route = Screen.ShoppingCart.route) {
            ShoppingCartScreen(
                navController = navController
            )
        }
    }
}