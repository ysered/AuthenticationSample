package com.ysered.authenticationsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ysered.authenticationsample.ext.debug
import com.ysered.authenticationsample.ext.replaceFragment

class MainActivity : AppCompatActivity(), PasswordFragment.PasswordAuthenticatorCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            replaceFragment(AuthListFragment(), addToBackStack = false)
        }
    }

    override fun onAuthenticate(password: String) {
        debug("On authenticate called with password: $password")
    }
}
