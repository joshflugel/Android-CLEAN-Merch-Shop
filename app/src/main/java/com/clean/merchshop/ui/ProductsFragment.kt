package com.clean.merchshop.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.clean.merchshop.R
import com.clean.merchshop.databinding.FragmentProductsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductsFragment : Fragment(R.layout.fragment_products) {

    private lateinit var binding: FragmentProductsBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductsBinding.bind(view)

        val composeViewProducts = view.findViewById<ComposeView>(R.id.compose_view_products)
        composeViewProducts.setContent {
            CleanMerchShopApp()
        }
    }

    fun navigateToShoppingCartFragment() {
        val action = ProductsFragmentDirections.actionProductsToShoppingCartDest()
        findNavController().navigate(action)
    }
}