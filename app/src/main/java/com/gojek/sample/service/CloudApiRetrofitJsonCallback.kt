package com.gojek.sample.service

import com.google.gson.Gson
import com.gojek.sample.service.exception.CloudErrorThrowable
import com.gojek.sample.service.response.ErrorResponseInJsonImpl
import io.reactivex.ObservableEmitter
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CloudApiRetrofitJsonCallback<R>(private val emitter: ObservableEmitter<R>,
                                      private val resCls: Class<R>) : Callback<ResponseBody> {


    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        var bodyString: String?
        if (response.body() != null) {
            bodyString = (response.body() as ResponseBody).string()
            var model: R = Gson().fromJson(bodyString, resCls)
            emitter.onNext(model)
            emitter.onComplete()
        } else if (response.errorBody() != null) {
            if (response.code() == 400|| response.code() == 401 || response.code() == 403|| response.code() == 404) {
                try {
                    bodyString = (response.errorBody() as ResponseBody).string()
                    val error: ErrorResponseInJsonImpl = Gson().fromJson(bodyString, ErrorResponseInJsonImpl::class.java)

                    if (!emitter.isDisposed) {
                        emitter.onError(CloudErrorThrowable(error))
                        emitter.onComplete()
                    }
                } catch (exception: Exception) {
                    val errorResponse = ErrorResponseInJsonImpl()
                    if (!emitter.isDisposed) {
                        emitter.onError(CloudErrorThrowable(errorResponse))
                    }
                }
            } else {
                val errorResponse = ErrorResponseInJsonImpl()
                if (!emitter.isDisposed) {
                    emitter.onError(CloudErrorThrowable(errorResponse))
                    emitter.onComplete()
                }
            }
        }
    }

    override fun onFailure(call: Call<ResponseBody>?, throwable: Throwable) {
        if (!emitter.isDisposed) {
            emitter.onError(throwable)
            emitter.onComplete()
        }
    }
}