package com.ysered.authenticationsample.sdk

sealed class AuthenticatorInfo(
        val title: String,
        val description: String,
        var isFailed: Boolean = false
) {

    class Password(var password: String = "") : AuthenticatorInfo(
            title = "Password",
            description = "Use password to authenticate"
    )

    class Fingerprint(var useFingerprint: Boolean = false) : AuthenticatorInfo(
            title = "Fingerprint",
            description = "Use fingerprint sensor to authenticate"
    )
}
