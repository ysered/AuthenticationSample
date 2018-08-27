package com.ysered.authenticationsample.sdk

sealed class AuthenticatorInfo(val title: String, val description: String) {

    class Password(var password: String = "") : AuthenticatorInfo(
            title = "Password",
            description = "User password to authenticate"
    )

    class Fingerprint(var useFingerprint: Boolean = false) : AuthenticatorInfo(
            title = "Fingerprint",
            description = "User fingerprint sensor to authenticate"
    )
}
