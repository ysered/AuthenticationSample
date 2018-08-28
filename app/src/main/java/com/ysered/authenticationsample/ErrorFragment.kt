package com.ysered.authenticationsample

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ysered.authenticationsample.ext.getIntArg
import com.ysered.authenticationsample.ext.getStringArg
import kotlinx.android.synthetic.main.fragment_auth_error.*

class ErrorFragment : Fragment() {

    companion object {
        const val ERROR_PASSWORD = 1
        const val ERROR_FINGERPRINT = 2

        const val ARG_ERROR = "arg_error"
        const val ARG_ERROR_TYPE = "arg_error_type"

        fun newInstance(message: String, type: Int): ErrorFragment {
            val args = Bundle().apply {
                putString(ARG_ERROR, message)
                putInt(ARG_ERROR_TYPE, type)
            }
            return ErrorFragment().apply {
                arguments = args
            }
        }
    }

    private lateinit var callback: AuthResultCallback

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is AuthResultCallback) {
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
            when (getIntArg(ARG_ERROR_TYPE)) {
                ERROR_PASSWORD -> callback.onPasswordAuthFailed()
                ERROR_FINGERPRINT -> callback.onFingerprintAuthFailed()
            }
        }
    }
}
