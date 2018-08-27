package com.ysered.authenticationsample

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ysered.authenticationsample.sdk.Authenticator
import kotlinx.android.synthetic.main.item_authenticator.view.*


class AuthenticatorsAdapter(
        val authList: List<Authenticator>,
        val onAuthenticatorClickListener: OnAuthenticatorClickListener
) : RecyclerView.Adapter<AuthenticatorsAdapter.AuthenticatorHolder>() {

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

    private fun getAuthenticator(position: Int): Authenticator {
        return authList[position]
    }

    inner class AuthenticatorHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                val authenticator = getAuthenticator(adapterPosition)
                onAuthenticatorClickListener.onClick(authenticator)
            }
        }

        fun bind(authenticator: Authenticator) {
            itemView.titleText.text = authenticator.title
            itemView.descriptionText.text = authenticator.description
        }
    }

    interface OnAuthenticatorClickListener {

        fun onClick(authenticator: Authenticator)
    }
}