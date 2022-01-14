package com.example.e_progression.presentation.main.news

import com.example.e_progression.domain.model.News

data class NewsListState(
    val isLoading: Boolean = false,
    val news: List<News> = emptyList(),
    val error: String = ""
)