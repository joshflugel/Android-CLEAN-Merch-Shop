package com.clean.merchshop.ui.shoppingcart

import com.clean.merchshop.data.model.ShoppingCartProduct

data class ShoppingCartState(
    val shoppingCartProducts: List<ShoppingCartProduct>? = emptyList(),
    val isLoading: Boolean = false,
    val total: Double = 0.00
)


