package com.clean.merchshop.di

import com.clean.merchshop.data.repository.ProductsRepositoryImpl
import com.clean.merchshop.domain.repositories.ProductsRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindProductsRepository(impl: ProductsRepositoryImpl): ProductsRepositoryInterface
}