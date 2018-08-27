package com.ysered.authenticationsample.sdk

import kotlinx.coroutines.experimental.Job

abstract class Authenticator {

    protected var authJob: Job? = null

    abstract val title: String
    abstract val description: String

    abstract fun authenticate(request: AuthenticatorRequest, onResult: OnResult)

    val inProgress: Boolean
        get() = authJob?.isActive ?: false

    fun cancel() {
        if (inProgress) {
            authJob?.cancel()
        }
    }
}
