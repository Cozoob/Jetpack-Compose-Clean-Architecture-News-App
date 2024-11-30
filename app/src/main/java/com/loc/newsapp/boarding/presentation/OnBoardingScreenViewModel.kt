package com.loc.newsapp.boarding.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.newsapp.boarding.domain.model.OrderType
import com.loc.newsapp.boarding.domain.model.PageOrder
import com.loc.newsapp.boarding.domain.useCase.appEntry.AppEntryUseCases
import com.loc.newsapp.boarding.domain.useCase.page.PageUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class OnBoardingScreenViewModel
@Inject
constructor(
    private val pageUseCases: PageUseCases,
    private val appEntryUseCases: AppEntryUseCases
) : ViewModel() {
  var state by mutableStateOf(OnBoardingScreenState())
    private set

  init {
    loadPages()
  }

  fun onAction(action: OnBoardingScreenAction) {
    when (action) {
      is OnBoardingScreenAction.LogFirstAppEntry -> logFirstEntry()
    }
  }

  private fun logFirstEntry() {
    viewModelScope.launch { appEntryUseCases.writeAppEntry() }
  }

  private fun loadPages(orderType: OrderType = OrderType.Ascending) {
    viewModelScope.launch {
      state = state.copy(isLoading = true)
      fakeDelay()
      val result = pageUseCases.getPages(PageOrder.Title(orderType))
      state = state.copy(pageIndex = 0, pages = result, isLoading = false)
    }
  }

  private suspend fun fakeDelay() {
    delay(timeMillis = 5000)
  }
}
