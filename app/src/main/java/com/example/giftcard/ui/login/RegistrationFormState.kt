package com.example.giftcard.ui.login

data class RegistrationFormState(
    val username: String = "",
    val usernameError: String? = null,
    val password: String = "",
    val passwordError: String? = null
)
