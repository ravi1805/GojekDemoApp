package com.gojek.sample.presentation.utils

/**
 * Data class will use to manage result response from view model
 */
data class Resource<out T> constructor(
    val state: ResourceState,
    val data: T? = null,
    val message: String? = null
)
