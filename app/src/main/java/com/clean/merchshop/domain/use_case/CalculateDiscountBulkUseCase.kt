package com.clean.merchshop.domain.use_case

import com.clean.merchshop.domain.repositories.ProductsRepositoryInterface
import javax.inject.Inject

class CalculateDiscountBulkUseCase @Inject constructor(
    private val repository: ProductsRepositoryInterface
) {
    operator fun invoke(): Double {
        val itemCode = "TSHIRT"
        var itemCount = repository.getProductQuantity(itemCode)
        val discountPerItem = 1.00
        val discount = if (itemCount >= 3) {
            itemCount * discountPerItem
        } else {
            0.00
        }
        repository.setDiscountForProduct(itemCode, discount)

        return discount
    }
}