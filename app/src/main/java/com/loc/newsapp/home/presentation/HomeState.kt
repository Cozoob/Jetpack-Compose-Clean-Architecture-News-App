package com.loc.newsapp.home.presentation

data class HomeState(
    val isLoading: Boolean = true,
    val news: List<String> = emptyList()
)