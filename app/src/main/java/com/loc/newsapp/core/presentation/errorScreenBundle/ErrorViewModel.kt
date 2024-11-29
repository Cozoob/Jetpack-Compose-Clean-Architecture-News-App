package com.loc.newsapp.core.presentation.errorScreenBundle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import com.loc.newsapp.R
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException

class ErrorViewModel(error: LoadState.Error? = null) : ViewModel() {
  var state by mutableStateOf(ErrorState(error = error))
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

    return "You have not saved news so far !"
  }

  private fun getIcon(): Int {
    if (state.error == null) {
      return R.drawable.ic_search_document
    }
    return R.drawable.ic_network_error
  }
}
