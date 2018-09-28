package com.example.myapp.myapp.component.life;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.data.bean.JokeResponse;
import com.example.myapp.myapp.ui.activity.VocalConcertTextActivity;
import com.example.myapp.myapp.ui.adapter.LifeHeadAdapter;
import com.example.myapp.myapp.ui.adapter.SwipeFlingAdater;
import com.example.myapp.myapp.ui.dialog.EditTextDialog;
import com.example.myapp.myapp.ui.flingswipe.SwipeFlingAdapterView;
import com.example.myapp.myapp.ui.helper.UiHelper;
import com.example.myapp.myapp.ui.load.LoadingStatusLayout;
import com.example.myapp.myapp.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yexing on 2018/3/28.
 */

public class LifeFragment extends BaseFragment implements LifeFragmentContract.View, View.OnClickListener {


    private ViewPager vp_module;
    private RecyclerView mRecyclerView;
    private LifeFragmentContract.Presenter mPresenter;
    private int pageSize = 20;
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
        swipeFlingAdapterView = getView(R.id.swipe);
        RelativeLayout singing = getView(R.id.rl_singing);
        singing.setOnClickListener(this);
        mDialog.setDialogConfirmCallback(new EditTextDialog.DialogConfirmCallback() {
            @Override
            public void confirm(String message) {
                UiHelper.skipToOtherActivityWithExtra(getActivity(), VocalConcertTextActivity.class, VocalConcertTextActivity.TEXT, message);
            }
        });

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
            case R.id.rl_singing:
                mDialog.show(getFragmentManager(), "edittext");
                break;
        }
    }
}
