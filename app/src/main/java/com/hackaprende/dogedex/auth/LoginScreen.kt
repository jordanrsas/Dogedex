package com.hackaprende.dogedex.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hackaprende.dogedex.R
import com.hackaprende.dogedex.composables.AuthField

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onLoginButtonClick: (email: String, passwor: String) -> Unit,
    onRegisterButtonClick: () -> Unit
) {
    Scaffold(
        topBar = { LoginScreenToolBar() }
    ) {
        Content(
            onLoginButtonClick = onLoginButtonClick,
            onRegisterButtonClick = {
                onRegisterButtonClick()
                authViewModel.resetErrors()
            },
            resetFieldErrors = { authViewModel.resetErrors() },
            authViewModel = authViewModel
        )
    }
}

@Composable
fun Content(
    onLoginButtonClick: (email: String, passwor: String) -> Unit,
    onRegisterButtonClick: () -> Unit,
    resetFieldErrors: () -> Unit,
    authViewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 32.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthField(
            label = stringResource(id = R.string.email),
            text = email,
            modifier = Modifier.fillMaxWidth(),
            onTextChanged = { newValue ->
                email = newValue
                resetFieldErrors()
            },
            errorMessageId = authViewModel.emailError
        )

        AuthField(
            label = stringResource(id = R.string.password),
            text = password,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            visualTransformation = PasswordVisualTransformation(),
            onTextChanged = { newValue ->
                password = newValue
                resetFieldErrors()
            },
            errorMessageId = authViewModel.passwordError
        )

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .semantics { testTag = "login-button" },
            onClick = {
                onLoginButtonClick(email, password)
            }) {
            Text(
                stringResource(R.string.login),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.do_not_have_an_account)
        )

        Text(
            modifier = Modifier
                .clickable(enabled = true, onClick = { onRegisterButtonClick() })
                .fillMaxWidth()
                .padding(16.dp)
                .semantics { testTag = "login-screen-register-button" },
            text = stringResource(R.string.register),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun LoginScreenToolBar() {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        backgroundColor = Color.Red,
        contentColor = Color.White
    )
}