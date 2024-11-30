package com.loc.newsapp.core.presentation.errorScreenBundle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import com.loc.newsapp.R
import com.loc.newsapp.core.presentation.util.UiText
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

  private fun getErrorMessage(): UiText {
    if (state.error != null) {
      return when (state.error?.error) {
        is SocketTimeoutException -> {
          UiText.StringResource(resId = R.string.core_text_serverUnavailable_errorMessage)
        }

        is ConnectException -> {
          UiText.StringResource(resId = R.string.core_text_internetUnavailable_errorMessage)
        }

        else -> {
          UiText.StringResource(resId = R.string.core_text_unknownError_errorMessage)
        }
      }
    }

    return UiText.StringResource(resId = R.string.core_text_noContent_errorMessage)
  }

  private fun getIcon(): Int {
    if (state.error == null) {
      return R.drawable.icon_search_document
    }
    return R.drawable.icon_network_error
  }
}
