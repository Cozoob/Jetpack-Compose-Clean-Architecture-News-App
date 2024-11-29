package com.loc.newsapp.boarding.presentation

sealed interface OnBoardingScreenAction {
  data object LogFirstAppEntry : OnBoardingScreenAction
}
