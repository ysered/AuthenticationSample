package com.ysered.authenticationsample.sdk

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch


class FakeTransmitApi : TransmitApi {

    private var authListJob: Job? = null
    private var passwordAuthJob: Job? = null
    private var fingerprintAuthJob: Job? = null

    override fun authenticatorsList(onListResult: OnListResult<AuthenticatorInfo>) {
        val inProgress = authListJob?.isActive ?: false
        if (inProgress) return
        authListJob = launch(CommonPool) {
            delay(5_000)
            val authList = listOf(AuthenticatorInfo.Password(), AuthenticatorInfo.Fingerprint())
            onListResult.onPositive(authList)
        }
    }

    override fun authenticate(password: String, onResult: OnResult) {
        val inProgress = passwordAuthJob?.isActive ?: false
        if (inProgress) return
        passwordAuthJob = launch(CommonPool) {
            delay(3_000)
            if (password == "123456") {
                onResult.onPositive()
            } else {
                onResult.onError("You entered invalid password")
            }
        }
    }

    override fun authenticate(useFingerPrint: Boolean, onResult: OnResult) {
        val inProgress = fingerprintAuthJob?.isActive ?: false
        if (inProgress) return
        fingerprintAuthJob = launch(CommonPool) {
            delay(3_000)
            if (useFingerPrint) {
                onResult.onPositive()
            } else {
                onResult.onError("Authentication error")
            }
        }
    }

    fun cancelAll() {
        authListJob?.cancel()

    }
}
