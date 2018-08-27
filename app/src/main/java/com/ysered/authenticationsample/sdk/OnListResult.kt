package com.ysered.authenticationsample.sdk

/**
 * Callback get list result for asynchronous request.
 */
interface OnListResult<T> {
    /**
     * Called when request was successful.
     */
    fun onPositive(result: List<T>)

    /**
     * Called when request was unsuccessful.
     */
    fun onError(message: String)
}
