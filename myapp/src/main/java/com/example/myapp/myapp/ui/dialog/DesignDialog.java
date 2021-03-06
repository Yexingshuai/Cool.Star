package com.example.myapp.myapp.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.myapp.R;

import java.util.ArrayList;

import com.example.myapp.myapp.ui.adapter.DesginAdapter;
import com.example.myapp.myapp.data.bean.ThemeColor;

import com.example.myapp.myapp.utils.SPUtils;

/**
 * Created by yexing on 2018/5/8.
 */

public class DesignDialog extends AppCompatDialogFragment implements View.OnClickListener {
    private ArrayList<ThemeColor> themeColorList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);   //这句话 是为了去除标题栏
        View view = inflater.inflate(R.layout.dialog_desgin, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);

        setStyle(STYLE_NO_TITLE, getTheme());


    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Dialog dialog = getDialog();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
//        params.width = 200;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        window.setWindowAnimations(R.style.DialogStyle);
        dialog.onWindowAttributesChanged(params);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        themeColorList.add(new ThemeColor(R.color.theme_red_base));
        themeColorList.add(new ThemeColor(R.color.theme_blue));
        themeColorList.add(new ThemeColor(R.color.theme_blue_light));
        themeColorList.add(new ThemeColor(R.color.theme_balck));
        themeColorList.add(new ThemeColor(R.color.theme_teal));
        themeColorList.add(new ThemeColor(R.color.theme_brown));
        themeColorList.add(new ThemeColor(R.color.theme_green));
        themeColorList.add(new ThemeColor(R.color.theme_red));
        RecyclerView rv = view.findViewById(R.id.rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 4);
        rv.setLayoutManager(gridLayoutManager);
        final DesginAdapter desginAdapter = new DesginAdapter(themeColorList);
        rv.setAdapter(desginAdapter);
        desginAdapter.setOnItemClickListener(new DesginAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                for (ThemeColor themeColor : themeColorList) {
                    themeColor.setChosen(false);
                }
                themeColorList.get(position).setChosen(true);
                desginAdapter.notifyDataSetChanged();
            }
        });
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(this);

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
//        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;


        window.setWindowAnimations(R.style.DialogStyle);
        window.setAttributes(params);
        dialog.onWindowAttributesChanged(params);


//        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                dismiss();
                break;
        }
    }
}
