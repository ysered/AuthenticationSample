package com.ysered.authenticationsample

interface AuthResultCallback {

    fun onPasswordAuthSucceeded()

    fun onPasswordAuthFailed()

    fun onFingerprintAuthFailed()
}