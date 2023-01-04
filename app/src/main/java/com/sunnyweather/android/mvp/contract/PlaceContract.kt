package com.sunnyweather.android.mvp.contract

import com.cxz.kotlin.baselibs.http.HttpResult
import com.cxz.kotlin.baselibs.mvp.IModel
import com.cxz.kotlin.baselibs.mvp.IPresenter
import com.cxz.kotlin.baselibs.mvp.IView
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.logic.model.PlaceResponse
import io.reactivex.rxjava3.core.Observable

interface PlaceContract {

    interface View : IView {

        fun onSearchPlacesResult(data: PlaceResponse?)

        fun onSearchPlacesError(msg: String)

    }

    interface Presenter : IPresenter<View> {
        fun isPlaceSaved()

        fun getSavePlace()

        fun savePlace(place: Place)

        fun searchPlaces(query: String)
    }

    interface Model : IModel {
        fun isPlaceSaved(): Boolean

        fun getSavePlace(): Place

        fun savePlace(place: Place)

        fun searchPlaces(query: String): Observable<HttpResult<PlaceResponse>>
    }

}