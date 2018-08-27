package com.ysered.authenticationsample.sdk


class FakeTransmitApi : TransmitApi {

    private val manager = AuthenticatorManager

    override fun authenticatorsList(onListResult: OnListResult<Authenticator>) {
        manager.fetchAuthenticators(onListResult)
    }

    override fun authenticate(password: String, onResult: OnResult) {
        manager.authenticateByPassword(password, onResult)
    }

    override fun authenticate(useFingerPrint: Boolean, onResult: OnResult) {
        manager.authenticateByFingerprint(useFingerPrint, onResult)
    }

    fun cancelAll() {
        manager.cancelAll()
    }
}
