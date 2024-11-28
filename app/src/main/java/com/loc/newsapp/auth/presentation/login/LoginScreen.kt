package com.loc.newsapp.auth.presentation.login

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.loc.newsapp.core.presentation.components.NewsCircularProgressIndicator

// THE ROOT must exist like this in MVI and MVVM for the Kotlin reasons...
@Composable
fun LoginScreenRoot(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
  LoginScreen(
      state = viewModel.state,
      onAction = { action ->
        when (action) { // just in case we want some actions to be handle by other "controller" for
          // example the navigator :) if it is not necessary we can just write
          // onAction = viewModel::onAction
          is LoginAction.GoBack -> navController.navigateUp()
          else -> Unit
        }
        viewModel.onAction(action)
      })
}

@Composable
fun LoginScreen(state: LoginState, onAction: (LoginAction) -> Unit) {
  if (state.isLoading) {
    NewsCircularProgressIndicator()
  } else {
    // show the login page

  }
}
