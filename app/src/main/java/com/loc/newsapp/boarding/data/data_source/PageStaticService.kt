package com.loc.newsapp.boarding.data.data_source

import com.loc.newsapp.R
import com.loc.newsapp.boarding.domain.model.Page

class PageStaticService : IPageDAO {
  override fun getPages(): List<Page> {
    return listOf(
        Page(
            title = "Stay Informed, Anytime, Anywhere",
            description =
                "Get the latest news from trusted sources worldwide, delivered straight to your device. Stay updated with breaking stories, local updates, and global events, all in one place.",
            image = R.drawable.onboarding1),
        Page(
            title = "Personalized News, Just for You",
            description =
                "Customize your news feed by selecting topics and regions that matter most to you. Receive tailored content, from sports to politics, based on your preferences.",
            image = R.drawable.onboarding2),
        Page(
            title = "Real-Time Alerts & Updates",
            description =
                "Never miss an important update! Enable notifications to get real-time alerts on major news, live events, and stories that impact you directly.",
            image = R.drawable.onboarding3))
  }

  override suspend fun getPageById(id: Int): Page? {
    TODO("Not yet implemented")
  }

  override suspend fun insertPage(page: Page) {
    TODO("Not yet implemented")
  }

  override suspend fun deletePage(page: Page) {
    TODO("Not yet implemented")
  }
}
