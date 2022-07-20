package com.hackaprende.dogedex.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hackaprende.dogedex.dogdetail.ui.theme.DogedexTheme
import com.hackaprende.dogedex.main.MainActivity
import com.hackaprende.dogedex.model.User

class LoginActivity : ComponentActivity() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = viewModel.user
        val status = viewModel.status

        val userValue = user.value
        if (userValue != null) {
            User.setLoggedInUser(this, userValue)
            startMainActivity()
        } else {

        }

        setContent {
            DogedexTheme {
                AuthScreen(
                    status = status.value,
                    onLoginButtonClick = { email, password ->
                        viewModel.login(email, password)
                    },
                    onSignUpButtonClick = { email, password, confirmPassword ->
                        viewModel.signUp(email, password, confirmPassword)
                    },
                    onErrorDialogDismiss = ::resetApiResponseStatus
                )
            }
        }
    }

    private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        DogedexTheme {

        }
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
