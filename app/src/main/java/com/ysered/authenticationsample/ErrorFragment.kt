package com.ysered.authenticationsample

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ysered.authenticationsample.ext.getStringArg
import kotlinx.android.synthetic.main.fragment_auth_error.*

class ErrorFragment : Fragment() {

    companion object {
        const val ARG_ERROR = "arg_error"

        fun newInstance(message: String): ErrorFragment {
            val args = Bundle().apply {
                putString(ARG_ERROR, message)
            }
            return ErrorFragment().apply {
                arguments = args
            }
        }
    }

    private lateinit var callback: Callback

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Callback) {
            callback = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_auth_error, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        errorText.text = getStringArg(ARG_ERROR)
        retryButton.setOnClickListener {
            callback.onPasswordAuthError()
        }
    }

    interface Callback {
        fun onPasswordAuthError()

        fun onFingerprintAuthError()
    }
}
