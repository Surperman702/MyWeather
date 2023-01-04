package com.sunnyweather.android.mvp.presenter

import com.cxz.kotlin.baselibs.ext.ss
import com.cxz.kotlin.baselibs.mvp.BasePresenter
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.mvp.contract.PlaceContract
import com.sunnyweather.android.mvp.model.PlaceModel

class PlacePresenter : BasePresenter<PlaceContract.Model, PlaceContract.View>(),
    PlaceContract.Presenter {

    override fun createModel(): PlaceContract.Model = PlaceModel()

    override fun isPlaceSaved() {
        mModel?.isPlaceSaved()
    }

    override fun getSavePlace() {
        mModel?.getSavePlace()
    }

    override fun savePlace(place: Place) {
        mModel?.savePlace(place)
    }

    override fun searchPlaces(query: String) {
        mModel?.searchPlaces(query)?.ss(mModel, mView, onSuccess = {
            mView?.onSearchPlacesResult(it.data)
        }, onError = {
            mView?.onSearchPlacesError(it.message)
        })
    }

}