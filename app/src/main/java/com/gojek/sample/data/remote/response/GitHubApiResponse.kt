package com.gojek.sample.data.remote.response

import com.gojek.sample.domain.model.UIRepoData

/**
 * this the data model for parsing remote data
 */
data class GitHubApiResponse(
    val name: String,
    val imageUrl: String,
    val description: String,
    val language: String,
    val languageColor: String,
    val star: Int,
    val forks: Int
)

/**
 * this the data model for parsing remote data
 */
fun GitHubApiResponse.mapToDomain(): UIRepoData {
    return UIRepoData(name, imageUrl, description, language, languageColor, star, forks)
}

fun List<GitHubApiResponse>.mapToDomain(): List<UIRepoData> {
    return map { it.mapToDomain() }
}


