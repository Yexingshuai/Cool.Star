package com.example.myapp.myapp.utils;

import android.content.Context;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

/**
 * Created by yexing on 2018/4/9.
 */

public class FingerUtils {

    /**
     * 是否支持指纹验证
     */
    public  static  boolean isSupportFinger(Context  mCtx) {
        FingerprintManagerCompat fingerprint = FingerprintManagerCompat.from(mCtx);
        return fingerprint.isHardwareDetected();
    }


    /**
     * 是否包含指纹信息
     */
    public  static boolean  isContainFingerInfo(Context mCtx) {
        FingerprintManagerCompat fingerprint = FingerprintManagerCompat.from(mCtx);
        return  fingerprint.hasEnrolledFingerprints(); //判断设备是否以保存过指纹信息，至少需要保存过一个
    }
}
