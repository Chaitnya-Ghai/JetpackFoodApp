package com.example.jetpackfoodapp

sealed class SealedClass<out T> {
    data class Success<out T>(val data: T? = null) : SealedClass<T>()
    data class Loading(val nothing: Nothing? = null) : SealedClass<Nothing>()
    data class Error(val mesg: String? = null) : SealedClass<Nothing>()
}



