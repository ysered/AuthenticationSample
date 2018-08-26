package com.ysered.authenticationsample.sdk.authenticator

/**
 * Marker interface.
 */
interface Authenticator {
    /**
     * Contains authenticator name be shown on UI.
     */
    val displayName: String?

    /**
     * Short description.
     */
    val displayDescription: String?
}
