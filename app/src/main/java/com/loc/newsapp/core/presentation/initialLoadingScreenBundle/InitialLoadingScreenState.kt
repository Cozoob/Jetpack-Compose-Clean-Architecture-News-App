package com.loc.newsapp.core.presentation.initialLoadingScreenBundle

import com.loc.newsapp.core.domain.route.HomeScreenRoute
import com.loc.newsapp.core.domain.route.IRoute

data class InitialLoadingScreenState(
    val startRoute: IRoute = HomeScreenRoute,
    val isLoading: Boolean = true
)
