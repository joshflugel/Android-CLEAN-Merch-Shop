package com.clean.merchshop.domain.use_case

import com.clean.merchshop.data.ResultMerchShop
import com.clean.merchshop.domain.model.Product
import com.clean.merchshop.domain.repositories.ProductsRepositoryInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsInventoryUseCase @Inject constructor(
    private val repository: ProductsRepositoryInterface
) {
    operator fun invoke(): Flow<ResultMerchShop<List<Product>>> {
        return repository.getProducts()
    }
}


