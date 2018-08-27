package com.ysered.authenticationsample

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_auth_password.*

class PasswordDialogFragment : Fragment() {

    companion object {
        private const val ARG_TITLE = "arg_title"

        fun newInstance(title: String): PasswordDialogFragment {
            val args = Bundle(1).apply {
                putString(ARG_TITLE, title)
            }
            return PasswordDialogFragment().apply {
                arguments = args
            }
        }
    }

    private var authCallback: PasswordAuthenticatorCallback? = null
    private lateinit var authViewModel: AuthenticatorsViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is PasswordAuthenticatorCallback) {
            authCallback = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        authViewModel = ViewModelProviders.of(this)
                .get(AuthenticatorsViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_auth_password, container, false)
        submitButton.setOnClickListener {
            //authViewModel.authenticateByPassword()
        }
        return view
    }

    interface PasswordAuthenticatorCallback {

        fun onAuthenticate(password: String)
    }

}