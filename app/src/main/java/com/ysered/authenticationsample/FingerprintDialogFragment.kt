package com.ysered.authenticationsample

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater


class FingerprintDialogFragment : DialogFragment() {

    private lateinit var callback: Callback

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Callback) {
            callback = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_auth_fingerprint, null)
        return AlertDialog.Builder(context!!)
                .setView(view)
                .setPositiveButton(R.string.use_fingerprint) { _, _ ->
                    callback.onUseFingerprintClicked()
                }
                .setNegativeButton(android.R.string.cancel) { _, _ ->
                    callback.onCancelClicked()
                }
                .create()
    }

    interface Callback {

        fun onCancelClicked()

        fun onUseFingerprintClicked()
    }
}
