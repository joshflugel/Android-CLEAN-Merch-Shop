package com.clean.merchshop.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
// import com.clean.merchshop.ARG_PARAM1
// import com.clean.merchshop.ARG_PARAM2
import com.clean.merchshop.R
import com.clean.merchshop.databinding.FragmentShoppingCartBinding

class ShoppingCartFragment : Fragment(R.layout.fragment_shopping_cart) {

    private lateinit var binding: FragmentShoppingCartBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentShoppingCartBinding.bind(view)
        binding.shoppingCartFragmentToolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShoppingCartFragment().apply {
                arguments = Bundle().apply {
                    // putString(ARG_PARAM1, param1)
                    // putString(ARG_PARAM2, param2)
                }
            }
    }
}