package com.clean.merchshop.domain.use_case

import com.clean.merchshop.data.model.ShoppingCartProduct
import com.clean.merchshop.domain.repositories.ProductsRepositoryInterface
import javax.inject.Inject

class AddProductToShoppingCartUseCase @Inject constructor(
    private val repository: ProductsRepositoryInterface
) {
    operator fun invoke(shoppingCartProduct: ShoppingCartProduct){
        return repository.addProductToShoppingCart(shoppingCartProduct)
    }
}