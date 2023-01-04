package com.cxz.kotlin.baselibs.utils

import android.content.Intent
import com.alibaba.android.arouter.launcher.ARouter
import com.cxz.kotlin.baselibs.aroute.AppRoute

object LogoutUtil {

    fun logout() {
        clearUserTag()
        ARouter.getInstance()
            .build(AppRoute.APP_ACTIVITY_LOGIN)
//            .withBoolean(AppRouteTag.RE_LOGIN_TAG, needReLogin)
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .navigation()
    }

    private fun clearUserTag() {
//        MmkvUtils.clearAll()
//        ServiceCreator.setToken("")
//        Constant.getInstance().token = null
    }

}