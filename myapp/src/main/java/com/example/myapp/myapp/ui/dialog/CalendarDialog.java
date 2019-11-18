package com.example.myapp.myapp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapp.R;

import org.greenrobot.eventbus.EventBus;

public class CalendarDialog extends Dialog implements View.OnClickListener {


    public Window win;
    public static final int CONFIRM_DELETE = 1005;


    public CalendarDialog(@NonNull Context context) {
        super(context);
//        super(context, R.style.common_dialog);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        win = getWindow();
        win.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//这句要加上，不加ui样式 差别很大

        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        win.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL);
        win.setAttributes(lp);

        setContentView(R.layout.calendar_del);
        setCanceledOnTouchOutside(true);
        setCancelable(true);


        //解决 状态栏变色的bug
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            win.setStatusBarColor(Color.TRANSPARENT);
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
//                try {
//                    Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
//                    Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
//                    field.setAccessible(true);
//                    field.setInt(getWindow().getDecorView(), Color.TRANSPARENT);  //去掉高版本蒙层改为透明
//                } catch (Exception e) {}
//            }
//        }


        initView();
    }


    private void initView() {

        RelativeLayout img_cancle = findViewById(R.id.img_cancle);
        img_cancle.setOnClickListener(this);
        TextView tv_del_cancle = findViewById(R.id.tv_del_cancle);
        tv_del_cancle.setOnClickListener(this);
        TextView calendar_del_ok = findViewById(R.id.calendar_del_ok);
        calendar_del_ok.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_del_cancle:
            case R.id.img_cancle:
                dismiss();
                break;
            case R.id.calendar_del_ok:
                Message message = new Message();
                message.what = CONFIRM_DELETE;
                EventBus.getDefault().post(message);
                dismiss();
                break;
        }
    }
}
