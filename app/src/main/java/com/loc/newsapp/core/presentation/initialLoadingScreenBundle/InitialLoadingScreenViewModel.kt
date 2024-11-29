package com.loc.newsapp.core.presentation.initialLoadingScreenBundle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.newsapp.boarding.domain.useCase.appEntry.AppEntryUseCases
import com.loc.newsapp.core.domain.route.HomeScreenRoute
import com.loc.newsapp.core.domain.route.OnBoardingScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class InitialLoadingScreenViewModel
@Inject
constructor(private val appEntryUseCases: AppEntryUseCases) : ViewModel() {
  var state by mutableStateOf(InitialLoadingScreenState())
    private set

  init {
    appEntryUseCases
        .readAppEntry()
        .onEach { shouldStartFromHomeScreen ->
          val startRoute = if (shouldStartFromHomeScreen) HomeScreenRoute else OnBoardingScreenRoute
          state = state.copy(startRoute = startRoute, isLoading = false)
        }
        .launchIn(viewModelScope)
  }
}
