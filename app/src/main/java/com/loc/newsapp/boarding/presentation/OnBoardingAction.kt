package com.loc.newsapp.boarding.presentation

sealed interface OnBoardingAction {
  data object LogFirstAppEntry : OnBoardingAction
}
