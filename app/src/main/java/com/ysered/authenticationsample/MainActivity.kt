package com.ysered.authenticationsample

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ysered.authenticationsample.ext.clearAllFragments
import com.ysered.authenticationsample.ext.replaceFragment

class MainActivity : AppCompatActivity(), ErrorFragment.Callback {

    private lateinit var authListViewModel: AuthListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        authListViewModel = ViewModelProviders.of(this).get(AuthListViewModel::class.java)

        if (savedInstanceState == null) {
            replaceFragment(AuthListFragment(), addToBackStack = false)
        }
    }

    override fun onPasswordAuthError() {
        clearAllFragments()
        val newFragment = AuthListFragment.newInstance(isPasswordAuthFailed = true)
        replaceFragment(newFragment, addToBackStack = false)
    }

    override fun onFingerprintAuthError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
