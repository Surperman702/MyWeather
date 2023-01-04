package com.cxz.kotlin.baselibs.http

import com.cxz.kotlin.baselibs.contract.Constant
import com.cxz.kotlin.baselibs.utils.MmkvUtils
import com.tencent.mars.xlog.Log
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


object HttpHelper {

    private const val contentType: String = "application/json"

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(CommonApi.APP_DEFAULT_DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(getOkHttpClient())
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        return RetrofitUrlManager.getInstance().with(getUnsafeOkHttpClient())
            // token添加
            .addInterceptor(addHeaderInterceptor())
            .addNetworkInterceptor(addRequestLog())
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .build()
    }


    /**
     * 配置信任所有https证书
     */
    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }
            return builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }


    /**
     * 设置header token
     */
    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                // Provide your custom header here
                .header("Authorization", "Bearer ${MmkvUtils.decodeString(Constant.TOKEN)}")
                .header("Content-Type", contentType)
                .method(originalRequest.method, originalRequest.body)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    /**
     * 打印请求的参数 等信息
     */
    private fun addRequestLog(): Interceptor {
        return Interceptor { chain ->
            val requestStartTime = System.currentTimeMillis()
            val originalRequest = chain.request()
            val url = originalRequest.url.toString()
            val requestBody = getRequestBody(originalRequest)
            var response = chain.proceed(originalRequest)
            val responseBody = response.body?.string()
            val responseCode = response.code;
            val mediaType = response.body?.contentType();
            response =
                response.newBuilder().body(ResponseBody.create(mediaType, responseBody!!)).build()

            val requestEndTime = System.currentTimeMillis()

            val requestDuration = requestEndTime - requestStartTime
            Log.i(
                "netRequestInterceptorInfo",
                "requestUrl -> $url \n requestTime -> $requestDuration \n" +
                        "token -> ${MmkvUtils.decodeString(Constant.TOKEN)} \n requestParams-> $requestBody \n responseCode -> $responseCode \n result -> $responseBody"
            )
            response
        }
    }

    private fun getRequestBody(originalRequest: Request): String {
        if (originalRequest.body == null) {
            return ""
        }
        val buffer = Buffer()
        originalRequest.body!!.writeTo(buffer)
        val charset: Charset = Charset.forName("utf-8")
        return buffer.readString(charset)
    }

}