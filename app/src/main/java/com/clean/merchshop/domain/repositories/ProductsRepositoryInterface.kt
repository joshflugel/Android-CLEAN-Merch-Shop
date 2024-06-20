package com.clean.merchshop.domain.repositories

import com.clean.merchshop.data.ResultMerchShop
import com.clean.merchshop.data.model.ShoppingCartProduct
import com.clean.merchshop.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ProductsRepositoryInterface {

    fun getProducts(): Flow<ResultMerchShop<List<Product>>> // Products from Domain->Model

    fun getShoppingCartProducts(): Flow<List<ShoppingCartProduct>>

    fun addProductToShoppingCart(shoppingCartProduct: ShoppingCartProduct)

    fun getShoppingCartTotal(): StateFlow<Double>

    fun updateProductQuantity(productCode: String, quantity: Int)

    fun setDiscountForProduct(productCode: String, discount: Double)

    fun removeProduct(productCode: String)

    fun getProductQuantity(itemCode: String): Int

    fun getProductPrice(itemCode: String): Double

}

