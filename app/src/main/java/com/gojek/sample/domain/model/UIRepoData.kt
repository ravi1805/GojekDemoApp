package com.gojek.sample.domain.model

data class UIRepoData(
    val name: String,
    val imageUrl: String,
    val description: String,
    val language: String,
    val languageColor:String,
    val star:Int,
    val forks:Int
)