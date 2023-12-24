package com.example.news.api

import com.example.news.data.model.NewsResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface NewsService {

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("pageSize") pageSize: Int,
    ): Response<NewsResponse>

    companion object {
        private const val BASE_URL = "https://newsapi.org/"
        private const val TOKEN = "8624a88c2fc34b8d8e4a502eab50094b"

        fun create(): NewsService {
            val client = OkHttpClient.Builder()
                .addNetworkInterceptor(
                    HttpLoggingInterceptor().apply { level = BODY }
                )
                .addInterceptor(
                    Interceptor { chain ->
                        chain.run {
                            proceed(
                                request()
                                    .newBuilder()
                                    .addHeader("Authorization", TOKEN)
                                    .build()
                            )
                        }
                    }
                )
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsService::class.java)
        }
    }
}
