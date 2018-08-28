package com.ysered.authenticationsample

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ysered.authenticationsample.sdk.AuthenticatorInfo
import kotlinx.android.synthetic.main.item_authenticator.view.*


class AuthListAdapter(
        context: Context,
        val authList: List<AuthenticatorInfo>,
        val onAuthenticatorClickListener: OnAuthenticatorClickListener
) : RecyclerView.Adapter<AuthListAdapter.AuthenticatorHolder>() {

    private val normalColor = ContextCompat.getColor(context, R.color.white)
    private val failedColor = ContextCompat.getColor(context, R.color.colorDeepOrange200)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthenticatorHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_authenticator, parent, false)
        return AuthenticatorHolder(view)
    }

    override fun onBindViewHolder(holder: AuthenticatorHolder, position: Int) {
        holder.bind(authList[position])
    }

    override fun getItemCount(): Int {
        return authList.size
    }

    private fun getAuthenticatorInfo(position: Int): AuthenticatorInfo {
        return authList[position]
    }

    inner class AuthenticatorHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                val info = getAuthenticatorInfo(adapterPosition)
                onAuthenticatorClickListener.onClick(info)
            }
        }

        fun bind(authenticator: AuthenticatorInfo) {
            itemView.titleText.text = authenticator.title
            itemView.descriptionText.text = authenticator.description
            val bg = if (authenticator.isFailed) failedColor else normalColor
            itemView.setBackgroundColor(bg)
        }
    }

    interface OnAuthenticatorClickListener {

        fun onClick(info: AuthenticatorInfo)
    }
}
