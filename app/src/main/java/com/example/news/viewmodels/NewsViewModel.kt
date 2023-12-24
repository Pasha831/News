package com.example.news.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.news.api.NewsRepository
import com.example.news.data.model.News
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository,
) : ViewModel() {

    private val _newsList = MutableStateFlow(PagingData.empty<News>())
    val newsList: StateFlow<PagingData<News>> get() = _newsList

    fun getNews(keyWords: String) = viewModelScope.launch {
        repository.getNews(keyWords)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _newsList.value = it
            }
    }
}
