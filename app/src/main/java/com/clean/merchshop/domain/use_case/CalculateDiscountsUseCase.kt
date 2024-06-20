package com.clean.merchshop.domain.use_case

import javax.inject.Inject

class CalculateDiscountsUseCase @Inject constructor(
    private val calculateDiscountBulkUseCase: CalculateDiscountBulkUseCase,
    private val calculateDiscount2For1UseCase: CalculateDiscount2for1UseCase
) {
    operator fun invoke() {
        calculateDiscountBulkUseCase()
        calculateDiscount2For1UseCase()
    }
}

