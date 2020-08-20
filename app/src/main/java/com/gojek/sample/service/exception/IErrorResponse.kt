package com.gojek.sample.service.exception

interface IErrorResponse {
    fun getStatusCode(): Int
    fun getMessage(): String?
}