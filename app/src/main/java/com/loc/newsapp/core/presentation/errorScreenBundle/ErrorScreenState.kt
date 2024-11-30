package com.loc.newsapp.core.presentation.errorScreenBundle

import androidx.paging.LoadState
import com.loc.newsapp.R

data class ErrorScreenState(
    val error: LoadState.Error? = null,
    val message: String = "",
    val icon: Int = R.drawable.icon_network_error,
    val startAnimation: Boolean = false
)
