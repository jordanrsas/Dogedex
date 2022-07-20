package com.hackaprende.dogedex.auth

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
import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.composables.AuthField
import com.hackaprende.dogedex.composables.BackNavigationIcon

@Composable
fun SignUpScreen(
    onSignUpButtonClick: (email: String, password: String, confirmPassword: String) -> Unit,
    onNavigationIconClick: () -> Unit,
) {
    Scaffold(
        topBar = { SignUpScreenTopBar(onNavigationIconClick) }
    ) {
        Content(
            onSignUpButtonClick = onSignUpButtonClick
        )
    }
}

@Composable
fun Content(
    onSignUpButtonClick: (email: String, password: String, confirmPassword: String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

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
            modifier = Modifier
                .fillMaxWidth(),
            onTextChanged = { newValue -> email = newValue })

        AuthField(
            label = stringResource(id = R.string.password),
            text = password,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            visualTransformation = PasswordVisualTransformation(),
            onTextChanged = { newValue -> password = newValue })

        AuthField(
            label = stringResource(id = R.string.confirm_password),
            text = confirmPassword,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            visualTransformation = PasswordVisualTransformation(),
            onTextChanged = { newValue -> confirmPassword = newValue })

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .semantics { testTag = "signup-button" },
            onClick = { onSignUpButtonClick(email, password, confirmPassword) }) {
            Text(
                stringResource(R.string.sign_up),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun SignUpScreenTopBar(
    onNavigationIconClick: () -> Unit
) {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        backgroundColor = Color.Red,
        contentColor = Color.White,
        navigationIcon = { BackNavigationIcon(onNavigationIconClick) }
    )
}