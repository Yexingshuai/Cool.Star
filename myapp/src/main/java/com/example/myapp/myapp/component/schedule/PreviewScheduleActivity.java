package com.example.myapp.myapp.component.schedule;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.data.api.WandroidApi;
import com.example.myapp.myapp.data.bean.ScheduleListResponse;
import com.example.myapp.myapp.data.bean.WanAndroidBaseResponse;
import com.example.myapp.myapp.data.http.HttpContext;
import com.example.myapp.myapp.ui.dialog.CalendarDialog;
import com.example.myapp.myapp.utils.ToastUtil;
import com.example.myapp.myapp.utils.Utils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class PreviewScheduleActivity extends BaseActivity {
    public static final String SCHEDULEINFO = "scheduleInfo";
    private ScheduleListResponse.DataBean.DatasBean mData;
    private TextView mTitle;
    private TextView mTime;
    private TextView mMonth;
    private TextView mContent;
    private TextView mWeek;
    private TextView mDay;
    private TextView mAddress;
    private TextView mContact;
    private RelativeLayout mDelete;

    @Override
    public int inflateContentView() {
        return R.layout.acitvity_preview_schedule;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        Utils.setDarkStatusIcon(this, true);
        ImageView img_close = getView(R.id.image_close);
        mTitle = getView(R.id.tv_title);
        mTime = getView(R.id.tv_time);
        mMonth = getView(R.id.tv_month);
        mContent = getView(R.id.tv_content);
        mWeek = getView(R.id.tv_week);
        mDay = getView(R.id.tv_day);
        mAddress = getView(R.id.tv_address);
        mContact = getView(R.id.tv_contact);
        mDelete = getView(R.id.rl_delete);
        setCommonClickListener(mDelete);
        setCommonClickListener(img_close);
    }

    @Override
    protected void initData() {
        mData = (ScheduleListResponse.DataBean.DatasBean) getIntent().getSerializableExtra(SCHEDULEINFO);
        mTitle.setText(mData.getTitle());
        String[] contents = mData.getContent().split("#");
        if (contents.length != 6) {
            return;
        }
        //正文
        if (!TextUtils.isEmpty(contents[0])) {
            mContent.setText(contents[0]);
        }

        //地址
        if (!TextUtils.isEmpty(contents[1])) {
            mAddress.setText(contents[1]);
        }

        //联系人
        if (TextUtils.isEmpty(contents[2])) {
            mContent.setText("暂无");
        } else {
            mContact.setText(contents[2]);
        }

        //是否全天
        if (TextUtils.equals(contents[3], "0")) {
            //全天
            mTime.setText("全天");
        } else {

        }


    }

    @Override
    protected boolean isNeedToBeSubscriber() {
        return true;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(Message msg) {
        if (mData.getId() == 0 || msg.what != CalendarDialog.CONFIRM_DELETE) {
            ToastUtil.showApp("请检查数据是否正确");
            return;
        }
        //删除日程
        HttpContext httpContext = new HttpContext();
        WandroidApi wandroidApi = httpContext.createApi(WandroidApi.class);
        httpContext.execute(wandroidApi.delTodo(mData.getId()), new HttpContext.Response<WanAndroidBaseResponse>() {
            @Override
            public void success(WanAndroidBaseResponse result) {
                if (result.getErrorCode() == 0) {
                    ToastUtil.showApp("删除成功");
                    setResult(1002);
                    finish();
                }

            }
        });
    }

    @Override
    protected void onClickImpl(View view) {
        super.onClickImpl(view);
        switch (view.getId()) {
            case R.id.image_close:
                finish();
                break;
            case R.id.rl_delete:
                CalendarDialog dialog = new CalendarDialog(this);
                dialog.show();
                break;
        }
    }
}
