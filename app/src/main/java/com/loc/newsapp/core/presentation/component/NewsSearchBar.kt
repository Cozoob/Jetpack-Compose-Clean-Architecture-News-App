package com.loc.newsapp.core.presentation.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import com.loc.newsapp.R
import com.loc.newsapp.core.constant.DimensionConstants.ICON_SIZE
import com.loc.newsapp.core.domain.annotation.DayNightPreviews
import com.loc.newsapp.core.presentation.extension.searchBar

@Composable
fun NewsSearchBar(
    modifier: Modifier = Modifier,
    text: String,
    readOnly: Boolean,
    focusSearchOnStart: Boolean = false,
    onClick: (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit
) {
  val focusRequester = remember { FocusRequester() }

  val interactionSource = remember { MutableInteractionSource() }

  val isClicked = interactionSource.collectIsPressedAsState().value
  LaunchedEffect(key1 = isClicked) {
    if (isClicked) {
      onClick?.invoke()
    }
  }

  if (focusSearchOnStart) {
    LaunchedEffect(key1 = focusSearchOnStart) { focusRequester.requestFocus() }
  }

  Box(modifier = modifier) {
    TextField(
        modifier = Modifier.focusRequester(focusRequester).fillMaxWidth().searchBar(),
        value = text,
        onValueChange = onValueChange,
        readOnly = readOnly,
        leadingIcon = {
          Icon(
              painter = painterResource(id = R.drawable.icon_search),
              contentDescription = null,
              modifier = Modifier.size(ICON_SIZE),
              tint = colorResource(id = R.color.body))
        },
        placeholder = {
          Text(
              text = "Search",
              style = MaterialTheme.typography.bodySmall,
              color = colorResource(id = R.color.placeholder))
        },
        shape = MaterialTheme.shapes.medium,
        colors =
            TextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(id = R.color.input_background),
                unfocusedTextColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                cursorColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch() }),
        textStyle = MaterialTheme.typography.bodySmall,
        interactionSource = interactionSource)
  }
}

@DayNightPreviews
@Composable
private fun NewsSearchBar_NoText_Preview() {
  NewsAppPreviewSurface(
      content = { NewsSearchBar(text = "", readOnly = false, onValueChange = {}, onSearch = {}) })
}

@DayNightPreviews
@Composable
private fun NewsSearchBar_FilledText_Preview() {
  NewsAppPreviewSurface(
      content = {
        NewsSearchBar(
            text = "Lorem Ipsum Dolor Sit", readOnly = false, onValueChange = {}, onSearch = {})
      })
}
