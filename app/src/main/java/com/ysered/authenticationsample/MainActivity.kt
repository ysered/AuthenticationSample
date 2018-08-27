package com.ysered.authenticationsample

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.ysered.authenticationsample.ext.debug
import com.ysered.authenticationsample.sdk.Authenticator
import com.ysered.authenticationsample.sdk.FingerprintAuthenticator
import com.ysered.authenticationsample.sdk.PasswordAuthenticator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        PasswordDialogFragment.PasswordAuthenticatorCallback {

    private lateinit var authenticatorsViewModel: AuthenticatorsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        authList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        authList.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        authenticatorsViewModel = ViewModelProviders.of(this)
                .get(AuthenticatorsViewModel::class.java)
        authenticatorsViewModel.observeAuthList(this, Observer {
            when (it) {
                is Result.InProgress -> showLoading()
                is Result.Success -> showList(it.payload)
                is Result.Error -> showError(it.message)
            }
        })
    }

    override fun onAuthenticate(password: String) {
        authenticatorsViewModel.authenticateByPassword(password)
    }

    private fun showLoading(isShow: Boolean = true) {
        progressBar.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun showList(authenticators: List<Authenticator>) {
        showLoading(isShow = false)
        showError(isShow = false)
        authList.adapter = AuthenticatorsAdapter(authenticators,
                object : AuthenticatorsAdapter.OnAuthenticatorClickListener {
                    override fun onClick(authenticator: Authenticator) {
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

    private fun showAuthenticatorScreen(authenticator: Authenticator) {
        showError(isShow = false)
        authenticatorsViewModel.currentAuthenticator = authenticator
        when (authenticator) {
            is PasswordAuthenticator -> {
                PasswordDialogFragment()
            }
            is FingerprintAuthenticator -> debug("!!!!!!!!!!!!!! fingerprint auth")
        }
    }
}
