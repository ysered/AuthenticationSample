package com.ysered.authenticationsample.sdk

interface TransmitApi {

    fun authenticatorsList(onListResult: OnListResult<AuthenticatorInfo>)

    fun authenticate(password: String, onResult: OnResult)

    fun authenticate(useFingerPrint: Boolean, onResult: OnResult)
}
