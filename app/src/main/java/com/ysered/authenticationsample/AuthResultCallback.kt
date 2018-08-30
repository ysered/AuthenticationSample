package com.ysered.authenticationsample

interface AuthResultCallback {

    fun onPasswordAuthSucceeded()

    fun onPasswordAuthRetry()

    fun onFingerprintAuthRetry()
}