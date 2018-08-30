package com.ysered.authenticationsample

sealed class Result<T> {

    class InProgress<T>: Result<T>()

    class Success<T>(val payload: T) : Result<T>()

    class Error<T>(val message: String) : Result<T>()
}
