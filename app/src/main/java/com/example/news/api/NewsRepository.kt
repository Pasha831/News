package com.example.news.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.news.data.model.News
import com.example.news.data.paging.NewsPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsService: NewsService,
) {

    companion object {
        private const val NETWORK_PAGE_SIZE = 25
    }

    fun getNews(keyWords: String): Flow<PagingData<News>> {
        runBlocking {
            val response = newsService.searchNews("android", 10)
            println(response)
        }

        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { NewsPagingSource(service = newsService, keyWords = keyWords) },
        ).flow
    }
}
