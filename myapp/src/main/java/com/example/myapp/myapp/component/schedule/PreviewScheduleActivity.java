package com.example.myapp.myapp.component.schedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.data.bean.ScheduleListResponse;
import com.example.myapp.myapp.utils.Utils;

public class PreviewScheduleActivity extends BaseActivity {
    public static final String SCHEDULEINFO = "scheduleInfo";
    private ScheduleListResponse.DataBean.DatasBean mData;

    @Override
    public int inflateContentView() {
        return R.layout.acitvity_preview_schedule;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        Utils.setDarkStatusIcon(this, true);
        ImageView img_close = getView(R.id.image_close);
        setCommonClickListener(img_close);
    }

    @Override
    protected void initData() {
        mData = (ScheduleListResponse.DataBean.DatasBean) getIntent().getSerializableExtra(SCHEDULEINFO);
    }

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }

    @Override
    protected void onClickImpl(View view) {
        super.onClickImpl(view);
        switch (view.getId()) {
            case R.id.image_close:
                finish();
                break;
        }
    }
}
