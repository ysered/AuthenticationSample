package com.ysered.authenticationsample.sdk

/**
 * Provides API to interact with authentication service.
 */
interface AuthenticationService {
    /**
     * Retrieve list of available authenticators asynchronously.
     * @param onListResult callback to get result or error when request is finished
     */
    fun authenticatorsList(onListResult: OnListResult)
}
