package com.clean.merchshop.ui.products

import com.clean.merchshop.domain.model.Product

data class ProductsState(
  val products: List<Product>? = emptyList(),
  val isLoading: Boolean = false
)