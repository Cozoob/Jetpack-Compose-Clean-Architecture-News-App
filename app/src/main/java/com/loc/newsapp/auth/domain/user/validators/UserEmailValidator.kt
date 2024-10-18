package com.loc.newsapp.auth.domain.user.validators

import com.loc.newsapp.core.domain.IValidator

object UserEmailValidator : IValidator<String> {
    override fun validate(value: String) {
        cannotBeBlank(value)
        mustBeCorrectFormat(value)
    }

    private fun cannotBeBlank(value: String) {
        require(value.isNotBlank()) {
            "Email cannot be blank."
        }
    }

    private fun mustBeCorrectFormat(value: String) {
        require(value.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))) {
            "Invalid email format."
        }
    }
}