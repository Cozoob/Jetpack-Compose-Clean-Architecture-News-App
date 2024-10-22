package com.loc.newsapp.boarding.presentation

import com.loc.newsapp.boarding.domain.model.Page

data class OnBoardingState (
    val pages: List<Page> = emptyList(),
    val pageIndex: Int = 0,
    var isLoading: Boolean = true
)