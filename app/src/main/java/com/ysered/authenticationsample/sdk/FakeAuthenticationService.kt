package com.ysered.authenticationsample.sdk

import com.ysered.authenticationsample.sdk.authenticator.Authenticator
import com.ysered.authenticationsample.sdk.authenticator.FakeFingerPrintAuthenticator
import com.ysered.authenticationsample.sdk.authenticator.FakePasswordAuthenticator
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

class FakeAuthenticationService : AuthenticationService {

    companion object {
        const val RESPONSE_TIME_MS = 3000L
    }

    private var listJob: Job? = null

    override fun authenticatorsList(onListResult: OnListResult) {
        if (!inProgress) {
            listJob = launch(CommonPool) {
                delay(RESPONSE_TIME_MS)
                var authList = emptyList<Authenticator>()
                authList += FakePasswordAuthenticator()
                authList += FakeFingerPrintAuthenticator()
                onListResult.onPositive(authList)
            }
        }
    }

    val inProgress: Boolean
        get() = listJob?.isActive ?: false

    fun cancel() {
        if (inProgress) {
            listJob?.cancel()
        }
    }
}