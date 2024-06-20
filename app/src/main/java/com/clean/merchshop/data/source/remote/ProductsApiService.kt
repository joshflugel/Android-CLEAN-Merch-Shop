package com.clean.merchshop.data.source.remote

import retrofit2.http.GET

interface ProductsApiService {

    @GET("joshflugel/00020a35baeca2184e4ecf857246ff94/raw/fa855514e8fc0bb959d7f69962b33cff97645979/Products.json")
    suspend fun getProductsInventory():ProductsDTO

}