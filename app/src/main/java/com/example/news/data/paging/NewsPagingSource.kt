package com.example.news.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.news.api.NewsService
import com.example.news.data.model.News

private const val NEWS_STARTING_PAGE_INDEX = 0

class NewsPagingSource(
    private val service: NewsService,
    private val keyWords: String,
) : PagingSource<Int, News>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        val page = params.key ?: NEWS_STARTING_PAGE_INDEX

        return try {
            val response = service.searchNews(
                query = keyWords,
                pageSize = params.loadSize,
            )
            if (!response.isSuccessful) throw Exception()

            val news = response.body()?.articles ?: emptyList()
            LoadResult.Page(
                data = news,
                prevKey = if (page == NEWS_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == response.body()?.totalResults) null else page + 1,
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}
