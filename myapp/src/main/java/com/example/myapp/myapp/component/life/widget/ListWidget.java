package com.example.myapp.myapp.component.life.widget;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapp.R;
import com.example.myapp.myapp.common.AppFlag;
import com.example.myapp.myapp.component.map.MapActivity;
import com.example.myapp.myapp.component.weather.WeatherActivity;
import com.example.myapp.myapp.data.api.MobApi;
import com.example.myapp.myapp.data.bean.FortuneResponse;
import com.example.myapp.myapp.data.http.HttpContext;
import com.example.myapp.myapp.ui.activity.MainActivity;
import com.example.myapp.myapp.ui.activity.VocalConcertTextActivity;
import com.example.myapp.myapp.ui.adapter.OnItemClickListener;
import com.example.myapp.myapp.ui.adapter.RecyclerAdapter;
import com.example.myapp.myapp.ui.adapter.RecyclerHolder;
import com.example.myapp.myapp.ui.dialog.EditTextDialog;
import com.example.myapp.myapp.ui.helper.UiHelper;
import com.example.myapp.myapp.utils.FlashUtils;
import com.example.myapp.myapp.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class ListWidget extends BaseWidget {


    private RecyclerView mRecyclerView;
    private List<Tab> mList = new ArrayList();
    private EditTextDialog mDialog = new EditTextDialog();
    private FlashUtils utils;

    public ListWidget(Context context) {
        super(context);
    }

    @Override
    public int getWidgetLayout() {
        return R.layout.widget_list;
    }

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            switch (position) {
                case 0:
                    ToastUtil.showApp("待开发！");
                    break;
                case 1:
                    UiHelper.skipWebActivity(mContext, "", "https://www.ickd.cn/");
                    break;
                case 2:
                    mDialog.showNow(((AppCompatActivity) mContext).getSupportFragmentManager(), "fortune");
                    mDialog.setTitleText("手机号查运势");
                    mDialog.setEditTextHint("请输入十一位手机号");
                    mDialog.setDialogConfirmCallback(new EditTextDialog.DialogConfirmCallback() {
                        @Override
                        public void confirm(String message) {
                            lookUp(message);
                        }
                    });
                    break;
                case 3:
                    mDialog.showNow(((AppCompatActivity) mContext).getSupportFragmentManager(), "edittext");  //此时用showNow方式，如果用show方法，可能导致还没有走完onViewCreated方法，导致下面代码造成空指针
                    mDialog.setTitleText("演唱会专用");
                    mDialog.setEditTextHint("你发如雪凄美了离别，我等待苍老了谁。");
                    mDialog.setDialogConfirmCallback(new EditTextDialog.DialogConfirmCallback() {
                        @Override
                        public void confirm(String message) {
                            UiHelper.skipToOtherActivityWithExtra(mContext, VocalConcertTextActivity.class, VocalConcertTextActivity.TEXT, message);
                        }
                    });
                    break;
                case 4:
                    utils.converse();
                    break;
                case 5:
                  UiHelper.skipActivityNofinish(mContext,MapActivity.class);
                    break;
                case 6:
                    UiHelper.skipActivityNofinish(mContext, WeatherActivity.class);
                    break;
                case 7:
                    ToastUtil.showApp("待开发！");
                    break;
            }
        }
    };

    @Override
    public void initView() {
        super.initView();
        mRecyclerView = mRootView.findViewById(R.id.recyclerView_widget);

    }

    @Override
    public void initData() {
        super.initData();
        utils = new FlashUtils(mContext);
        prepareData();
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        RecyclerAdapter<Tab> recyclerAdapter = new RecyclerAdapter<Tab>(R.layout.item_widget_list, mList, onItemClickListener) {
            @Override
            protected void onBindHolder(RecyclerHolder holder, Tab model, int position) {
                holder.text(R.id.text, model.text);
                ((ImageView) holder.getViewById(R.id.icon)).setImageResource(model.icon);
            }
        };
        mRecyclerView.setAdapter(recyclerAdapter);
    }

    public void prepareData() {
        mList.add(new Tab("今日事", R.mipmap.todaything));
        mList.add(new Tab("查快递", R.mipmap.kuaidi));
        mList.add(new Tab("查运势", R.mipmap.yunshi));
        mList.add(new Tab("演唱会", R.mipmap.singing));
        mList.add(new Tab("手电筒", R.mipmap.shoudian));
        mList.add(new Tab("找厕所", R.mipmap.toilet));
        mList.add(new Tab("看天气", R.mipmap.item_weather));
        mList.add(new Tab("不知道", R.mipmap.donkonw));
    }


    /**
     * 查询运势
     *
     * @param message
     */
    private void lookUp(String message) {
        HttpContext httpContext = new HttpContext();
        MobApi api = httpContext.createApi4(MobApi.class);
        httpContext.execute(api.getFortune(AppFlag.MOB_KEY, message), new HttpContext.Response<FortuneResponse>() {
            @Override
            public void success(FortuneResponse result) {
                if (result.getRetCode() == 200) {
                    Toast.makeText(mContext, result.getResult().getConclusion(), Toast.LENGTH_LONG).show();
                } else {
                    ToastUtil.showApp(result.getMsg());
                }
            }
        });

    }


    class Tab {

        public String text;
        public int icon;

        public Tab(String text, int icon) {
            this.text = text;
            this.icon = icon;
        }

    }
}

