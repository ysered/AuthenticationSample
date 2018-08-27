package com.ysered.authenticationsample

import android.arch.lifecycle.ViewModel
import com.ysered.authenticationsample.sdk.AuthenticatorManager


class AuthListViewModel : ViewModel() {

    val authManager = AuthenticatorManager

    override fun onCleared() {
        super.onCleared()
        authManager.stopAllJobs()
    }
}
