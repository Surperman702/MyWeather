package com.sunnyweather.android.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.logic.model.Place

/**
 * <pre>
 *     @author : lvkuiliang
 *     @e-mail : 1943641461@qq.com
 *     @desc   : 定义ViewModel层,相当于逻辑层和UI层之间的一个桥梁
 *     @created: 2022/12/28 14:26
 * </pre>
 */
class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    // 定义了一个placeList集合，用于对界面上显示的城市数据进行缓存
    val placeList = ArrayList<Place>()

    // 并使用Transformations的switchMap()方法来观察这个对象，否则仓库层返回的LiveData对象将无法进行观察
    // 现在每当searchPlaces()函数被调用时，switchMap()方法所对应的转换函数就会执行。
    // 然后在转换函数中，我们只需要调用仓库层中定义的searchPlaces()方法就可以发起网络请求，
    // 同时将仓库层返回的LiveData对象转换成一个可供Activity观察的LiveData对象。
    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query)
    }

    // 将传入的搜索参数赋值给了一个searchLiveData对象
    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }

    fun savePlace(place: Place) = Repository.savePlace(place)

    fun getSavePlace() = Repository.getSavePlace()

    fun isPlaceSaved() = Repository.isPlaceSaved()

}