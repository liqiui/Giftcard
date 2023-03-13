package com.example.giftcard.ui.login

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.giftcard.utils.UserManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    application: Application,
    private val validateUsername: ValidateUsername = ValidateUsername(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
) : ViewModel() {

    var state by mutableStateOf(RegistrationFormState())
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()
    private val userManager = UserManager(application)

    init {
        onEvent(RegistrationFormEvent.UsernameChanged(userManager.username ?: ""))
        onEvent(RegistrationFormEvent.PasswordChanged(userManager.password ?: ""))
    }

    fun onEvent(event: RegistrationFormEvent) {
        when (event) {
            is RegistrationFormEvent.UsernameChanged -> state = state.copy(username = event.username)
            is RegistrationFormEvent.PasswordChanged -> state = state.copy(password = event.password)
            is RegistrationFormEvent.Submit -> submitData()
        }
    }

    private fun submitData() {
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
            return
        }
        userManager.username = state.username
        userManager.password = state.password

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }
    sealed class ValidationEvent {
        object Success: ValidationEvent()
    }
}

class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(application = application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
