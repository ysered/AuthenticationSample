package com.ysered.authenticationsample.sdk.authenticator

import com.ysered.authenticationsample.sdk.OnResult

/**
 * Provides API to authenticate user by password.
 */
interface PasswordAuthenticator : Authenticator {
    /**
     * Performs password authentication asynchronously.
     * @param password password plain string
     * @param onResult callback to get result of authentication when request is finished
     */
    fun authenticate(password: String, onResult: OnResult)
}
