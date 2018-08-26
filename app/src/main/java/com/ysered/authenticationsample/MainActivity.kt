package com.ysered.authenticationsample

import android.arch.lifecycle.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.ysered.authenticationsample.ext.debug
import com.ysered.authenticationsample.sdk.authenticator.Authenticator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        authList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        authList.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        authList.adapter = AuthenticatorsAdapter(emptyList())

        val authListViewModel = ViewModelProviders.of(this)
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
        debug("!!!!!!!!!!!!!!!!!!!!!!! show loading")
    }

    private fun showList(authenticators: List<Authenticator>) {
        showLoading(isShow = false)
        authList.adapter = AuthenticatorsAdapter(authenticators)
        authList.adapter.notifyDataSetChanged()
        debug("!!!!!!!!!!!!!!!!!!!!!!!!! show list of authenticators")
    }

    private fun showError(message: String) {
        showLoading(isShow = false)
        debug("!!!!!!!!!!!!!!!! an error occurred: $message")
    }
}
