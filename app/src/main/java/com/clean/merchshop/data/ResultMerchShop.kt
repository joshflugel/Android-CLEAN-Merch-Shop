package com.clean.merchshop.data

sealed class ResultMerchShop<T> (
    val data : T? = null,
    val message : String? = null
){
    class Success<T>(data: T) : ResultMerchShop<T>(data)
    class Loading<T>(data : T? = null) : ResultMerchShop<T>(data)
    class Error<T>(message : String, data: T? = null) : ResultMerchShop<T>(data,message)
}