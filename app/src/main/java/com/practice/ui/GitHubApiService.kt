package com.practice.ui

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

data class TreeItem(val path: String, val type: String) // type == "blob" for files
data class TreeResponse(val sha: String, val tree: List<TreeItem>, val truncated: Boolean)

interface GitHubApiService {
    // List all files under a branch recursively:
    // GET /repos/{owner}/{repo}/git/trees/{branch}?recursive=1
    @GET("repos/{owner}/{repo}/git/trees/{branch}")
    suspend fun getRepoTree(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("branch") branch: String,
        @Query("recursive") recursive: Int = 1
    ): TreeResponse

    // Fetch raw file content via full raw URL (ScalarsConverterFactory will return a String)
    @GET
    suspend fun getRawFile(@Url url: String): String
}