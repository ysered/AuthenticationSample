package com.ysered.authenticationsample

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import com.ysered.authenticationsample.sdk.*


class AuthenticatorsViewModel : ViewModel() {

    private val transmitApi = FakeTransmitApi()
    private var inProgress: Boolean = false

    private val authList = MutableLiveData<Result<List<Authenticator>>>()

    private var cachedAuth: Set<Authenticator> = emptySet()
    var currentAuthenticator: Authenticator? = null

    fun observeAuthList(owner: LifecycleOwner, observer: Observer<Result<List<Authenticator>>>) {
        authList.observe(owner, observer)
        loadData()
    }

    fun authenticateByPassword(password: String) {

    }

    private fun loadData() {
        if (cachedAuth.isNotEmpty()) {
            authList.postValue(Result.Success(cachedAuth.toList()))
            return
        }
        if (!inProgress) {
            inProgress = true
            authList.postValue(Result.InProgress())
            transmitApi.authenticatorsList(object: OnListResult<Authenticator> {
                override fun onPositive(result: List<Authenticator>) {
                    inProgress = false
                    cachedAuth = result.toSet()
                    authList.postValue(Result.Success(result))
                }

                override fun onError(message: String) {
                    inProgress = false
                    authList.postValue(Result.Error(message))
                }
            })
        } else {
            authList.postValue(Result.InProgress())
        }
    }

    override fun onCleared() {
        super.onCleared()
        transmitApi.cancelAll()
    }
}