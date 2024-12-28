package com.loc.newsapp.articleDetails.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri

class ArticleDetailsScreenIntent(private val context: Context) {
  fun actionSend(articleUrl: String) {
    Intent(Intent.ACTION_SEND).also {
      it.putExtra(Intent.EXTRA_TEXT, articleUrl)
      it.type = "text/plain"
      if (it.resolveActivity(context.packageManager) != null) {
        context.startActivity(it)
      }
    }
  }

  fun actionView(articleUrl: String) {
    Intent(Intent.ACTION_VIEW).also {
      it.data = Uri.parse(articleUrl)
      if (it.resolveActivity(context.packageManager) != null) {
        context.startActivity(it)
      }
    }
  }
}
