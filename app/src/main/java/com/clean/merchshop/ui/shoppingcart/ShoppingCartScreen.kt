package com.clean.merchshop.ui.shoppingcart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.clean.merchshop.R
import com.clean.merchshop.data.model.ShoppingCartProduct
import com.clean.merchshop.ui.Screen
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ShoppingCartScreen(
    viewModel: ShoppingCartViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    val eventFlow = viewModel.eventFlow
    val scaffoldSate = rememberScaffoldState()
    val navigateToProducts by viewModel.navigateToProducts.collectAsState()
    val shoppingCartTotal = state.shoppingCartProducts?.sumOf { it.subtotal } ?: 0.0

    val (selectedItem, setSelectedItem) = remember { mutableStateOf<ShoppingCartProduct?>(null) }
    val (quantity, setQuantity) = remember { mutableStateOf("0") }
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }

    LaunchedEffect(navigateToProducts) {
        if (navigateToProducts) {
            navController.navigate(Screen.Products.route)
            viewModel.onNavigatedToProducts()
        }
    }

    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is ShoppingCartViewModel.UIEvent.ShowSnackBar -> {
                    scaffoldSate.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    if (showDialog && selectedItem != null) {
        AlertDialog(
            onDismissRequest = { setShowDialog(false) },
            title = { Text(text = "Update ${selectedItem.name}") },
            text = {
                Column {
                    Text("Edit the number of items to buy, Zero to remove.:")
                    TextField(
                        value = quantity,
                        onValueChange = { newValue ->
                            setQuantity(newValue)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.updateProductQuantityRemoveIfZero(selectedItem.code, quantity.toIntOrNull() ?: 0)
                        setShowDialog(false)
                    }
                ) {
                    Text("Accept")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { setShowDialog(false) }
                ) {
                    Text("Cancel")
                }
            },
        )
    }

    Scaffold(
        scaffoldState = scaffoldSate,
        topBar = {
            ShoppingCartTopBar()
        },
        bottomBar = {
            ShoppingCartBottomBar(
                shoppingCartTotal = shoppingCartTotal,
                onProductsPressed = { viewModel.gotoProducts() }
            )
        }
    ) { innerPadding ->
        ShoppingCartContent(
            modifier = Modifier.padding(innerPadding),
            onItemClicked = {
                setSelectedItem(it)
                setQuantity(it.quantity.toString())
                setShowDialog(true)
            },
            isLoading = state.isLoading,
            shoppingCartItems = state.shoppingCartProducts ?: emptyList()
        )
    }
}



@Composable
private fun ShoppingCartTopBar(
    modifier: Modifier = Modifier
) {
    TopAppBar (
        title = {
            Text(
                text = stringResource(id = R.string.shopping_cart),
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
private fun ShoppingCartContent(
    modifier: Modifier = Modifier,
    onItemClicked: (ShoppingCartProduct) -> Unit,
    isLoading: Boolean = false,
    shoppingCartItems: List<ShoppingCartProduct> = emptyList()
) {

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.surface
    ) {
        if (isLoading) {
            FullScreenLoading()
        } else {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 6.dp),
                modifier = Modifier.fillMaxWidth(),
                content = {
                    items(shoppingCartItems.size) { index ->
                        ShoppingCartItem(
                            modifier = Modifier.fillMaxWidth(),
                            item = shoppingCartItems.get(index),
                            onItemClicked = { onItemClicked(it) }
                        )
                    }
                }
            )
        }

    }
}

@Composable
private fun ShoppingCartBottomBar(
    shoppingCartTotal: Double,
    onProductsPressed: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .wrapContentHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                style = MaterialTheme.typography.h3,
                text = "Total: €${"%.2f".format(shoppingCartTotal)}",
                textAlign = TextAlign.End,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            )
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = true,
                onClick = onProductsPressed,
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.DarkGray
                )
            ) {
                Text(text = stringResource(id = R.string.return_to_the_store))
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