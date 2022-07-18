package com.hackaprende.dogedex.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hackaprende.dogedex.R
import com.hackaprende.dogedex.api.ApiResponseStatus

@Composable
fun loginScreen(
    status: ApiResponseStatus<Any>? = null
) {
    Scaffold(
        topBar = { LoginScreenToolBar() }
    ) {
        Content()
    }
}

@Composable
fun Content() {
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
            onTextChanged = { newValue -> email = newValue })

        AuthField(
            label = stringResource(id = R.string.password),
            text = password,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            visualTransformation = PasswordVisualTransformation(),
            onTextChanged = { newValue -> password = newValue })

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .semantics { testTag = "login-button" },
            onClick = { }) {
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
                .clickable(enabled = true, onClick = { })
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
fun AuthField(
    label: String,
    text: String,
    modifier: Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        label = { Text(text = label) },
        value = text,
        visualTransformation = visualTransformation,
        onValueChange = { newValue -> onTextChanged(newValue) }
    )
}

@Composable
fun LoginScreenToolBar() {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        backgroundColor = Color.Red,
        contentColor = Color.White
    )
}