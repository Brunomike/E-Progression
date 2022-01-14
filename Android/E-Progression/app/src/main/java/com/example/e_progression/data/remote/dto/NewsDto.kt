package com.example.e_progression.data.remote.dto

import com.example.e_progression.domain.model.News

data class NewsDto(
    val createdAt: String,
    val id: Int,
    val news_category: String,
    val news_content: String,
    val news_image_link: String?,
    val news_sub_title: String,
    val news_title: String,
    val updatedAt: String
)

fun NewsDto.toNews(): News {
    return News(
        id = id,
        newsTitle = news_title,
        newsSubTitle = news_sub_title,
        newsContent = news_content,
        newsCategory = news_category,
        datePosted = createdAt,
        newsImgUrl = news_image_link?:""
    )
}