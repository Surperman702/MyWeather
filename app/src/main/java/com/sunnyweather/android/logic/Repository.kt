package com.sunnyweather.android.logic

import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers

/**
 * <pre>
 *     @author : lvkuiliang
 *     @e-mail : 1943641461@qq.com
 *     @desc   : 仓库层
 *     @created: 2022/12/28 14:13
 * </pre>
 */
object Repository {

    // liveData()函数可以自动构建并返回一个LiveData对象，然后在它的代码块中提供一个挂起函数的上下文，
    // 这样我们就可以在liveData()函数的代码块中调用任意的挂起函数了。
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) { // Dispatchers.IO，这样代码块中的所有代码就都运行在子线程中了
        val result = try {
            // 调用了SunnyWeatherNetwork的searchPlaces()函数来搜索城市数据
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            // 如果服务器响应的状态是ok，那么就使用Kotlin内置的Result.success()方法来包装获取的城市数据列表，
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else { // 否则使用Result.failure()方法来包装一个异常信息
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<Place>(e)
        }
        // 最后使用一个emit()方法将包装的结果发射出去，这个emit()方法其实类似于调用LiveData的setValue()方法来通知数据变化，
        // 只不过这里我们无法直接取得返回的LiveData对象
        emit(result)
    }

}