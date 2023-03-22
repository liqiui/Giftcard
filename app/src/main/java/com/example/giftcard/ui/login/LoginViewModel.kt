package com.example.giftcard.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.giftcard.utils.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userManager: UserManager
) : ViewModel() {

    private val validateUsername = ValidateUsername()
    private val validatePassword = ValidatePassword()

    var state by mutableStateOf(RegistrationFormState())
    var isSubmitting by mutableStateOf(false) // Add new state for form submission status


    init {
        state = state.copy(
            username = userManager.username ?: "",
            password = ""
        )
    }

    fun onUsernameChanged(username: String) {
        state = state.copy(username = username)
    }

    fun onPasswordChanged(password: String) {
        state = state.copy(password = password)
    }

    fun onSubmit() {
        val usernameResult = validateUsername.execute(state.username)
        val passwordResult = validatePassword.execute(state.password)
        val hasError = listOf(
            usernameResult,
            passwordResult,
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                usernameError = usernameResult.errorMessage,
                passwordError = passwordResult.errorMessage,
            )
            // Set isSubmitting state to false to indicate form submission complete
            isSubmitting = false
            return
        }
        // Set isSubmitting state to true to indicate form submission in progress
        isSubmitting = true
        userManager.username = state.username
        state = state.copy(
            usernameError = "",
            passwordError = "",
        )

//        userManager.password = state.password
        // Perform any other necessary actions upon successful submission
    }
}
