package com.example.myapp.myapp.component.life.qrcode;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.ui.dialog.QrCodeTextDialog;
import com.example.myapp.myapp.utils.ToastUtil;
import com.example.myapp.myapp.utils.VibratorUtil;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;


public class QRcodeActivity extends BaseActivity implements QRCodeView.Delegate  {


    private ZBarView mZBarView;

    private boolean isSHow = true;

    private QrCodeTextDialog qrCodeTextDialog;


    @Override
    public int inflateContentView() {
        return R.layout.activity_qr_code;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        mZBarView = findViewById(R.id.zbarview);
        mZBarView.setDelegate(this);
    }

    @Override
    protected void initData() {
        qrCodeTextDialog = new QrCodeTextDialog();
    }

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }


    @Override
    protected void onStart() {
        super.onStart();
        mZBarView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
//        mZBarView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别

        mZBarView.startSpotAndShowRect(); // 显示扫描框，并开始识别
    }

    @Override
    protected void onStop() {
        mZBarView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZBarView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        QrCodePresenter qrCodePresenter = new QrCodePresenter(this, result);
        boolean handler = qrCodePresenter.handler();
        if (!handler) {
            //暂时用dialog展示
            qrCodeTextDialog.showNow(this.getSupportFragmentManager(), "qrCode");
            qrCodeTextDialog.setContent_text(result);
            qrCodeTextDialog.setDialogConfirmCallback(new QrCodeTextDialog.DialogConfirmCallback() {
                @Override
                public void confirm() {
                    mZBarView.startSpot(); // 开始识别
                }
            });
        }
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        if (isDark && isSHow) {
            isSHow = false;
            ToastUtil.showApp("环境过暗，请打开闪光灯!");
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        ToastUtil.showApp("打开相机出错!");
    }

    private void vibrate() {
        VibratorUtil.Vibrate(this, 200);
    }
}
