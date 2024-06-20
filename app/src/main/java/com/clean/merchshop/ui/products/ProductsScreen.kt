package com.clean.merchshop.ui.products


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clean.merchshop.R
import com.clean.merchshop.domain.model.Product
import com.clean.merchshop.ui.Screen
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.runtime.*
import androidx.navigation.NavController

@Composable
fun ProductsScreen(
    onItemClicked: () -> Unit,
    viewModel: ProductsViewModel = hiltViewModel(),
    navController: NavController
){
    val state = viewModel.state
    val eventFlow = viewModel.eventFlow
    val scaffoldSate = rememberScaffoldState()
    val navigateToShoppingCart by viewModel.navigateToShoppingCart.collectAsState()


    LaunchedEffect(navigateToShoppingCart) {
        if (navigateToShoppingCart) {
            navController.navigate(Screen.ShoppingCart.route)
            viewModel.onNavigatedToShoppingCart()
        }
    }

    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when(event) {
                is ProductsViewModel.UIEvent.ShowSnackBar -> {
                    scaffoldSate.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldSate,
        topBar = {
            ProductsTopBar()
        },
        bottomBar = {
            ProductsBottomBar(
                onShoppingCartPressed = { viewModel.gotoShoppingCart() }
            )
        }
    ){  innerPadding ->
        ProductsContent(
            modifier = Modifier.padding(innerPadding),
            isLoading = state.isLoading,
            products = state.products ?: emptyList(),
            onBuyClicked = { product, quantity ->
                viewModel.addProductToShoppingCart(product, quantity)
            }
        )
    }
}

@Composable
private fun ProductsTopBar(
    modifier: Modifier = Modifier
) {
    TopAppBar (
        title = {
            Text(
                text = stringResource(id = R.string.products_title),
                textAlign =  TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        },
        backgroundColor =  MaterialTheme.colors.surface
    )
}

@Composable
private fun ProductsContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    products: List<Product> = emptyList(),
    onBuyClicked: (Product, Int) -> Unit
) {

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.surface
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 6.dp),
            modifier = Modifier.fillMaxWidth(),
            content = {
                items(products.size) { index ->
                    ProductItem(
                        modifier = Modifier.fillMaxWidth(),
                        item = products.get(index),
                        onItemClicked = {},
                        onBuyClicked = { product, quantity ->
                            onBuyClicked(product, quantity)
                        }
                    )
                }
            }
        )
        if (isLoading) FullScreenLoading()
    }
}

@Composable
private fun ProductsBottomBar(
    onShoppingCartPressed: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                enabled = true,
                onClick = onShoppingCartPressed,
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.DarkGray
                )
            ) {
                Text(text = stringResource(id = R.string.shopping_cart))
            }
        }
    }
}

@Composable
private fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}