package com.clean.merchshop.data.source.remote

import com.clean.merchshop.domain.model.Product

data class ProductsDTO(
    val products: List<ProductDTO>
)

fun ProductsDTO.toListProducts(): List<Product> {
    val productEntries = products.mapIndexed { _, entries ->
        Product(
            code = entries.code,
            name = entries.name,
            price = entries.price
        )
    }
    return productEntries
}