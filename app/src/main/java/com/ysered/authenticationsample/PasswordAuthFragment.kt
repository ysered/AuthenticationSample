package com.ysered.authenticationsample

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ysered.authenticationsample.ext.replace
import kotlinx.android.synthetic.main.fragment_auth_password.*

class PasswordAuthFragment : Fragment() {

    private lateinit var authViewModel: AuthListViewModel
    private lateinit var resultCallback: AuthResultCallback

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is AuthResultCallback) {
            resultCallback = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        authViewModel = ViewModelProviders.of(this)
                .get(AuthListViewModel::class.java)
        authViewModel.observePasswordAuthData(this, Observer {
            when (it) {
                is Result.InProgress -> showLoading(isLoading = true)
                is Result.Success -> onAuthSuccess()
                is Result.Error -> onAuthError(it.message)
            }
        })
        return inflater.inflate(R.layout.fragment_auth_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        submitButton.setOnClickListener {
            authViewModel.authByPassword(passwordText.text.toString())
        }
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun onAuthSuccess() {
        showLoading(isLoading = false)
        resultCallback.onPasswordAuthSucceeded()
    }

    private fun onAuthError(message: String) {
        authViewModel.resetAuthData()
        showLoading(isLoading = false)
        val errorFragment = ErrorFragment.newInstance(message, ErrorFragment.ERROR_PASSWORD)
        replace(errorFragment, addToBackStack = true)
    }
}
