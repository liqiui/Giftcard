package com.example.giftcard.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ScreenLogin(onSubmit: () -> Unit) {
    val viewModel = viewModel<LoginViewModel>()
    val keyboardController = LocalSoftwareKeyboardController.current

    val validationEvents = remember(viewModel) {
        viewModel.validationEvents
    }
    LaunchedEffect(key1 = viewModel) {
        validationEvents.collect { event ->
            when (event) {
                is LoginViewModel.ValidationEvent.Success -> onSubmit()
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = viewModel.state.username,
            onValueChange = { viewModel.onEvent(RegistrationFormEvent.UsernameChanged(it)) },
            label = { Text(text = "Username") },
            leadingIcon = {
                Icon(
                    Icons.Default.AccountBox,
                    contentDescription = null,
                    tint = Color.Black
                )
            },
            singleLine = true,
            isError = viewModel.state.usernameError != null,
            modifier = Modifier
                .height(66.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                autoCorrect = true,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions {
                keyboardController?.hide() // Hide keyboard when "Next" is pressed
            }
        )
        viewModel.state.usernameError?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                fontSize = MaterialTheme.typography.labelSmall.fontSize,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(Modifier.height(14.dp))

        OutlinedTextField(
            value = viewModel.state.password,
            onValueChange = { viewModel.onEvent(RegistrationFormEvent.PasswordChanged(it)) },
            label = { Text(text = "Password") },
            leadingIcon = {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = null,
                    tint = Color.Black
                )
            },
            singleLine = true,
            isError = viewModel.state.passwordError != null,
            modifier = Modifier
                .height(66.dp)
                .fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                autoCorrect = true,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions {
                keyboardController?.hide() // Hide keyboard when "Done" is pressed
            }
        )
        viewModel.state.passwordError?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                fontSize = MaterialTheme.typography.labelSmall.fontSize,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(Modifier.height(14.dp))

        Button(
            onClick = {
                viewModel.onEvent(RegistrationFormEvent.Submit)
            },
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Submit")
        }
    }
}

