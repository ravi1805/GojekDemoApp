package com.gojek.sample.data.remote.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IRemoteServiceApi {

    @GET("repositories")
    fun getGitHubRepo(@Query("language") language: String, @Query("since") since: String): Call<ResponseBody>
}