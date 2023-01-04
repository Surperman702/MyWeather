package com.cxz.kotlin.baselibs.bean

import com.google.gson.annotations.SerializedName

/**
 * @author admin
 * @date 2018/11/21
 * @desc
 */
open class BaseBean {
    @SerializedName("code")
    var resultcode: Int = 0
    @SerializedName("msg")
    var message: String = ""
}