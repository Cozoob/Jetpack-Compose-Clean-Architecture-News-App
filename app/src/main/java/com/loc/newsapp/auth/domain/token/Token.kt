package com.loc.newsapp.auth.domain.token

data class Token(val token: String) {
  fun isOk(): Boolean {
    // dummy code :)
    return token != ""
  }
}
