package com.loc.newsapp.auth.domain.user.validators

import com.loc.newsapp.core.domain.IValidator

object UserUsernameValidator : IValidator<String> {
  override fun validate(value: String) {
    cannotBeBlank(value)
    mustBeAtLeast3AtMost15Long(value)
    mustContainOnlyLettersDigitsUnderscores(value)
  }

  private fun cannotBeBlank(value: String) {
    require(value.isNotBlank()) { "Username cannot be blank or empty." }
  }

  private fun mustBeAtLeast3AtMost15Long(value: String) {
    require(value.length in 3..15) { "Username must be between 3 and 15 characters." }
  }

  private fun mustContainOnlyLettersDigitsUnderscores(value: String) {
    require(value.matches(Regex("^[a-zA-Z0-9_]*$"))) {
      "Username can only contain letters, digits, and underscores."
    }
  }
}
