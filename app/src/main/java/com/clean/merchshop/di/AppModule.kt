package com.clean.merchshop.di

import com.clean.merchshop.data.source.local.ShoppingCartDataSource
import com.clean.merchshop.data.source.remote.ProductsApiService
import com.clean.merchshop.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) //provides lifecycle scope: app-wide
object  AppModule {

    @Singleton
    @Provides
    fun injectProductsApiService(): ProductsApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ProductsApiService::class.java)
    }

    @Singleton
    @Provides
    fun injectShoppingCartDataSource(): ShoppingCartDataSource {
        return ShoppingCartDataSource
    }

}