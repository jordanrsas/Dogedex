package com.hackaprende.dogedex.auth

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.auth.AuthNavDestinations.LoginScreenDestination
import com.hackaprende.dogedex.auth.AuthNavDestinations.SignUpScreenDestination
import com.hackaprende.dogedex.composables.ErrorDialog
import com.hackaprende.dogedex.composables.LoadingWheel
import com.hackaprende.dogedex.model.User

@Composable
fun AuthScreen(
    onUserLoggedIn: (User) -> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val status = authViewModel.status
    val user = authViewModel.user

    val userValue = user
    if (userValue != null) {
        onUserLoggedIn(userValue)
    }

    val navController = rememberNavController()
    AuthNavHost(
        navController = navController,
        authViewModel = authViewModel,
        onSignUpButtonClick = { email, password, confirmPassword ->
            authViewModel.signUp(email, password, confirmPassword)
        },
        onLoginButtonClick = { email, password ->
            authViewModel.login(email, password)
        }
    )

    if (status is ApiResponseStatus.Loading) {
        LoadingWheel()
    } else if (status is ApiResponseStatus.Error) {
        ErrorDialog(
            messageId = status.messageId
        ) { authViewModel.resetApiResponseStatus() }
    }
}

@Composable
private fun AuthNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    onLoginButtonClick: (email: String, password: String) -> Unit,
    onSignUpButtonClick: (email: String, password: String, confirmPassword: String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = LoginScreenDestination
    ) {
        composable(route = LoginScreenDestination) {
            LoginScreen(
                authViewModel = authViewModel,
                onRegisterButtonClick = {
                    navController.navigate(route = SignUpScreenDestination)
                },
                onLoginButtonClick = onLoginButtonClick
            )
        }

        composable(route = SignUpScreenDestination) {
            SignUpScreen(
                authViewModel = authViewModel,
                onNavigationIconClick = {
                    navController.navigateUp()
                },
                onSignUpButtonClick = onSignUpButtonClick
            )
        }
    }
}