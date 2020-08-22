package com.gojek.sample.service

import okhttp3.Cache

abstract class NetworkClient {
    abstract fun setupNetworkClient(httpUrlString: String, cache: Cache, ignoreCache: Boolean)
    abstract fun <T> getNetworkClient(apiService: Class<T>): T
}
