package com.hackaprende.dogedex.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hackaprende.dogedex.dogdetail.ui.theme.DogedexTheme
import com.hackaprende.dogedex.main.MainActivity
import com.hackaprende.dogedex.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    //private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val user = viewModel.user
        //val status = viewModel.status
        setContent {
            DogedexTheme {
                AuthScreen(
                    onUserLoggedIn = ::startMainActivity
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        DogedexTheme {
            AuthScreen(onUserLoggedIn = {})
        }
    }

    private fun startMainActivity(userValue: User) {
        User.setLoggedInUser(this, userValue)
        try {
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
        } catch (e: ClassNotFoundException) {
            Toast.makeText(this, "Camera Screen error", Toast.LENGTH_SHORT).show()
        }
        finish()
    }
}

