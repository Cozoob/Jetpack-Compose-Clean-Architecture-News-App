package com.loc.newsapp.auth.presentation.login

sealed interface LoginAction {
    data object TogglePasswordVisibility: LoginAction
    data object GoBack: LoginAction
}