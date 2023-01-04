package com.cxz.kotlin.baselibs.utils

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

object SysUtils {
    /**
     * 获取进程名字
     */
    fun getProcessName(): String? {
        return try {
            val file = File("/proc/" + android.os.Process.myPid() + "/" + "cmdline")
            val bufferedReader = BufferedReader(FileReader(file))
            val processName = bufferedReader.readLine().trim { it <= ' ' }
            bufferedReader.close()
            processName
        } catch (e: Exception) {
            null
        }
    }



}