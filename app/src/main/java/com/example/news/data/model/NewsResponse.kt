package com.example.news.data.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @field:SerializedName("status") val status: String,
    @field:SerializedName("articles") val articles: List<News>?,
    @field:SerializedName("totalResults") val totalResults: Int?,
)
