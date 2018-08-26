package com.ysered.authenticationsample.sdk

import com.ysered.authenticationsample.sdk.authenticator.Authenticator

/**
 * Callback get list result for asynchronous request.
 */
interface OnListResult {
    /**
     * Called when request was successful.
     */
    fun onPositive(result: List<Authenticator>)

    /**
     * Called when request was unsuccessful.
     */
    fun onError(message: String)
}
