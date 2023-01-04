package com.cxz.kotlin.baselibs.http

import com.cxz.kotlin.baselibs.contract.Constant
import com.cxz.kotlin.baselibs.utils.MmkvUtils

interface CommonApi {
    companion object {
        val APP_DEFAULT_DOMAIN: String
            get() = if (MmkvUtils.decodeString(Constant.URL_INFO).isNullOrBlank()) {
                Constant.DEFAULT_BASE_URL
            } else MmkvUtils.decodeString(Constant.URL_INFO)!!
    }
}