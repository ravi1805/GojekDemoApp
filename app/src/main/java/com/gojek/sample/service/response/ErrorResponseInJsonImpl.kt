package com.gojek.sample.service.response

import com.google.gson.annotations.Expose
import com.gojek.sample.service.exception.IErrorResponse
import java.io.Serializable

class ErrorResponseInJsonImpl : IErrorResponse, Serializable {

    @Expose
    var status: Int = 500
    @Expose
    var data: ErrorData? = null

    override fun getStatusCode(): Int {
        return status
    }

    override fun getMessage(): String? {
        return data?.error ?: ""
    }

}

data class ErrorData(val error: String)
