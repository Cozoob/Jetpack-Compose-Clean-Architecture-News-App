package com.loc.newsapp.auth.presentation.login


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel


class LoginViewModel(
    private val saveStateHandle: SavedStateHandle
): ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    init {
        saveStateHandle.get<String>("userId")?.let { userId ->
            loadUser(userId)
        }
    }

    private fun loadUser(userId: String) {
       // implemented function...
        // TODO SHOULD USER GET USER USE CASE
    }

    fun onAction(action: LoginAction) {
        when(action) {
            LoginAction.TogglePasswordVisibility -> togglePasswordVisibility()
            LoginAction.GoBack -> goBack()
            else -> Unit
        }
    }

    private fun togglePasswordVisibility() {
        // implemented function...
    }

    private fun goBack() {
        // implemented function...
    }
}