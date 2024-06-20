package com.clean.merchshop.ui.shoppingcart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clean.merchshop.domain.use_case.GetShoppingCartProductsUseCase
import com.clean.merchshop.domain.use_case.GetShoppingCartTotalUseCase
import com.clean.merchshop.domain.use_case.UpdateQuantityResult
import com.clean.merchshop.domain.use_case.UpdateShoppingCartProductQuantityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ShoppingCartViewModel @Inject constructor(
    private val getShoppingCartProductsUseCase: GetShoppingCartProductsUseCase,
    private val getShoppingCartTotalUseCase: GetShoppingCartTotalUseCase,
    private val updateShoppingCartProductQuantityUseCase: UpdateShoppingCartProductQuantityUseCase
): ViewModel() {

    private val _state = MutableStateFlow(ShoppingCartState())
    val state: StateFlow<ShoppingCartState> get() = _state.asStateFlow()

    private val  _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    // Navigation event
    private val _navigateToProducts = MutableStateFlow(false)
    val navigateToProducts: StateFlow<Boolean> get() = _navigateToProducts


    init {
        fetchShoppingCartProducts()
        fetchShoppingCartTotal()
    }

    fun fetchShoppingCartProducts() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            getShoppingCartProductsUseCase()
                .catch { exception ->
                    _state.value = _state.value.copy(isLoading = false)
                    _eventFlow.emit(UIEvent.ShowSnackBar(exception.message ?: "Unknown Error"))
                }
                .collect { products ->
                    _state.value = ShoppingCartState(
                        shoppingCartProducts = products,
                        isLoading = false
                    )
                }
        }
    }

    private fun fetchShoppingCartTotal() {
        viewModelScope.launch {
            getShoppingCartTotalUseCase()
                .catch { exception ->
                    println("Some Error in ViewModel.fetchCartTotal: ${exception.message}")
                }
                .collect { total ->
                    _state.value = _state.value.copy(total = total)
                }
        }
    }

    fun updateProductQuantityRemoveIfZero(code: String, quantity: Int) {
        viewModelScope.launch {
            val result = updateShoppingCartProductQuantityUseCase(code, quantity)
            when (result) {
                is UpdateQuantityResult.Success -> {
                    // Handle success if needed
                }
                is UpdateQuantityResult.Error -> {
                    _eventFlow.emit(UIEvent.ShowSnackBar(result.message))
                }
            }
        }
    }

    fun gotoProducts() {
        _navigateToProducts.value = true
    }

    fun onNavigatedToProducts() {
        _navigateToProducts.value = false
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String): UIEvent()
    }
}