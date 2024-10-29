package com.loc.newsapp.core.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.newsapp.core.domain.routes.HomeScreenRoute
import com.loc.newsapp.core.domain.routes.OnBoardingScreenRoute
import com.loc.newsapp.boarding.domain.use_case.app_entry.AppEntryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class InitialLoadingViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
) : ViewModel() {
    var state by mutableStateOf(InitialLoadingState())
        private set

    init {
        appEntryUseCases.readAppEntry().onEach { shouldStartFromHomeScreen ->
            val startRoute = if (shouldStartFromHomeScreen) HomeScreenRoute else OnBoardingScreenRoute
            state = state.copy(
                startRoute = startRoute,
                isLoading = false
            )
        }.launchIn(viewModelScope)
    }
}
