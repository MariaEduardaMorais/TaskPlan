package com.example.taskplanapp.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val username: String,
    val displayName: String,
    val userFound: Boolean
) {
    constructor(userFound: Boolean) : this("", "", userFound)
}
