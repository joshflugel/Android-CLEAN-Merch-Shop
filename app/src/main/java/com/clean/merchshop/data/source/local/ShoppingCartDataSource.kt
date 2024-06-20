package com.clean.merchshop.data.source.local

import com.clean.merchshop.data.model.ShoppingCartProduct
import com.clean.merchshop.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object ShoppingCartDataSource {

    private val _shoppingCart = MutableStateFlow<List<ShoppingCartProduct>>(emptyList())
    val shoppingCart: StateFlow<List<ShoppingCartProduct>> get() = _shoppingCart

    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> get() = _total

    fun printItems() {
        val cartItems = shoppingCart.value
        if (cartItems.isEmpty()) {
            println("FLUGEL - The shopping cart is empty.")
        } else {
            println("FLUGEL - Shopping Cart Items:")
            cartItems.forEach { item ->
                println("Product Code: ${item.code}, Name: ${item.name}, Quantity: ${item.quantity}, Price: ${item.price}")
            }
        }
    }

    fun addProduct(shoppingCartProduct: ShoppingCartProduct) {
        addProduct(shoppingCartProduct.inventoryProduct, shoppingCartProduct.quantity)
    }

    fun addProduct(product: Product, quantity: Int) {
        val currentCart = _shoppingCart.value.toMutableList()
        val existingProduct = currentCart.find { it.code == product.code }

        if (existingProduct != null) {
            existingProduct.quantity += quantity
        } else {
            currentCart.add(ShoppingCartProduct(product, quantity))
        }

        _shoppingCart.value = currentCart
        calculateTotal()
    }

    fun removeProduct(productCode: String) {
        val currentCart = _shoppingCart.value.toMutableList()
        currentCart.removeAll { it.code == productCode }
        _shoppingCart.value = currentCart
        calculateTotal()
    }

    fun updateProductQuantity(productCode: String, quantity: Int) {
        val currentCart = _shoppingCart.value.toMutableList()
        val existingProduct = currentCart.find { it.code == productCode }

        if (existingProduct != null) {
            if (quantity > 0) {
                existingProduct.quantity = quantity
            } else {
                currentCart.remove(existingProduct)
            }
            _shoppingCart.value = currentCart
            calculateTotal()
        }
    }

    fun getProductQuantity(productCode: String): Int {
        val existingProduct = _shoppingCart.value.find { it.code == productCode }
        return existingProduct?.quantity ?: 0
    }

    fun getProductPrice(productCode: String): Double {
        val existingProduct = _shoppingCart.value.find { it.code == productCode }
        return existingProduct?.price ?: 0.0
    }

    fun getAllProducts(): Flow<List<ShoppingCartProduct>> {
        printItems()
        return shoppingCart
    }

    fun setDiscountForProduct(productCode: String, discount: Double) {
        val currentCart = _shoppingCart.value.toMutableList()
        val existingProduct = currentCart.find { it.code == productCode }

        if (existingProduct != null) {
            existingProduct.discount = discount
            _shoppingCart.value = currentCart
        } else {
            println("FLUGEL - Product with code $productCode not found in the cart.")
        }
    }

    fun calculateTotal() {
        val currentCart = _shoppingCart.value
        var total = 0.00
        for (item in currentCart) {
            total += item.subtotal
        }
        _total.value = total
        println("FLUGEL - TOTAL $total")
    }
}

