package com.example.news.utils

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.example.news.ui.theme.NewsTheme

@Composable
fun NewsThemePreview(content: @Composable () -> Unit) {
    NewsTheme {
        Surface(content = content)
    }
}
