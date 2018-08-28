package com.ysered.authenticationsample

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ysered.authenticationsample.ext.debug
import com.ysered.authenticationsample.ext.getBooleanArg
import com.ysered.authenticationsample.ext.replace
import com.ysered.authenticationsample.sdk.AuthenticatorInfo
import kotlinx.android.synthetic.main.fragment_auth_list.*


class AuthListFragment : Fragment() {

    companion object {
        const val ARG_IS_PASSWORD_AUTH_FAILED = "arg_is_password_auth_failed"
        const val ARG_IS_FINGERPRINT_AUTH_FAILED = "arg_is_fingerprint_auth_failed"

        fun newInstance(isPasswordAuthFailed: Boolean = false,
                        isFingerPrintAuthFailed: Boolean = false
        ): AuthListFragment {
            val args = Bundle().apply {
                putBoolean(ARG_IS_PASSWORD_AUTH_FAILED, isPasswordAuthFailed)
                putBoolean(ARG_IS_FINGERPRINT_AUTH_FAILED, isFingerPrintAuthFailed)
            }
            return AuthListFragment().apply {
                arguments = args
            }
        }
    }

    private lateinit var authListViewModel: AuthListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_auth_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authList.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        authList.layoutManager = LinearLayoutManager(activity).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        authListViewModel = ViewModelProviders.of(this)
                .get(AuthListViewModel::class.java)
        authListViewModel.authManager.authListData.observe(this, Observer {
            when (it) {
                is Result.InProgress -> showLoading()
                is Result.Success -> {
                    val updatedInfoList = populateFailedInfo(it.payload)
                    showList(updatedInfoList)
                }
                is Result.Error -> showError(it.message)
            }
        })
    }

    private fun populateFailedInfo(authInfoList: List<AuthenticatorInfo>): List<AuthenticatorInfo> {
        return authInfoList.map {
            when (it) {
                is AuthenticatorInfo.Password -> {
                    it.isFailed = getBooleanArg(ARG_IS_PASSWORD_AUTH_FAILED)
                    it
                }
                is AuthenticatorInfo.Fingerprint -> {
                    it.isFailed = getBooleanArg(ARG_IS_FINGERPRINT_AUTH_FAILED)
                    it
                }
            }
        }
    }

    private fun showLoading(isShow: Boolean = true) {
        progressBar.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun showList(infoList: List<AuthenticatorInfo>) {
        showLoading(isShow = false)
        showError(isShow = false)
        authList.adapter = AuthListAdapter(context!!, infoList,
                object : AuthListAdapter.OnAuthenticatorClickListener {
                    override fun onClick(info: AuthenticatorInfo) {
                        showAuthenticatorScreen(info)
                    }
                })
    }

    private fun showError(message: String = "", isShow: Boolean = true) {
        showLoading(isShow = false)
        if (isShow) {
            errorText.visibility = View.VISIBLE
            errorText.text = message
        } else {
            errorText.visibility = View.INVISIBLE
        }
    }

    private fun showAuthenticatorScreen(info: AuthenticatorInfo) {
        showError(isShow = false)
        when (info) {
            is AuthenticatorInfo.Password -> replace(PasswordFragment(), addToBackStack = true)
            is AuthenticatorInfo.Fingerprint -> debug("!!!!!!!!!!!!!! fingerprint auth")
        }
    }

}