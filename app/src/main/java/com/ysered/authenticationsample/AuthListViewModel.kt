package com.ysered.authenticationsample

import android.arch.lifecycle.*
import com.ysered.authenticationsample.sdk.AuthenticatorInfo
import com.ysered.authenticationsample.sdk.AuthenticatorManager


class AuthListViewModel : ViewModel() {

    private val authManager = AuthenticatorManager

    fun observeAuthListData(owner: LifecycleOwner, observer: Observer<Result<List<AuthenticatorInfo>>>) {
        authManager.authListData.observe(owner, observer)
    }

    fun observePasswordAuthData(owner: LifecycleOwner, observer: Observer<Result<Unit>>) {
        authManager.passwordAuthData.observe(owner, observer)
    }

    fun observeFingerprintAuthData(owner: LifecycleOwner, observer: Observer<Result<Unit>>) {
        authManager.fingerprintAuthData.observe(owner, observer)
    }

    fun authByPassword(password: String) {
        authManager.authByPassword(password)
    }

    fun authByFingerprint(useFingerprint: Boolean) {
        authManager.authByFingerprint(useFingerprint)
    }

    fun resetAuthData() {
        authManager.resetAuthData()
    }

    override fun onCleared() {
        super.onCleared()
        authManager.stopAllJobs()
    }
}
