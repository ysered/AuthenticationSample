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
import com.ysered.authenticationsample.ext.replaceFragment
import com.ysered.authenticationsample.sdk.AuthenticatorInfo
import kotlinx.android.synthetic.main.fragment_auth_list.*


class AuthListFragment: Fragment() {

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
        authListViewModel.observeAuthList(this, Observer {
            when (it) {
                is Result.InProgress -> showLoading()
                is Result.Success -> showList(it.payload)
                is Result.Error -> showError(it.message)
            }
        })
    }

    private fun showLoading(isShow: Boolean = true) {
        progressBar.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun showList(authenticators: List<AuthenticatorInfo>) {
        showLoading(isShow = false)
        showError(isShow = false)
        authList.adapter = AuthenticatorsAdapter(authenticators,
                object : AuthenticatorsAdapter.OnAuthenticatorClickListener {
                    override fun onClick(authenticator: AuthenticatorInfo) {
                        showAuthenticatorScreen(authenticator)
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
            is AuthenticatorInfo.Password -> {
                activity?.replaceFragment(PasswordFragment(), addToBackStack = true)
            }
            is AuthenticatorInfo.Fingerprint -> debug("!!!!!!!!!!!!!! fingerprint auth")
        }
    }

}