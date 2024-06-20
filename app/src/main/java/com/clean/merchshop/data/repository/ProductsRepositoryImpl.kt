package com.clean.merchshop.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.clean.merchshop.data.ResultMerchShop
import com.clean.merchshop.data.source.remote.ProductsApiService
import com.clean.merchshop.domain.repositories.ProductsRepositoryInterface
import com.clean.merchshop.data.model.ShoppingCartProduct
import com.clean.merchshop.data.source.local.ShoppingCartDataSource
import com.clean.merchshop.data.source.remote.toListProducts
import com.clean.merchshop.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject


class ProductsRepositoryImpl @Inject constructor(
    private var shoppingCartDataSource: ShoppingCartDataSource,
    private val productApiService: ProductsApiService
): ProductsRepositoryInterface {

    override fun getShoppingCartProducts(): Flow<List<ShoppingCartProduct>> {
        shoppingCartDataSource.printItems()
        return shoppingCartDataSource.getAllProducts()
    }

    override fun addProductToShoppingCart(shoppingCartProduct: ShoppingCartProduct) {
        shoppingCartDataSource.addProduct(shoppingCartProduct)
    }

    override fun getShoppingCartTotal(): StateFlow<Double> {
        return shoppingCartDataSource.total
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun getProducts(): Flow<ResultMerchShop<List<Product>>> = flow{
        emit(ResultMerchShop.Loading())
        try {
            val response = productApiService.getProductsInventory().toListProducts()
            emit(ResultMerchShop.Success(response))
        } catch(e: HttpException) {
            emit(ResultMerchShop.Error(
                message = "Something blew up - HttpException",
                data = null
            ))
        } catch(e: IOException) {
            emit(ResultMerchShop.Error(
                message = "The server is Unreachable - IOException",
                data = null
            ))
        }
    }

    override fun updateProductQuantity(productCode: String, quantity: Int) {
        shoppingCartDataSource.updateProductQuantity(productCode, quantity)
    }

    override fun getProductQuantity(itemCode: String): Int {
        return shoppingCartDataSource.getProductQuantity(itemCode)
    }

    override fun getProductPrice(itemCode: String): Double {
        return shoppingCartDataSource.getProductPrice(itemCode)
    }

    override fun setDiscountForProduct(productCode: String, discount: Double) {
        shoppingCartDataSource.setDiscountForProduct(productCode, discount)
    }

    override fun removeProduct(productCode: String) {
        shoppingCartDataSource.removeProduct(productCode)
    }

}