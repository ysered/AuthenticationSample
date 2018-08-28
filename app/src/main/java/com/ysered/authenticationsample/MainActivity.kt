package com.ysered.authenticationsample

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ysered.authenticationsample.ext.clearAllFragments
import com.ysered.authenticationsample.ext.popFragment
import com.ysered.authenticationsample.ext.replaceFragment

class MainActivity : AppCompatActivity(), PasswordAuthResultCallback {

    private lateinit var authListViewModel: AuthListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        authListViewModel = ViewModelProviders.of(this).get(AuthListViewModel::class.java)

        if (savedInstanceState == null) {
            replaceFragment(AuthListFragment(), addToBackStack = false)
        }
    }

    override fun onPasswordAuthSuccess() {
        popFragment()
    }

    override fun onPasswordAuthFailed() {
        authListViewModel.authManager.resetAuthData()
        clearAllFragments()
        val newFragment = AuthListFragment.newInstance(isPasswordAuthFailed = true)
        replaceFragment(newFragment, addToBackStack = false)
    }
}
