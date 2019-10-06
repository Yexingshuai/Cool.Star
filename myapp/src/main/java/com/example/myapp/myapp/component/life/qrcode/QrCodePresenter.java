package com.example.myapp.myapp.component.life.qrcode;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.example.myapp.myapp.ui.helper.UiHelper;

public class QrCodePresenter {

    private String mResult;

    private Activity mContext;

    private String http = "http://";

    private String https = "https://";


    public QrCodePresenter(Context context, String result) {
        this.mResult = result;
        this.mContext = (Activity) context;
    }

    /**
     * 解析二维码
     */
    public boolean handler() {
        if (TextUtils.isEmpty(mResult)) {
            return false;
        }


        /**
         * 网址
         */
        if (mResult.startsWith(http) || mResult.startsWith(https)) {
            UiHelper.skipWebActivity(mContext, "", mResult);
            return true;
        } else {
            return false;
        }
    }
}

