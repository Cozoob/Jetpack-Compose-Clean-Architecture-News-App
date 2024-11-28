package com.loc.newsapp.auth.domain.user.validators

import com.loc.newsapp.core.domain.IValidator

object UserIdValidator : IValidator<String> {
  override fun validate(value: String) {
    cannotBeBlank(value)
    mustBeAtLeast8AtMost20Long(value)
    mustContainOnlyLettersDigitsUnderscoresHyphens(value)
  }

  private fun cannotBeBlank(value: String) {
    require(value.isNotBlank()) { "ID cannot be blank or empty." }
  }

  private fun mustBeAtLeast8AtMost20Long(value: String) {
    require(value.length in 8..20) { "ID must be between 8 and 20 characters." }
  }

  private fun mustContainOnlyLettersDigitsUnderscoresHyphens(value: String) {
    require(value.matches(Regex("^[a-zA-Z0-9_-]*$"))) {
      "ID can only contain letters, digits, underscores, or hyphens."
    }
  }
}
