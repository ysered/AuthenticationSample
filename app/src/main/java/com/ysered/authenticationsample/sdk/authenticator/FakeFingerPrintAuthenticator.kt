package com.ysered.authenticationsample.sdk.authenticator

import com.ysered.authenticationsample.sdk.OnResult

class FakeFingerPrintAuthenticator : FingerprintAuthenticator {

    override val displayName: String?
        get() = "Fingerprint"

    override val displayDescription: String?
        get() = "Touch fingerprint sensor to authenticate"

    override fun authenticate(useFingerPrint: Boolean, onResult: OnResult) {
        if (useFingerPrint) {
            onResult.onPositive()
        } else {
            onResult.onError("Cannot authenticate")
        }
    }
}
