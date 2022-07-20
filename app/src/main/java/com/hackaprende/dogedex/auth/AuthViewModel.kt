package com.hackaprende.dogedex.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackaprende.dogedex.R
import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.isValidEmail
import com.hackaprende.dogedex.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    var user by mutableStateOf<User?>(null)
        private set

    var status by mutableStateOf<ApiResponseStatus<User>?>(null)
        private set

    var emailError by mutableStateOf<Int?>(null)
        private set

    var passwordError by mutableStateOf<Int?>(null)
        private set

    var confirmPasswordError by mutableStateOf<Int?>(null)
        private set

    private val authRepository = AuthRepository()

    fun login(email: String, password: String) {
        when {
            email.isEmpty() -> emailError = R.string.email_is_not_valid
            password.isEmpty() -> passwordError = R.string.password_must_not_be_empty
            else -> {
                viewModelScope.launch {
                    status = ApiResponseStatus.Loading()
                    handleResponseStatus(
                        authRepository.login(
                            email,
                            password
                        )
                    )
                }
            }
        }
    }

    fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ) {

        when {
            email.isEmpty() -> {
                emailError = R.string.email_is_empty
            }
            !isValidEmail(email) -> {
                emailError = R.string.email_is_not_valid
            }
            password.isEmpty() -> {
                passwordError = R.string.password_must_not_be_empty
            }
            passwordConfirmation.isEmpty() -> {
                confirmPasswordError = R.string.password_must_not_be_empty
            }
            password != passwordConfirmation -> {
                passwordError = R.string.passwords_do_not_match
                confirmPasswordError = R.string.passwords_do_not_match
            }
        }

        viewModelScope.launch {
            status = ApiResponseStatus.Loading()
            handleResponseStatus(
                authRepository.signUp(
                    email,
                    password,
                    passwordConfirmation
                )
            )
        }
    }

    fun resetErrors() {
        emailError = null
        passwordError = null
        confirmPasswordError = null
    }

    fun resetApiResponseStatus() {
        status = null
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<User>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            user = apiResponseStatus.data
        }
        status = apiResponseStatus
    }
}