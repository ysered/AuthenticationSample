package com.ysered.authenticationsample.sdk

/**
 * Callback interface to return result for asynchronous request.
 */
interface OnResult {
    /**
     * Called when request was finished successfully.
     */
    fun onPositive()

    /**
     * Called when request was unsuccessful.
     */
    fun onError(message: String)
}
