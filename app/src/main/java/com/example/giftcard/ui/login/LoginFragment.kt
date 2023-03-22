package com.example.giftcard.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.giftcard.R

@Composable
fun ScreenLogin(onSubmit: () -> Unit,
viewModel: LoginViewModel = hiltViewModel<LoginViewModel>()) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = viewModel.state.username,
            onValueChange = { viewModel.state = viewModel.state.copy(username = it) },
            label = { Text(text = stringResource(R.string.user_name)) },
            leadingIcon = {
                Icon(
                    Icons.Default.AccountBox,
                    contentDescription = null,
                    tint = Color.Black
                )
            },
            singleLine = true,
            isError = viewModel.state.usernameError?.isNotEmpty() ?: false,
            modifier = Modifier
                .height(66.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                autoCorrect = true,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions {}
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
            onValueChange = { viewModel.state = viewModel.state.copy(password = it) },
            label = { Text(text = "Password") },
            leadingIcon = {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = null,
                    tint = Color.Black
                )
            },
            singleLine = true,
            isError = viewModel.state.passwordError?.isNotEmpty() ?: false,
            modifier = Modifier
                .height(66.dp)
                .fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                autoCorrect = true,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions {}
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
                viewModel.onSubmit()
                if (viewModel.isSubmitting ) {
                    onSubmit()
                }
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

