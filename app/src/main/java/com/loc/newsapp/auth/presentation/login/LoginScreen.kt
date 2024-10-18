package com.loc.newsapp.auth.presentation.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

// THE ROOT must exist like this in MVI and MVVM for the Kotlin reasons...
@Composable
fun LoginScreenRoot(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    LoginScreen(
        state = viewModel.state,
        onAction = { action ->
            when(action) { // just in case we want some actions to be handle by other "controller" for example the navigator :) if it is not necessary we can just write onAction = viewModel::onAction
                is LoginAction.GoBack -> navController.navigateUp()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    if(state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        // show the login page

    }
}