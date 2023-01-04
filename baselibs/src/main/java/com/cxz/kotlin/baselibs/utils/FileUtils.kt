package com.cxz.kotlin.baselibs.utils

import android.content.Context
import android.os.Build
import android.os.Environment
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

object FileUtils {

    /**
     * 设备号存储的文件名字
     */
    private const val devicesFile = "huh.DEVICE"
    private const val devicesFolder = "devices"

    /**
     * 获取外置sd卡的存储路径下的文件   需要权限
     */
    fun getExternalDevicesFile(): File {
        val file = if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .toString() + File.separator + devicesFolder
            )
        } else {
            File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                    .toString() + File.separator + devicesFolder
            )
        }

        if (!file.exists()) {
            file.mkdirs()
        }

        val devicesFile = File(file.toString() + File.separator + devicesFile)
        if (!devicesFile.exists()) {
            try {
                devicesFile.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return devicesFile
    }

    /**
     * 获取app内部存储文件 不需要权限
     */
    fun getInnerDevicesFile(context: Context): File {
        val file = File(context.filesDir.path + File.separator + devicesFolder)
        if (!file.exists()) {
            file.mkdirs()
        }
        val devicesFile = File(file.toString() + File.separator + devicesFile)
        if (!devicesFile.exists()) {
            try {
                devicesFile.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return devicesFile
    }

    /**
     * 读取文件中的内容
     */
    fun getDeviceIdFromFile(file: File): String {
        var ins: InputStream = file.inputStream()
        return ins.readBytes().toString(Charset.defaultCharset())
    }

    /**
     * 将内容写入文件
     */
    fun writeDeviceIdToFile(imei: String, file: File) {
        file.bufferedWriter(Charset.defaultCharset()).use { out ->
            out.write(imei)
        }
    }


}

