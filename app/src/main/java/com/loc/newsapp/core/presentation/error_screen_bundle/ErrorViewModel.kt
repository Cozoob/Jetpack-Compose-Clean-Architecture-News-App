package com.loc.newsapp.core.presentation.error_screen_bundle

import android.util.Log
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

class ErrorViewModel(
    private val error: LoadState.Error? = null
) : ViewModel() {
    var state by mutableStateOf(ErrorState(error = error))
        private set

    init {
        Log.d("test", "halo1")
        initState()
    }

    fun onAction(action: ErrorAction) {
//        when(action) {
//            is ErrorAction -> dosmth()
//        }
    }

    private fun initState() {
        Log.d("test", "halo1")
        viewModelScope.launch { // todo init not working
            state = state.copy(
                message = getErrorMessage(),
                icon = getIcon(),
                startAnimation = true
            )
            Log.d("test", "halo2")
        }
    }

    private fun getErrorMessage(): String {
        if(state.error != null) {
            return when(state.error?.error) {
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

        return "You have not saved news so far !" // TODO WHY?????? Should it be here?
    }

    private fun getIcon(): Int {
        if(state.error == null) {
            return R.drawable.ic_search_document;
        }
        return R.drawable.ic_network_error
    }
}