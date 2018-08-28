package com.ysered.authenticationsample.ext

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.replaceFragment(fragment: Fragment, containerId: Int = android.R.id.content, addToBackStack: Boolean = true) {
    val transaction = supportFragmentManager?.beginTransaction()
    if (addToBackStack) {
        transaction?.addToBackStack(null)
    }
    transaction?.replace(containerId, fragment)
            ?.commit()
}

fun Activity.replaceFragment(fragment: Fragment, containerId: Int = android.R.id.content, addToBackStack: Boolean = true) {
    (this as? AppCompatActivity)?.replaceFragment(fragment, containerId, addToBackStack)
}

fun AppCompatActivity.popFragment() {
    supportFragmentManager?.popBackStack()
}

fun Activity.popFragment() {
    (this as? AppCompatActivity)?.popFragment()
}

fun AppCompatActivity.clearAllFragments() {
    supportFragmentManager?.fragments?.forEach { fragment ->
        supportFragmentManager?.beginTransaction()
                ?.remove(fragment)
                ?.commit()
    }
}

fun Activity.clearAllFragments() {
    (this as? AppCompatActivity)?.clearAllFragments()
}
