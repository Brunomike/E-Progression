package com.example.e_progression.presentation.main.news_detail

import com.example.e_progression.domain.model.News

data class NewsItemListState(
    val isLoading: Boolean = false,
    val news: News? = null,
    val error: String = ""
)