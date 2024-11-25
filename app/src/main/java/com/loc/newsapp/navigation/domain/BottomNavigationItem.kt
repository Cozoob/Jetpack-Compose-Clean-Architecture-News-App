package com.loc.newsapp.navigation.domain

import androidx.annotation.DrawableRes

data class BottomNavigationItem(
    @DrawableRes val icon: Int,
    val text: String
)
