package com.loc.newsapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule
@OptIn(ExperimentalCoroutinesApi::class)
constructor(private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()) :
    TestWatcher() {

  override fun finished(description: Description?) {
    super.finished(description)
    Dispatchers.resetMain()
  }

  override fun starting(description: Description?) {
    super.starting(description)
    Dispatchers.setMain(testDispatcher)
  }
}
