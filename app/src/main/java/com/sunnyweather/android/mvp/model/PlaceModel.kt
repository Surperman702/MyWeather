package com.sunnyweather.android.mvp.model

import com.cxz.kotlin.baselibs.http.HttpHelper
import com.cxz.kotlin.baselibs.http.HttpResult
import com.cxz.kotlin.baselibs.mvp.BaseModel
import com.sunnyweather.android.api.PlaceService
import com.sunnyweather.android.logic.dao.PlaceDao
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.logic.model.PlaceResponse
import com.sunnyweather.android.mvp.contract.PlaceContract
import io.reactivex.rxjava3.core.Observable

class PlaceModel : BaseModel(), PlaceContract.Model {

    private val apiService: PlaceService by lazy {
        HttpHelper.getRetrofit().create(PlaceService::class.java)
    }

    override fun isPlaceSaved(): Boolean {
        return PlaceDao.isPlaceSaved()
    }

    override fun getSavePlace(): Place {
        return PlaceDao.getSavedPlace()
    }

    override fun savePlace(place: Place) {
        PlaceDao.savePlace(place)
    }

    override fun searchPlaces(query: String): Observable<HttpResult<PlaceResponse>> {
        return apiService.searchPlaces(query)
    }

}