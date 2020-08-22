package com.gojek.sample.service


import com.gojek.sample.BuildConfig
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * The RetrofitClient program implements an application that
 * setup the retrofit client
 * get the current object of retrofit client
 */

class RetrofitClient : NetworkClient() {

    private var retrofit: Retrofit? = null
    /**
     * Get the instance of retrofit
     * @return retrofit : Instance of retrofit
     */
    private fun getInstance(): Retrofit? {
        synchronized(NetworkClientFactory::class) {
            return retrofit
        }
    }

    /**
     * Get the network client reference
     * @param apiService : Pass the api service class like " AccountApiService::class.java"
     * @return Api service interface
     */
    override fun <T> getNetworkClient(apiService: Class<T>): T {
        val networkClient = getInstance()
            ?: throw IllegalStateException("Retrofit not initialized")
        return networkClient.create(apiService)
    }

    /**
     * Set the network client
     *
     * @param webUrl : Target Url to which connect for api service call
     */

    override fun setupNetworkClient(httpUrlString: String, cache: Cache, ignoreCache: Boolean) {
        val logging = HttpLoggingInterceptor()

        logging.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val okHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(logging)
            .addNetworkInterceptor(provideRemoteInterceptor())
            .addInterceptor(provideOfflineCacheInterceptor(ignoreCache))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .build()

        val factory = GsonConverterFactory.create()
        val rxAdapter =
            RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

        synchronized(RetrofitClient::class) {
            retrofit = Retrofit.Builder()
                .baseUrl(httpUrlString)
                .addConverterFactory(factory)
                .addCallAdapterFactory(rxAdapter)
                .client(okHttpClient)
                .build()
        }
    }

    private fun provideRemoteInterceptor(): Interceptor {
        return object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
                val originalResponse = chain.proceed(request)
                val cacheControl = originalResponse.header("Cache-Control")
                return if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains(
                        "no-cache"
                    ) ||
                    cacheControl.contains("must-revalidate") || cacheControl.contains("max-stale=0")
                ) {
                    val cc = CacheControl.Builder()
                        .maxStale(2, TimeUnit.DAYS)
                        .build()
                    request = request.newBuilder()
                        .cacheControl(cc)
                        .build()
                    chain.proceed(request)
                } else {
                    originalResponse
                }
            }
        }
    }

    private fun provideOfflineCacheInterceptor(ignoreCache: Boolean): Interceptor {
        if (ignoreCache) {
            return object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    return try {
                        val remoteRequest = chain.request().newBuilder()
                            .cacheControl(CacheControl.FORCE_NETWORK)
                            .addHeader("Cache-Control", "no-cache")
                            .build()
                        chain.proceed(remoteRequest)
                    } catch (e: Exception) {
                        val remoteRequest = chain.request().newBuilder()
                            .cacheControl(CacheControl.FORCE_NETWORK)
                            .addHeader("Cache-Control", "no-cache")
                            .build()
                        chain.proceed(remoteRequest)
                    }
                }
            }
        } else {
            return object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    return try {
                        chain.proceed(chain.request())
                    } catch (e: Exception) {
                        val cacheControl = CacheControl.Builder()
                            .onlyIfCached()
                            .maxStale(2, TimeUnit.HOURS)
                            .build()
                        val offlineRequest = chain.request().newBuilder()
                            .cacheControl(cacheControl)
                            .build()
                        chain.proceed(offlineRequest)
                    }
                }
            }
        }
    }

}