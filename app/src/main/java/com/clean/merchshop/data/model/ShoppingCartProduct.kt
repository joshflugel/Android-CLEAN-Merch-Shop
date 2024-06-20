package com.clean.merchshop.data.model

import com.clean.merchshop.domain.model.Product
import java.util.Locale

class ShoppingCartProduct (
    val inventoryProduct: Product,
    var quantity: Int = 0,
) {
    val code: String
        get() = inventoryProduct.code

    val name: String
        get() = inventoryProduct.name

    var price: Double = 0.00
        get() = inventoryProduct.price
        set(value) {
            field = String.format(Locale.US,"%.2f", value).toDouble()
        }

    var discount: Double = 0.00
        set(value) {
            field = String.format(Locale.US,"%.2f", value).toDouble()
        }

    var subtotal: Double = 0.00
        get() = String.format(Locale.US,"%.2f", price * quantity - discount).toDouble()
}
