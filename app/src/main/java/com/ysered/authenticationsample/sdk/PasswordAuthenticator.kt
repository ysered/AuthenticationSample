package com.ysered.authenticationsample.sdk

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch


class PasswordAuthenticator : Authenticator() {

    override val title: String
        get() = "Password"

    override val description: String
        get() = "Enter password to authenticate"

    override fun authenticate(request: AuthenticatorRequest, onResult: OnResult) {
        if (!inProgress) {
            authJob = launch(CommonPool) {
                delay(3_000)
                if (isValidPassword(request.password)) {
                    onResult.onPositive()
                } else {
                    onResult.onError("You entered invalid password")
                }
            }
        }
    }

    private fun isValidPassword(password: String): Boolean {
        return password == "12345"
    }
}
