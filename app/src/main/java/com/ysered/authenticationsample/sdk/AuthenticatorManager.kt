package com.ysered.authenticationsample.sdk

import android.arch.lifecycle.MutableLiveData
import com.ysered.authenticationsample.Result
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch


object AuthenticatorManager {

    private val api = FakeTransmitApi()

    private var authSet = emptySet<AuthenticatorInfo>()

    private var allAuthPassed = false
    private var authListJob: Job? = null
    private var passwordAuthJob: Job? = null
    private var fingerprintAuthJob: Job? = null

    var passwordAuthData = MutableLiveData<Result<Unit>>()
        private set
    var fingerprintAuthData = MutableLiveData<Result<Unit>>()
        private set
    var fingerPrintAuthInProgress = false
        private set

    var authListData = MutableLiveData<Result<List<AuthenticatorInfo>>>()
        private set
        get () {
            if (allAuthPassed) {
                return field
            }
            // cached
            if (authSet.isNotEmpty()) {
                field.postValue(Result.Success(authSet.toList()))
                return field
            }
            // job already in progress
            if (authListJob?.isActive == true) {
                field.value = Result.InProgress()
                return field
            }
            authListJob = launch(CommonPool) {
                field.postValue(Result.InProgress())
                api.authenticatorsList(object : OnListResult<AuthenticatorInfo> {
                    override fun onPositive(result: List<AuthenticatorInfo>) {
                        authSet = result.toSet()
                        field.postValue(Result.Success(result))
                    }

                    override fun onError(message: String) {
                        field.postValue(Result.Error(message))
                    }
                })
            }
            return field
        }

    fun authByPassword(password: String) {
        if (fingerPrintAuthInProgress) {
            return
        }
        passwordAuthJob = launch(CommonPool) {
            fingerPrintAuthInProgress = true
            passwordAuthData.postValue(Result.InProgress())
            api.authenticate(password, object : OnResult {
                override fun onPositive() {
                    passwordAuthData.postValue(Result.Success(Unit))
                    val updated = authSet.filter { it !is AuthenticatorInfo.Password }
                    authListData.postValue(Result.Success(updated))
                    authSet = updated.toSet()
                    allAuthPassed = authSet.isEmpty()
                    fingerPrintAuthInProgress = false
                }

                override fun onError(message: String) {
                    passwordAuthData.postValue(Result.Error(message))
                    fingerPrintAuthInProgress = false
                }
            })
        }
    }

    fun authByFingerprint(useFingerprint: Boolean) {
        if (fingerprintAuthJob?.isActive == true) {
            return
        }
        fingerprintAuthJob = launch(CommonPool) {
            fingerprintAuthData.postValue(Result.InProgress())
            api.authenticate(useFingerprint, object : OnResult {
                override fun onPositive() {
                    fingerprintAuthData.postValue(Result.Success(Unit))
                    val updated = authSet.filter { it !is AuthenticatorInfo.Fingerprint }
                    authListData.postValue(Result.Success(updated))
                    authSet = updated.toSet()
                    allAuthPassed = authSet.isEmpty()
                }

                override fun onError(message: String) {
                    fingerprintAuthData.postValue(Result.Error(message))
                }
            })
        }
    }

    fun resetAuthData() {
        passwordAuthData = MutableLiveData()
        fingerprintAuthData = MutableLiveData()
    }

    fun stopAllJobs() {
        api.cancelAll()
        authListJob?.cancel()
        passwordAuthJob?.cancel()
        fingerprintAuthJob?.cancel()
    }
}