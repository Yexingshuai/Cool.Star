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

public class EditTextDialog extends AppCompatDialogFragment implements View.OnClickListener {


    private DialogConfirmCallback mCallback;
    private EditText edit_text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_editext, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edit_text = view.findViewById(R.id.edit_text);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                if (mCallback != null) {
                    if (TextUtils.isEmpty(edit_text.getText().toString())) {
                        mCallback.confirm(edit_text.getHint().toString());
                    } else {
                        mCallback.confirm(edit_text.getText().toString());
                    }
                }
                dismiss();
                break;
        }
    }

    public void setDialogConfirmCallback(DialogConfirmCallback callback) {

        mCallback = callback;
    }

    public interface DialogConfirmCallback {

        void confirm(String message);
    }
}
