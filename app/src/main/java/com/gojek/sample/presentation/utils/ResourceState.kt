package com.gojek.sample.presentation.utils

/**
 * To manage State of UI Screen when we fetch any response from backend
 */
sealed class ResourceState {
    object LOADING : ResourceState()
    object SUCCESS : ResourceState()
    object ERROR : ResourceState()
}
