package com.example.giftcard.ui.login

sealed class RegistrationFormEvent {
    data class UsernameChanged(val username: String) : RegistrationFormEvent()
    data class PasswordChanged(val password: String) : RegistrationFormEvent()

    object Submit: RegistrationFormEvent()
}
