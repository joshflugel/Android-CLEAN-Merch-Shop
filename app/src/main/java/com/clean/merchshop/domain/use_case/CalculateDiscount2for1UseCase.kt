package com.clean.merchshop.domain.use_case

import com.clean.merchshop.domain.repositories.ProductsRepositoryInterface
import javax.inject.Inject

class CalculateDiscount2for1UseCase @Inject constructor(
    private val repository: ProductsRepositoryInterface
) {
    operator fun invoke() {
        val productCode = "VYNIL"
        val numberOfItems = repository.getProductQuantity(productCode)
        val applicableItems:Int = numberOfItems/2
        repository.setDiscountForProduct(productCode, applicableItems * repository.getProductPrice(productCode))
    }
}

