package com.hackaprende.dogedex.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.hackaprende.dogedex.R
import com.hackaprende.dogedex.dogdetail.ui.theme.DogedexTheme
import com.hackaprende.dogedex.main.MainActivity

class LoginActivity : ComponentActivity(), LoginFragment.LoginFragmentActions,
    SignUpFragment.SignUpFragmentActions {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DogedexTheme {
                loginScreen()
            }
        }

        /*val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.status.observe(this) { status ->
            when (status) {
                is ApiResponseStatus.Error -> {
                    binding.loadingWheel.visibility = View.GONE
                    //Toast.makeText(this, status.messageId, Toast.LENGTH_LONG).show()
                    showErrorDialog(status.messageId)
                }
                is ApiResponseStatus.Loading ->
                    binding.loadingWheel.visibility = View.VISIBLE

                is ApiResponseStatus.Success ->
                    binding.loadingWheel.visibility = View.GONE
            }
        }

        viewModel.user.observe(this) { user ->
            if (user != null) {
                User.setLoggedInUser(this, user)
                ApiServiceInterceptor.setSessionToken(user.authenticationToken)
                startMainActivity()
            }
        }*/
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showErrorDialog(messageId: Int) {
        AlertDialog.Builder(this)
            .setTitle(R.string.there_was_an_error)
            .setMessage(messageId)
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .create()
            .show()

    }

    override fun onRegisterButtonClick() {
        findNavController(R.id.nav_host_fragment)
            .navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
    }

    override fun onLoginFieldsValidated(email: String, password: String) {
        viewModel.login(email, password)
    }

    override fun onSignUpFieldsValidated(
        email: String,
        password: String,
        passwordConfirmation: String
    ) {
        viewModel.signUp(email, password, passwordConfirmation)
    }
}
