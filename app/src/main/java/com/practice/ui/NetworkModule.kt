package com.practice.ui

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object NetworkModule {
    private const val GITHUB_API_BASE = "https://api.github.com/"
    fun provideRetrofit(): Retrofit {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        val client = OkHttpClient.Builder().addInterceptor(logger).build()

        return Retrofit.Builder()
            .baseUrl(GITHUB_API_BASE)
            .addConverterFactory(ScalarsConverterFactory.create()) // for raw file content if called by full URL
            .addConverterFactory(GsonConverterFactory.create())    // for tree JSON
            .client(client)
            .build()
    }

    val apiService: GitHubApiService by lazy {
        provideRetrofit().create(GitHubApiService::class.java)
    }
}