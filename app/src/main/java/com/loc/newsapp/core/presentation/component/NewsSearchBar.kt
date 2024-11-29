package com.loc.newsapp.core.presentation.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.material3.Surface
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
import androidx.compose.ui.tooling.preview.Preview
import com.loc.newsapp.R
import com.loc.newsapp.core.presentation.constant.Dimensions.IconSize
import com.loc.newsapp.core.presentation.extension.searchBar
import com.loc.newsapp.ui.theme.NewsAppTheme

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
        modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth()
            .searchBar(),
        value = text,
        onValueChange = onValueChange,
        readOnly = readOnly,
        leadingIcon = {
          Icon(
              painter = painterResource(id = R.drawable.ic_search),
              contentDescription = null,
              modifier = Modifier.size(IconSize),
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

@Preview(
    name = "NewsSearchBar, no text, light mode",
    group = "NewsSearchBarNoText",
    showBackground = true)
@Preview(
    name = "NewsSearchBar, no text, dark mode",
    group = "NewsSearchBarNoText",
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true)
@Composable
private fun NewsSearchBarNoTextPreview() {
  NewsAppTheme {
    Surface { NewsSearchBar(text = "", readOnly = false, onValueChange = {}, onSearch = {}) }
  }
}

@Preview(
    name = "NewsSearchBar, with text, light mode",
    group = "NewsSearchBarWithText",
    showBackground = true)
@Preview(
    name = "NewsSearchBar, with text, dark mode",
    group = "NewsSearchBarWithText",
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true)
@Composable
private fun NewsSearchBarWithTextPreview() {
  NewsAppTheme {
    Surface {
      NewsSearchBar(
          text = "Lorem Ipsum Dolor Sit", readOnly = false, onValueChange = {}, onSearch = {})
    }
  }
}
