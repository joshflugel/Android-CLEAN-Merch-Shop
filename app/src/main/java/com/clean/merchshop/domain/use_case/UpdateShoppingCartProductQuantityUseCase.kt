package com.clean.merchshop.domain.use_case

import com.clean.merchshop.domain.repositories.ProductsRepositoryInterface
import javax.inject.Inject

sealed class UpdateQuantityResult {
    object Success : UpdateQuantityResult()
    data class Error(val message: String) : UpdateQuantityResult()
}

class UpdateShoppingCartProductQuantityUseCase @Inject constructor(
    private val repository: ProductsRepositoryInterface,
    private val calculateDiscountsUseCase: CalculateDiscountsUseCase
) {
    operator fun invoke(shoppingCartProductCode: String, quantity: Int): UpdateQuantityResult {
        return try {
            if (quantity == 0) {
                repository.removeProduct(shoppingCartProductCode)
            } else {
                repository.updateProductQuantity(shoppingCartProductCode, quantity)
            }
            calculateDiscountsUseCase()
            UpdateQuantityResult.Success
        } catch (e: Exception) {
            UpdateQuantityResult.Error(e.message ?: "Unknown Error")
        }
    }
}
