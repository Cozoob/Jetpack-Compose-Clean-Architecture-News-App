package com.loc.newsapp.boarding.presentation

sealed interface OnBoardingAction {
    data object NextPage: OnBoardingAction
    data object PreviousPage: OnBoardingAction
    data object RefreshData: OnBoardingAction
}