package com.example.myapp.myapp.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.myapp.utils.Utils;

/**
 * Created by yexing on 2018/9/19.
 */

public class QrCodeTextDialog extends AppCompatDialogFragment implements View.OnClickListener {


    private TextView content_text;

    private DialogConfirmCallback mCallback;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_qrcode, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        content_text = view.findViewById(R.id.content_text);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Dialog dialog = getDialog();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        DisplayMetrics metrics = Utils.getMetrics(getActivity());

//        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = (int) (metrics.widthPixels * 0.8);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        dialog.onWindowAttributesChanged(params);
    }

    public void setContent_text(String text) {
        content_text.setText(text);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                dismiss();
                if (mCallback != null) {
                    mCallback.confirm();
                }
                break;
        }
    }

    public void setDialogConfirmCallback(DialogConfirmCallback callback) {

        mCallback = callback;
    }

    public interface DialogConfirmCallback {

        void confirm();
    }

}
