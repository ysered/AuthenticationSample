package com.ysered.authenticationsample.sdk.authenticator

import com.ysered.authenticationsample.sdk.OnResult
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch


class FakePasswordAuthenticator : PasswordAuthenticator {

    companion object {
        const val VALID_PASSWORD = "12345"
        const val AUTH_RESPONSE_MS = 3000L
    }

    private var authJob: Job? = null

    private val inProgress: Boolean
        get() = authJob?.isActive ?: false

    override val displayName: String?
        get() = "Password"

    override val displayDescription: String?
        get() = "Enter password to authenticate"

    override fun authenticate(password: String, onResult: OnResult) {
        if (!inProgress) {
            authJob = launch(CommonPool) {
                delay(AUTH_RESPONSE_MS)
                if (isValidPassword(password)) {
                    onResult.onPositive()
                } else {
                    onResult.onError("You entered invalid password")
                }
            }
        }
    }

    private fun isValidPassword(password: String): Boolean {
        return password == VALID_PASSWORD
    }
}
