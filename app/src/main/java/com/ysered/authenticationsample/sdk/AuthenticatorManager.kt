package com.ysered.authenticationsample.sdk

import com.ysered.authenticationsample.AUTH_LIST_LOAD_TIME_MS
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch


object AuthenticatorManager {

    private var fetchListJob: Job? = null
    private var passwordAuthenticator: PasswordAuthenticator? = null
    private var fingerprintAuthenticator: FingerprintAuthenticator? = null
    private var authenticatorList = emptyList<Authenticator>()

    private val inProgress: Boolean
        get() = fetchListJob?.isActive ?: false

    fun fetchAuthenticators(onListResult: OnListResult<Authenticator>) {
        if (!inProgress) {
            fetchListJob = launch(CommonPool) {
                delay(AUTH_LIST_LOAD_TIME_MS)
                passwordAuthenticator = PasswordAuthenticator()
                fingerprintAuthenticator = FingerprintAuthenticator()
                authenticatorList = listOf(passwordAuthenticator!!, fingerprintAuthenticator!!)
                onListResult.onPositive(authenticatorList)
            }
        }
    }

    fun authenticateByPassword(password: String, onResult: OnResult) {
        val request = AuthenticatorRequest(password = password)
        passwordAuthenticator?.authenticate(request, onResult)
    }

    fun authenticateByFingerprint(useFingerPrint: Boolean, onResult: OnResult) {
        val request = AuthenticatorRequest(useFingerPrint = useFingerPrint)
        fingerprintAuthenticator?.authenticate(request, onResult)
    }

    fun cancelAll() {
        fetchListJob?.cancel()
        passwordAuthenticator?.cancel()
        fingerprintAuthenticator?.cancel()
    }
}