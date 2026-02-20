package com.practice.ui

class GitHubRepository(
    private val api: GitHubApiService = NetworkModule.apiService,
    private val owner: String = "tlijkkkk",
    private val repo: String = "mark_IX",
    private val branch: String = "main"
) : SourceCodeRepository {


    override fun listFiles(path: String): List<String> {
        // Implementation to list files from GitHub repository
        return emptyList()
    }

    override fun readFile(path: String): String {
        // Implementation to read file content from GitHub repository
        return ""
    }
}