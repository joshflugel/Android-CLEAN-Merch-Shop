package com.clean.merchshop.ui.products

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clean.merchshop.data.ResultMerchShop
import com.clean.merchshop.data.model.ShoppingCartProduct
import com.clean.merchshop.domain.model.Product
import com.clean.merchshop.domain.use_case.AddProductToShoppingCartUseCase
import com.clean.merchshop.domain.use_case.GetProductsInventoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsInventoryUseCase: GetProductsInventoryUseCase,
    private val addProductToShoppingCartUseCase: AddProductToShoppingCartUseCase
): ViewModel() {

    var state by mutableStateOf(ProductsState(isLoading = true))
        private set

    private val _products = MutableStateFlow<ResultMerchShop<List<Product>>>(ResultMerchShop.Loading())
    val products: StateFlow<ResultMerchShop<List<Product>>?> get() = _products

    private val  _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    
    private val _navigateToShoppingCart = MutableStateFlow(false)
    val navigateToShoppingCart: StateFlow<Boolean> get() = _navigateToShoppingCart

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            getProductsInventoryUseCase()
                .catch { exception ->
                    state = state.copy(isLoading = false)
                    _products.value = ResultMerchShop.Error(exception.message.toString())
                }
                .collect { result ->
                    //_products.value = result
                    when(result) {
                        is ResultMerchShop.Success -> {
                            state = state.copy(
                                products = result.data,
                                isLoading = false
                            )
                            _products.value = result
                        }
                        is ResultMerchShop.Error -> {
                            state = state.copy(isLoading = false)
                            _eventFlow.emit(
                                UIEvent.ShowSnackBar(
                                    result.message ?: "Unknown Error"
                                )
                            )
                        }
                        is ResultMerchShop.Loading -> {
                                state = state.copy(isLoading = true)
                            }
                    }
                }
        }
    }

    fun addProductToShoppingCart(product: Product, quantity: Int) {
        viewModelScope.launch {
            addProductToShoppingCartUseCase(ShoppingCartProduct(product, quantity))
            _eventFlow.emit(UIEvent.ShowSnackBar("${product.name} added to cart"))
        }
    }

    fun gotoShoppingCart() {
        _navigateToShoppingCart.value = true
    }

    fun onNavigatedToShoppingCart() {
        _navigateToShoppingCart.value = false
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String): UIEvent()
    }
}