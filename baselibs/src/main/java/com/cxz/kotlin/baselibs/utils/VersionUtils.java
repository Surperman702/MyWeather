package com.cxz.kotlin.baselibs.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.tencent.mars.xlog.Log;

/**
 * <pre>
 *     @author : yitouniu
 *     @time   : 2021/05/14
 *     @desc   :
 * </pre>
 */
public class VersionUtils {

    /**
     * 版本code  用于 更新版本
     *
     * @param mContext
     * @return versionCode
     */
    public static int getVersionCode(Context mContext) {
        int versionCode = 1;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 版本名称  用于展示给用户
     *
     * @param context
     * @return versionName
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    private static final String SPLIT_POINT = "\\.";

    /**
     * 比较服务器的版本号 和本地的版本号
     *
     * @param remoteVersion 服务器版本
     * @return 是否有更新
     */
    public static boolean compareVersion(String remoteVersion, String localVersionName) {
        if (TextUtils.isEmpty(remoteVersion) || TextUtils.isEmpty(localVersionName)) {
            return false;
        }
        String[] localArr = localVersionName.split(SPLIT_POINT);
        String[] remoteArr = remoteVersion.split(SPLIT_POINT);

        // 服务器版本号长度小于本地的 无更新
        if (localArr.length > remoteArr.length) {
            return false;
        }
        // 服务器的版本号长度大于本地的 有更新
        if (remoteArr.length > localArr.length) {
            return true;
        }
        // 长度一致的时候 遍历判断大小
        for (int i = 0; i < remoteArr.length; i++) {
            long remoteNo = Long.parseLong(remoteArr[i]);
            long localNo = Long.parseLong(localArr[i]);
            if (remoteNo < localNo) {
                return false;
            } else if (remoteNo > localNo) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取manifest  节点中的 meta_data 中的的高德的值
     *
     * @param context
     * @return
     */
    public static String readMetaDataFromApplication(Context context) {
        String amapInfo = null;
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            amapInfo = appInfo.metaData.getString("com.amap.api.v2.apikey");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return amapInfo;
    }


}
