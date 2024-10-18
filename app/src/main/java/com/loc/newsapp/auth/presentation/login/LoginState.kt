package com.loc.newsapp.auth.presentation.login

import com.loc.newsapp.auth.domain.user.User

data class LoginState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val isUserLoggedIn: Boolean = false
)