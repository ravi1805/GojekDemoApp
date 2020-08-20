package com.gojek.sample.service.exception

class CloudErrorThrowable(val error: IErrorResponse) : Throwable() {

    override fun getLocalizedMessage(): String? {
        return error.getMessage()
    }

    override val message: String?
        get() = error.getMessage()


}