package com.sunnyweather.android.logic.network

import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
/**
 * <pre>
 *     @author : lvkuiliang
 *     @e-mail : 1943641461@qq.com
 *     @desc   : 用于访问彩云天气城市搜索API的Retrofit接口
 *     @created: 2022/12/28 13:32
 * </pre>
 */
interface PlaceService {

    /**
     * 搜索城市数据的API中只有query这个参数是需要动态指定的，我们使用@Query注解的方式来进行实现
     */
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>

}