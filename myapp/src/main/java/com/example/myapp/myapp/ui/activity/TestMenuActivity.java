package com.example.myapp.myapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.ui.view.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by daixiankade on 2018/7/19.
 */

public class TestMenuActivity extends BaseActivity {
    private String headers[] = {"AA", "BB"};
    private List<View> popupViews = new ArrayList<>();
    private DropDownMenu mDownMenu;

    @Override
    public int inflateContentView() {
        return R.layout.drop_layout;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        mDownMenu = findViewById(R.id.dropDownMenu);
        View view1 = LayoutInflater.from(this).inflate(R.layout.filter_drop_layout, null);
        popupViews.add(view1);
        View view = getLayoutInflater().inflate(R.layout.custom_drop_layout, null);
        popupViews.add(view);

        TextView contentView = new TextView(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setText("内容显示区域");
        contentView.setGravity(Gravity.CENTER);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        mDownMenu.setDropDownMenu(Arrays.asList(headers),popupViews,contentView);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }




}
