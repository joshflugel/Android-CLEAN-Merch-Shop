package com.clean.merchshop.util

data class ResultB<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): ResultB<T> {
            return ResultB(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): ResultB<T> {
            return ResultB(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): ResultB<T> {
            return ResultB(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}
