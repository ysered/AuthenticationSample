package com.ysered.authenticationsample.sdk

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch


class FingerprintAuthenticator : Authenticator() {

    override val title: String
        get() = "Fingerprint"

    override val description: String
        get() = "Touch fingerprint sensor to authenticate"

    override fun authenticate(request: AuthenticatorRequest, onResult: OnResult) {
        authJob = launch(CommonPool) {
            delay(3_000)
            if (request.useFingerPrint) {
                onResult.onPositive()
            } else {
                onResult.onError("Cannot authenticate")
            }
        }
    }
}
