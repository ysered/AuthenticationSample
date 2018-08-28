package com.ysered.authenticationsample.ext

import android.support.v4.app.Fragment

fun Fragment.replace(
        fragment: Fragment,
        containerId: Int = android.R.id.content,
        addToBackStack: Boolean = true) {
    activity?.replaceFragment(fragment, containerId, addToBackStack)
}

fun Fragment.getStringArg(key: String, default: String = ""): String {
    return arguments?.getString(key, default) ?: default
}

fun Fragment.getBooleanArg(key: String, default: Boolean = false): Boolean {
    return arguments?.getBoolean(key, default) ?: default
}
