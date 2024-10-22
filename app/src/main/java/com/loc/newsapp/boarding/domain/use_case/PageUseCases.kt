package com.loc.newsapp.boarding.domain.use_case

data class PageUseCases(
    val getPages: GetPages,
    val getPage: GetPage,
    val addPage: AddPage,
    val deletePage: DeletePage
)