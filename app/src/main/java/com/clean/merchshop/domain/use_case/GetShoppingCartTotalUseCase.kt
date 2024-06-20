package com.clean.merchshop.domain.use_case

import com.clean.merchshop.domain.repositories.ProductsRepositoryInterface
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetShoppingCartTotalUseCase @Inject constructor(
    private val repository: ProductsRepositoryInterface,
    private val calculateDiscountsUseCase: CalculateDiscountsUseCase
) {
    operator fun invoke(): StateFlow<Double> {
        calculateDiscountsUseCase()
        return repository.getShoppingCartTotal()
    }
}