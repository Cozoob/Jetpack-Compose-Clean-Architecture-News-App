package com.loc.newsapp.navigation.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.loc.newsapp.R
import com.loc.newsapp.core.constant.DimensionConstants.ICON_SIZE
import com.loc.newsapp.core.constant.DimensionConstants.SMALL_PADDING
import com.loc.newsapp.core.domain.annotation.DayNightPreviews
import com.loc.newsapp.core.presentation.component.NewsAppPreviewSurface
import com.loc.newsapp.navigation.domain.BottomNavigationItem

@Composable
fun BottomNavigation(
    items: List<BottomNavigationItem>,
    selectedItem: Int,
    onItemClick: (Int) -> Unit
) {
  NavigationBar(
      modifier = Modifier.fillMaxWidth(),
      containerColor = MaterialTheme.colorScheme.background,
      tonalElevation = 10.dp) {
        items.forEachIndexed { index, item ->
          NavigationBarItem(
              selected = index == selectedItem,
              onClick = { onItemClick(index) },
              icon = {
                Column(horizontalAlignment = CenterHorizontally) {
                  Icon(
                      painter = painterResource(id = item.icon),
                      contentDescription = "Bottom Navigation Icon",
                      modifier = Modifier.size(ICON_SIZE))
                  Spacer(modifier = Modifier.height(SMALL_PADDING))
                  Text(text = item.text, style = MaterialTheme.typography.labelSmall)
                }
              },
              colors =
                  NavigationBarItemDefaults.colors(
                      selectedIconColor = MaterialTheme.colorScheme.primary,
                      selectedTextColor = MaterialTheme.colorScheme.primary,
                      unselectedIconColor = colorResource(id = R.color.body),
                      unselectedTextColor = colorResource(id = R.color.body),
                      indicatorColor = MaterialTheme.colorScheme.background))
        }
      }
}

@DayNightPreviews
@Composable
private fun BottomNavigation_ThreeItems_Preview() {
  NewsAppPreviewSurface(
      content = {
        BottomNavigation(
            items =
                listOf(
                    BottomNavigationItem(icon = R.drawable.icon_home, text = "Home"),
                    BottomNavigationItem(icon = R.drawable.icon_search, text = "Search"),
                    BottomNavigationItem(icon = R.drawable.icon_bookmark, text = "Bookmark")),
            selectedItem = 0,
            onItemClick = {})
      })
}
