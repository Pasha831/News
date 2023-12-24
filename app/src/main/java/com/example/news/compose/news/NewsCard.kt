package com.example.news.compose.news

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ShareCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.news.utils.NewsThemePreview
import com.example.news.data.model.News
import com.example.news.utils.Constants
import com.github.thunder413.datetimeutils.DateTimeStyle
import com.github.thunder413.datetimeutils.DateTimeUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    news: News? = null,
) {
    val context = LocalContext.current
    val intent = remember {
        Intent(Intent.ACTION_VIEW, Uri.parse(news?.url ?: Constants.SAMPLE_NEWS_URL))
    }

    Card(
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
        ),
        onClick = { context.startActivity(intent) },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            NewsImage(
                modifier = Modifier.weight(0.4f),
                urlToImage = news?.urlToImage
            )
            NewsContent(
                modifier = Modifier.weight(0.6f),
                title = news?.title,
                source = news?.source?.name,
                publishedAt = news?.publishedAt,
                url = news?.url,
            )
        }
    }
}

@Composable
fun NewsImage(
    modifier: Modifier = Modifier,
    urlToImage: String? = null,
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(urlToImage ?: Constants.SAMPLE_PICTURE_URL)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun NewsContent(
    modifier: Modifier = Modifier,
    title: String? = null,
    source: String? = null,
    publishedAt: String? = Constants.SAMPLE_DATE,
    url: String? = null,
) {
    val activity = LocalContext.current as Activity

    val publicationDate = when {
        DateTimeUtils.isToday(publishedAt) -> "Today"
        else -> DateTimeUtils.formatWithStyle(publishedAt, DateTimeStyle.MEDIUM)
    }

    Column(
        modifier = modifier
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            FilledTonalButton(
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .height(30.dp),
                onClick = { },
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = publicationDate,
                    fontSize = 10.sp,
                )
            }
            FilledTonalButton(
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .size(30.dp),
                onClick = {
                    createShareIntent(
                        activity = activity,
                        url = url ?: Constants.SAMPLE_NEWS_URL,
                    )
                },
                shape = CircleShape,
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(0.65F),
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                )
            }
        }

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = title ?: Constants.SAMPLE_TITLE,
            fontSize = 18.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(modifier = Modifier.weight(1f))

        FilledTonalButton(
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .height(30.dp),
            onClick = { },
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = source ?: Constants.SAMPLE_SOURCE,
                fontSize = 10.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
fun NewsCardPreview() {
    NewsThemePreview {
        NewsCard()
    }
}

private fun createShareIntent(activity: Activity, url: String) {
    val shareIntent = ShareCompat.IntentBuilder(activity)
        .setText(url)
        .setType("text/plain")
        .createChooserIntent()
        .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
    activity.startActivity(shareIntent)
}
