package com.ysered.authenticationsample.sdk.authenticator

import com.ysered.authenticationsample.sdk.OnResult

/**
 * Provides API to authenticate user by fingerprint.
 */
interface FingerprintAuthenticator: Authenticator {
    /**
     * Performs authentication asynchronously.
     * @param useFingerPrint indicates whether fingerprint should be used
     * @param onResult callback to get result of authentication when request is finished
     */
    fun authenticate(useFingerPrint: Boolean, onResult: OnResult)
}
