package com.loc.newsapp.auth.domain.user

import com.loc.newsapp.auth.domain.user.validators.UserEmailValidator
import com.loc.newsapp.auth.domain.user.validators.UserIdValidator
import com.loc.newsapp.auth.domain.user.validators.UserUsernameValidator

data class User(
    val id: String,
    val username: String,
    val email: String,
    val role: UserRole
) {
    init { // must always be used in data classes to be sure it will throw error also when copy method used :)
        validate()
    }

    fun isAllowedToAdminTools(): Boolean {
        return when(role) {
            UserRole.ADMIN -> true
            else -> false
        }
    }

    private fun validate() {
        UserIdValidator.validate(id)
        UserUsernameValidator.validate(username)
        UserEmailValidator.validate(email)
    }
}