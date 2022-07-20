package com.hackaprende.dogedex.auth

import androidx.compose.runtime.Composable
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
    status: ApiResponseStatus<User>?,
    onLoginButtonClick: (email: String, password: String) -> Unit,
    onSignUpButtonClick: (email: String, password: String, confirmPassword: String) -> Unit,
    onErrorDialogDismiss: () -> Unit
) {
    val navController = rememberNavController()
    AuthNavHost(
        navController = navController,
        onSignUpButtonClick = onSignUpButtonClick,
        onLoginButtonClick = onLoginButtonClick
    )

    if (status is ApiResponseStatus.Loading) {
        LoadingWheel()
    } else if (status is ApiResponseStatus.Error) {
        ErrorDialog(
            messageId = status.messageId
        ) { onErrorDialogDismiss() }
    }
}

@Composable
private fun AuthNavHost(
    navController: NavHostController,
    onLoginButtonClick: (email: String, password: String) -> Unit,
    onSignUpButtonClick: (email: String, password: String, confirmPassword: String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = LoginScreenDestination
    ) {
        composable(route = LoginScreenDestination) {
            LoginScreen(
                onRegisterButtonClick = {
                    navController.navigate(route = SignUpScreenDestination)
                },
                onLoginButtonClick = onLoginButtonClick
            )
        }

        composable(route = SignUpScreenDestination) {
            SignUpScreen(
                onNavigationIconClick = {
                    navController.navigateUp()
                },
                onSignUpButtonClick = onSignUpButtonClick
            )
        }
    }
}