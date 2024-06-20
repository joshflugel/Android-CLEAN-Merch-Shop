package com.clean.merchshop.domain.use_case

import com.clean.merchshop.data.model.ShoppingCartProduct
import com.clean.merchshop.domain.repositories.ProductsRepositoryInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShoppingCartProductsUseCase @Inject constructor(
    private val repository: ProductsRepositoryInterface
) {
    operator fun invoke(): Flow<List<ShoppingCartProduct>> {
        return repository.getShoppingCartProducts()
    }
}