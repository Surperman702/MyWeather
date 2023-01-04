package com.cxz.kotlin.baselibs.http

import com.cxz.kotlin.baselibs.bean.BaseBean
import com.google.gson.annotations.SerializedName


data class HttpResult<T>(@SerializedName("result")val data: T) : BaseBean()

data class Items<T>(val item: MutableList<T>)