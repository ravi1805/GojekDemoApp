package com.gojek.sample.service

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.gson.reflect.TypeToken
import io.reactivex.ObservableEmitter
import okhttp3.Cache
import okhttp3.ResponseBody
import retrofit2.Callback
import java.io.File
import java.util.*
import javax.inject.Inject

/**
 * The NetworkClientFactory program implements an application that
 * setup the network client based on client type,
 * get the current object of network client and also it provide the functionlist to check is
 * network available or not in application
 */
class NetworkClientFactory @Inject constructor(private val context: Context) :
    INetworkClientService {

    lateinit var networkClient: NetworkClient

    override fun <T> getNetworkClient(apiServiceClass: Class<T>): T {
        return networkClient.getNetworkClient(apiServiceClass)
    }

    /**
     * Setup the network client
     * @param pref: user preference
     *
     */
    override fun setupNetworkClient(urlString: String) {
        networkClient = RetrofitClient()
        networkClient.setupNetworkClient(urlString, getCache())

    }

    private fun getCache(): Cache {
        val cacheSize = (10 * 1024 * 1024).toLong()
        val cacheDir = File(System.getProperty("java.io.tmpdir"), "offlineCache")
        return Cache(cacheDir, cacheSize)

    }

    /**
     * It will return the status of current network
     * whether is connected or not to wifi/mobile network
     *
     * @return true : if connected
     */
    override fun isMobileNetworkConnected(): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            ))
        } else {
            connectivityManager?.activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI
        }

    }

    override fun <R> getJsonCallback(
        emitter: ObservableEmitter<R>,
        resCls: Class<R>
    ): Callback<ResponseBody> {
        return CloudApiRetrofitJsonCallback(emitter, resCls)
    }


    override fun <R> getJsonCallback(
        emitter: ObservableEmitter<List<R>>,
        listOfTypeToken: TypeToken<out List<R>>
    ): Callback<ResponseBody> {
        return CloudArrayApiRetrofitJsonCallback(emitter, listOfTypeToken)
    }

}