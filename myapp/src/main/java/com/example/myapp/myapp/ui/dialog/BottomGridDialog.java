package com.example.myapp.myapp.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.myapp.R;

import java.util.ArrayList;
import java.util.List;

public class BottomGridDialog extends Dialog {

    public static Context mContext;

    private static List<BottomListMenuItem> btnMenu;
    private static String centerIconKey;
    private static boolean needKey = true;
    private static boolean needTopTitle = true;
    private ImageView ivTopIcon;
    private ImageView ivCancel;
    private RelativeLayout rlContainer;
    private RelativeLayout rlTop;
    public Window win;

    public static class BottomListMenuItem {
        private String item;


        public BottomListMenuItem(String item) {
            this.item = item;
        }


        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }
    }

    public BottomGridDialog(@NonNull Context context) {
        super(context, R.style.common_dialog);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        win = getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        win.setGravity(Gravity.BOTTOM);
        win.setAttributes(lp);

        setContentView(R.layout.cv_layout_calendar_view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        initView();
    }


    private void initView() {


        //findView
        if (!needTopTitle) {
            ivTopIcon.setVisibility(View.INVISIBLE);
        }
    }

    public static class Builder {

        public Builder(Context context) {
            mContext = context;
            btnMenu = new ArrayList<BottomListMenuItem>();
        }

        public Builder setNeedKey(boolean needKey) {
            BottomGridDialog.needKey = needKey;
            return this;
        }

        public Builder setNeedTopTitle(boolean needTopTitle) {
            BottomGridDialog.needTopTitle = needTopTitle;
            return this;
        }

        /**
         * @param item 对象
         */
        public Builder addMenuItem(BottomListMenuItem item) {
            btnMenu.add(item);
            return this;
        }

        public Builder setCenterIcon(String iconKey) {
            centerIconKey = iconKey;
            return this;
        }

        /**
         * @param mReportList 数组
         */
        public Builder addMenuListItem(List<String> mReportList) {
            BottomListMenuItem item = null;
            for (int i = 0; i < mReportList.size(); i++) {
                item = new BottomListMenuItem(mReportList.get(i));
                btnMenu.add(item);
            }
            return this;
        }

        public BottomGridDialog show() {
            if (null != mContext && (!(mContext instanceof Activity) || !((Activity) mContext).isFinishing())) {
                BottomGridDialog dialog = new BottomGridDialog(mContext);
                dialog.show();
                return dialog;
            }
            return null;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        btnMenu = null;
        mContext = null;
    }


}
