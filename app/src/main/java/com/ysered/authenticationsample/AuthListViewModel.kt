package com.ysered.authenticationsample

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import com.ysered.authenticationsample.sdk.FakeAuthenticationService
import com.ysered.authenticationsample.sdk.OnListResult
import com.ysered.authenticationsample.sdk.authenticator.Authenticator

class AuthListViewModel : ViewModel() {

    private val authService by lazy { FakeAuthenticationService() }
    private val authList = MutableLiveData<Result<List<Authenticator>>>()
    private var cachedList: List<Authenticator> = emptyList()

    fun observeAuthList(owner: LifecycleOwner, observer: Observer<Result<List<Authenticator>>>) {
        authList.observe(owner, observer)
        loadData()
    }

    private fun loadData() {
        if (cachedList.isNotEmpty()) {
            authList.postValue(Result.Success(cachedList))
            return
        }
        if (!authService.inProgress) {
            authList.postValue(Result.InProgress())
            authService.authenticatorsList(object : OnListResult {
                override fun onPositive(result: List<Authenticator>) {
                    cachedList = result
                    authList.postValue(Result.Success(result))
                }

                override fun onError(message: String) {
                    authList.postValue(Result.Error(message))
                }
            })
        } else {
            authList.postValue(Result.InProgress())
        }
    }

    override fun onCleared() {
        super.onCleared()
        authService.cancel()
    }
}