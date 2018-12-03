package com.example.myapp.myapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.myapp.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

/**
 * Created by yexing on 2018/11/27.
 */

public class PermissonUtil {

    /**
     * 请求多个权限
     *
     * @param context
     * @param permisson
     */
    public static void requestPermissons(Context context, String... permisson) {

        Dexter.withActivity((Activity) context).withPermissions(permisson).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        });

    }

    /**
     * 请求单个权限
     *
     * @param context
     * @param permisson
     */
    public static void requestPermisson(final Context context, final String permisson, final String permissonName, final PermissonListener permissonListener) {
        Dexter.withActivity((Activity) context).withPermission(permisson).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                permissonListener.onPermissonGranted();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setCancelable(false);
                dialog.create().setCanceledOnTouchOutside(false);
                dialog.setTitle(context.getString(R.string.permissonTitle));
                dialog.setMessage(permissonName + context.getString(R.string.permisson));
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        permissonListener.onPermissonDenied();
                    }
                });
                dialog.show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, final PermissionToken token) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setCancelable(false);
                dialog.create().setCanceledOnTouchOutside(false);
                dialog.setTitle(context.getString(R.string.permissonTitle));
                dialog.setMessage(permissonName + context.getString(R.string.permisson));
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        token.cancelPermissionRequest();
                    }
                });
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        token.continuePermissionRequest();
                    }
                });
                dialog.show();
            }
        }).check();
    }

    public interface PermissonListener {

        void onPermissonGranted();

        void onPermissonDenied();
    }
}
