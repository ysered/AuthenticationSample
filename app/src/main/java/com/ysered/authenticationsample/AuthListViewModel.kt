package com.ysered.authenticationsample

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import com.ysered.authenticationsample.sdk.AuthenticatorInfo
import com.ysered.authenticationsample.sdk.AuthenticatorManager


class AuthListViewModel : ViewModel() {

    private val manager = AuthenticatorManager

    fun observeAuthList(owner: LifecycleOwner, observer: Observer<Result<List<AuthenticatorInfo>>>) {
        manager.authListData.observe(owner, observer)
    }

    override fun onCleared() {
        super.onCleared()
        manager.stopAllJobs()
    }
}
