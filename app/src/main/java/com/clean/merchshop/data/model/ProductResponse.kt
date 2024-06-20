package com.clean.merchshop.data.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductResponse(
    @SerializedName("products")
    val inventoryProducts: List<InventoryProduct>
)

@JsonClass(generateAdapter = true)
data class InventoryProduct(
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Double
)