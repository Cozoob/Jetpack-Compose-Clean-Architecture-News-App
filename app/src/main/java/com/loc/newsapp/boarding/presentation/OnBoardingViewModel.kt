package com.loc.newsapp.boarding.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.newsapp.boarding.domain.model.OrderType
import com.loc.newsapp.boarding.domain.model.PageOrder
import com.loc.newsapp.boarding.domain.use_case.PageUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val pageUseCases: PageUseCases
): ViewModel() {
    var state by mutableStateOf(OnBoardingState())
        private set

    init {
        loadPages()
    }

    fun onAction(action: OnBoardingAction) {
        when(action) {
            is OnBoardingAction.NextPage -> nextPage()
            is OnBoardingAction.PreviousPage -> previousPage()
            is OnBoardingAction.RefreshData -> refreshData()
        }
    }

    private fun nextPage() {
        // TODO? Not needed?
    }

    private fun previousPage() {
        // TODO? Not needed?
    }

    private fun refreshData() {
        loadPages(OrderType.Descending)
    }

    private fun loadPages(orderType: OrderType = OrderType.Ascending) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            // Fake delay to show loading state :)
            delay(5000)
            val result = pageUseCases.getPages(PageOrder.Title(orderType))
            state = state.copy(
                pageIndex = 0,
                pages = result,
                isLoading = false
            )
        }
    }
}