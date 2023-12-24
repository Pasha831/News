package com.example.news.data.model

import com.google.gson.annotations.SerializedName

data class News(
    @field:SerializedName("source") val source: NewsSource?,
    @field:SerializedName("title") val title: String?,
    @field:SerializedName("url") val url: String?,
    @field:SerializedName("publishedAt") val publishedAt: String?,
    @field:SerializedName("urlToImage") val urlToImage: String?,
)

data class NewsSource(
    @field:SerializedName("id") val id: String?,
    @field:SerializedName("name") val name: String?,
)
