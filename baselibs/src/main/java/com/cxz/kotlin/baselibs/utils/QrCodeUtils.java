package com.cxz.kotlin.baselibs.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 互慧his 区分三码
 * <p>
 * 检验条码为纯数字的 12位长度
 * 医嘱条码为纯数字的 16位长度
 * 其余为患者腕带条码
 */
public class QrCodeUtils {


    private static QrCodeUtils instance;

    private static final int YZ_LENGTH = 16;
    private static final int JZ_LENGTH = 12;

    private String rules = "^[0-9]*$";

    private Pattern p;


    private QrCodeUtils() {
        p = Pattern.compile(rules);
    }

    public static QrCodeUtils getInstance() {
        if (instance == null) {
            synchronized (QrCodeUtils.class) {
                if (instance == null) {
                    instance = new QrCodeUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 互慧his 区分三码
     * <p>
     * 检验条码为纯数字的 12位长度
     * 医嘱条码为纯数字的 16位长度
     * 其余为患者腕带条码
     *
     * @param qrCode
     * @return
     */
    public QrType matchType(String qrCode) {
        if (TextUtils.isEmpty(qrCode)) {
            throw new RuntimeException("qrCode is empty");
        }
        Matcher matcher = p.matcher(qrCode);
        if (matcher.matches()) {
            if (qrCode.length() == YZ_LENGTH) {
                return QrType.YZ_CODE;
            } else if (qrCode.length() == JZ_LENGTH) {
                return QrType.JY_CODE;
            } else {
                return QrType.WD_CODE;
            }
        } else {
            return QrType.WD_CODE;
        }
    }


    public enum QrType {
        /**
         * 检验码
         */
        JY_CODE,
        /**
         * 腕带码
         */
        WD_CODE,
        /**
         * 医嘱码
         */
        YZ_CODE
    }


}
