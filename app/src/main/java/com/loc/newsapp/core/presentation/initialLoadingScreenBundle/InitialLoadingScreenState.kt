package com.loc.newsapp.core.presentation.initialLoadingScreenBundle

import com.loc.newsapp.core.domain.routes.HomeScreenRoute
import com.loc.newsapp.core.domain.routes.IRoute

data class InitialLoadingScreenState(
    val startRoute: IRoute = HomeScreenRoute,
    val isLoading: Boolean = true
)
