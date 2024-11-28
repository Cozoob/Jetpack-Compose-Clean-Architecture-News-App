package com.loc.newsapp.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.newsapp.auth.domain.user.UserRole
import kotlinx.coroutines.launch

class LoginViewModel(private val saveStateHandle: SavedStateHandle) : ViewModel() {

  var state by mutableStateOf(LoginState())
    private set

  init {
    saveStateHandle.get<String>("userId")?.let { userId -> loadUser(userId) }
  }

  fun onAction(action: LoginAction) {
    when (action) {
      is LoginAction.TogglePasswordVisibility -> togglePasswordVisibility()
      is LoginAction.ChangeUserRole -> changeUserRole()
      else -> Unit
    }
  }

  private fun togglePasswordVisibility() {
    // implemented function...
  }

  private fun changeUserRole() {
    state.user?.let { state = state.copy(user = it.copy(role = UserRole.USER)) }
  }

  private fun loadUser(userId: String) {
    viewModelScope.launch {
      // isLoading = true
      // user = repository.loadUser(userId)
      // isLoading = false
    }
  }
}
