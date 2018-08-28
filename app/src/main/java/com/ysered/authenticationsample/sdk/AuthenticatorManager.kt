package com.ysered.authenticationsample.sdk

import android.arch.lifecycle.MutableLiveData
import com.ysered.authenticationsample.Result
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch


object AuthenticatorManager {

    private val api = FakeTransmitApi()

    private var authSet = emptySet<AuthenticatorInfo>()

    private var authListJob: Job? = null
    private var passwordAuthJob: Job? = null
    private var fingerprintAuthJob: Job? = null

    var passwordAuthData = MutableLiveData<Result<Unit>>()
    var fingerprintAuthData = MutableLiveData<Result<Unit>>()

    var authListData = MutableLiveData<Result<List<AuthenticatorInfo>>>()
        private set
        get () {
            // cached
            if (authSet.isNotEmpty()) {
                field.postValue(Result.Success(authSet.toList()))
                return field
            }
            // job already in progress
            if (authListJob?.isActive == true) {
                field.postValue(Result.InProgress())
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
        if (passwordAuthJob?.isActive == true) {
            return
        }
        passwordAuthJob = launch(CommonPool) {
            passwordAuthData.postValue(Result.InProgress())
            api.authenticate(password, object : OnResult {
                override fun onPositive() {
                    passwordAuthData.postValue(Result.Success(Unit))
                    val updated = authSet.filter { it !is AuthenticatorInfo.Password }
                    authListData.postValue(Result.Success(updated))
                    authSet = updated.toSet()
                }

                override fun onError(message: String) {
                    passwordAuthData.postValue(Result.Error(message))
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
        authListJob?.cancel()
        passwordAuthJob?.cancel()
        fingerprintAuthJob?.cancel()
    }
}