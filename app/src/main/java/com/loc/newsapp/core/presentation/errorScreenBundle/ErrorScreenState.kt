package com.loc.newsapp.core.presentation.errorScreenBundle

import androidx.paging.LoadState
import com.loc.newsapp.R
import com.loc.newsapp.core.presentation.util.UiText

data class ErrorScreenState(
    val error: LoadState.Error? = null,
    val message: UiText = UiText.DynamicString(),
    val icon: Int = R.drawable.icon_network_error,
    val startAnimation: Boolean = false
)
