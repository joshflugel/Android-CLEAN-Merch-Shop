package com.clean.merchshop.data.source.remote

import com.clean.merchshop.domain.model.Product

data class ProductDTO(
    val code: String,
    val name: String,
    val price: Double
)

fun ProductDTO.toProduct(): Product {
    return Product(
        code = code,
        name = name,
        price = price
    )
}