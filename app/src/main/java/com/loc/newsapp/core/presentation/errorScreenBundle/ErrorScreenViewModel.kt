package com.loc.newsapp.core.presentation.errorScreenBundle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import com.loc.newsapp.R
import java.net.ConnectException
import java.net.SocketTimeoutException
import kotlinx.coroutines.launch

class ErrorScreenViewModel(error: LoadState.Error? = null) : ViewModel() {
  var state by mutableStateOf(ErrorScreenState(error = error))
    private set

  init {
    initState()
  }

  private fun initState() {
    viewModelScope.launch {
      state = state.copy(message = getErrorMessage(), icon = getIcon(), startAnimation = true)
    }
  }

  private fun getErrorMessage(): String {
    if (state.error != null) {
      return when (state.error?.error) {
        is SocketTimeoutException -> {
          "Server Unavailable."
        }

        is ConnectException -> {
          "Internet Unavailable."
        }

        else -> {
          "Unknown Error."
        }
      }
    }

    return "No content."
  }

  private fun getIcon(): Int {
    if (state.error == null) {
      return R.drawable.icon_search_document
    }
    return R.drawable.icon_network_error
  }
}
