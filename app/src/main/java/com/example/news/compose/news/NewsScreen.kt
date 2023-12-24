package com.example.news.compose.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.news.data.model.News
import com.example.news.ui.theme.NewsTheme
import com.example.news.viewmodels.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = viewModel(),
) {
    Scaffold(
        modifier = modifier,
        topBar = { },
    ) { padding ->
        val pagingItems: LazyPagingItems<News> = viewModel.newsList.collectAsLazyPagingItems()

        Column {
            LazyColumn(
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(all = 8.dp),
            ) {
                item {
                    NewsHeader()
                }
                item {
                    NewsSearch(
                        onSearchClick = { text -> viewModel.getNews(text) }
                    )
                }
                items(
                    count = pagingItems.itemCount,
                    key = pagingItems.itemKey { it.url ?: "" },
                ) { index ->
                    val news = pagingItems[index] ?: return@items
                    NewsCard(
                        news = news
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun NewsScreenPreview() {
    NewsTheme {
        NewsScreen()
    }
}
