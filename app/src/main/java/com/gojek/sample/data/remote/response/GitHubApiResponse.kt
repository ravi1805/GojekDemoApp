package com.gojek.sample.data.remote.response

import com.gojek.sample.domain.model.UIGitHubRepoData

/**
 * this the data model for parsing remote data
 */
data class GitHubApiResponse(
    val author: String? = null,
    val name: String? = null,
    val avatar: String? = null,
    val description: String? = null,
    val language: String? = null,
    val languageColor: String? = null,
    val stars: Int,
    val forks: Int
)

/**
 * this the data model for parsing remote data
 */
fun GitHubApiResponse.mapToDomain(): UIGitHubRepoData {
    return UIGitHubRepoData(author ?: "",name ?: "", avatar ?: "", description ?: "", language ?: "", languageColor ?: "", stars, forks)
}

fun List<GitHubApiResponse>.mapToDomain(): List<UIGitHubRepoData> {
    return map { it.mapToDomain() }
}


