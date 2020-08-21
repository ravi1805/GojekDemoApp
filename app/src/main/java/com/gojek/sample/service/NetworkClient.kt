package com.gojek.sample.service

import okhttp3.Cache

abstract class NetworkClient {
    abstract fun setupNetworkClient(httpUrlString: String ,cache: Cache)
    abstract fun <T> getNetworkClient(apiService:Class<T>): T
}
