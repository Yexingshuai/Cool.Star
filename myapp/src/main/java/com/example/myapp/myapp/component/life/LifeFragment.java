package com.example.myapp.myapp.component.life;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.common.AppFlag;
import com.example.myapp.myapp.data.api.MobApi;
import com.example.myapp.myapp.data.bean.FortuneResponse;
import com.example.myapp.myapp.data.bean.JokeResponse;
import com.example.myapp.myapp.data.http.HttpContext;
import com.example.myapp.myapp.ui.activity.VocalConcertTextActivity;
import com.example.myapp.myapp.ui.adapter.LifeHeadAdapter;
import com.example.myapp.myapp.ui.adapter.SwipeFlingAdater;
import com.example.myapp.myapp.ui.dialog.EditTextDialog;
import com.example.myapp.myapp.ui.flingswipe.SwipeFlingAdapterView;
import com.example.myapp.myapp.ui.helper.UiHelper;
import com.example.myapp.myapp.ui.load.LoadingStatusLayout;
import com.example.myapp.myapp.ui.view.FunctionView;
import com.example.myapp.myapp.utils.FlashUtils;
import com.example.myapp.myapp.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yexing on 2018/3/28.
 */

public class LifeFragment extends BaseFragment implements LifeFragmentContract.View, View.OnClickListener {


    private LifeFragmentContract.Presenter mPresenter;
    private final int pageSize = 40;
    private int pageNum = 1;
    private List<JokeResponse.ResultBean.DataBean> jokeList = new ArrayList<>();
    private EditTextDialog mDialog = new EditTextDialog();
    /**
     * 默认页码数
     */
    private static final int PAGE_NUMBER_DEFAULT = 1;
    private LifeHeadAdapter lifeHeadAdapter;
    private SwipeFlingAdapterView swipeFlingAdapterView;
    private SwipeFlingAdater swipeFlingAdater;
    private LoadingStatusLayout loadingStatusLayout;
    private FlashUtils utils;


    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }

    @Override
    protected void refreshData() {
        mPresenter.requestJoke(pageNum, pageSize);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_life;
    }

    @Override
    public void initView() {
        utils = new FlashUtils(getActivity());
        swipeFlingAdapterView = getView(R.id.swipe);
        FunctionView singing = getView(R.id.func_singing);
        FunctionView express = getView(R.id.func_express);
        FunctionView fortune = getView(R.id.func_fortune);
        FunctionView flashlight = getView(R.id.func_flashlight);
        flashlight.setOnClickListener(this);
        fortune.setOnClickListener(this);
        singing.setOnClickListener(this);
        express.setOnClickListener(this);
    }


    @Override
    public void initData() {
        mPresenter.requestJoke(pageNum, pageSize);
        swipeFlingAdater = new SwipeFlingAdater(getActivity(), jokeList);
        swipeFlingAdapterView.setAdapter(swipeFlingAdater);
        swipeFlingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                jokeList.remove(0);
                swipeFlingAdater.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
            }

            @Override
            public void onRightCardExit(Object dataObject) {

            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                if (itemsInAdapter == 1) {
                    mPresenter.requestJoke(++pageNum, pageSize);
                }
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });

    }

    @Override
    public void setPresenter(LifeFragmentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setJokeInfo(JokeResponse response) {
        jokeList.clear();
        jokeList.addAll(response.getResult().getData());
        swipeFlingAdater.notifyDataSetChanged();
    }

    /**
     * 请求失败
     *
     * @param errorMsg
     */
    @Override
    public void requestJokeFail(String errorMsg) {
        ToastUtil.showApp(errorMsg);
    }


    public static LifeFragment newInstance() {
        return new LifeFragment();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.func_singing:
                mDialog.showNow(getFragmentManager(), "edittext");  //此时用showNow方式，如果用show方法，可能导致还没有走完onViewCreated方法，导致下面代码造成空指针
                mDialog.setTitleText("演唱会专用");
                mDialog.setEditTextHint("你发如雪凄美了离别，我等待苍老了谁。");
                mDialog.setDialogConfirmCallback(new EditTextDialog.DialogConfirmCallback() {
                    @Override
                    public void confirm(String message) {
                        UiHelper.skipToOtherActivityWithExtra(getActivity(), VocalConcertTextActivity.class, VocalConcertTextActivity.TEXT, message);
                    }
                });
                break;
            case R.id.func_express:
                UiHelper.skipWebActivity(getContext(), "", "https://www.ickd.cn/");
                break;
            case R.id.func_fortune:
                mDialog.showNow(getFragmentManager(), "fortune");
                mDialog.setTitleText("手机号查运势");
                mDialog.setEditTextHint("请输入十一位手机号");
                mDialog.setDialogConfirmCallback(new EditTextDialog.DialogConfirmCallback() {
                    @Override
                    public void confirm(String message) {
                        lookUp(message);
                    }
                });
                break;
            case R.id.func_flashlight:
                utils.converse();
                break;
        }
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
                    Toast.makeText(getActivity(), result.getResult().getConclusion(), Toast.LENGTH_LONG).show();
                } else {
                    ToastUtil.showApp(result.getMsg());
                }
            }
        });

    }
}
