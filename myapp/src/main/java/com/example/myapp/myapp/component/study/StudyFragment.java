package com.example.myapp.myapp.component.study;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.component.login.helper.LoginContext;
import com.example.myapp.myapp.component.news.category.CategoryActivity;
import com.example.myapp.myapp.data.bean.BannerBean;
import com.example.myapp.myapp.data.bean.HomeItemBean;
import com.example.myapp.myapp.data.bean.KeyWordResponse;
import com.example.myapp.myapp.room.Injection;
import com.example.myapp.myapp.room.search.entity.SearchHistory;
import com.example.myapp.myapp.ui.activity.MainActivity;
import com.example.myapp.myapp.ui.adapter.HomeAdapter;
import com.example.myapp.myapp.ui.adapter.SpaceItemDecoration;
import com.example.myapp.myapp.ui.helper.GuidanceHelper;
import com.example.myapp.myapp.ui.view.CircleImageView;
import com.example.myapp.myapp.ui.view.SearchView;
import com.example.myapp.myapp.utils.ToastUtil;
import com.example.myapp.myapp.utils.VibratorUtil;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


/**
 * Created by yexing on 2018/3/28.
 */

public class StudyFragment extends BaseFragment implements StudyFragmentContract.View, View.OnClickListener, BaseActivity.TurnBackListener {


    private int[] imageResources = new int[]{
            R.drawable.squirrel,
            R.drawable.bear,
            R.drawable.bee,
            R.drawable.butterfly,
            R.drawable.cat,
            R.drawable.deer,
            R.drawable.dolphin,
            R.drawable.eagle,
            R.drawable.horse,
            R.drawable.elephant,
            R.drawable.owl,
            R.drawable.peacock,
            R.drawable.pig,
            R.drawable.rat,
            R.drawable.snake,
            R.drawable.bat
    };


    private RecyclerView mRecylerview;
    private RelativeLayout llTitleContainer;
    private int sumY = 0;

    //色值渐变工具类
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private float duration = 350f;
    private HomeAdapter homeAdapter;
    private ViewPager mViewPager;
    private SmartRefreshLayout mRefreshLayout;

    private int color = -1;
    private StudyFragmentContract.Presenter mPresenter;

    public static final int NAVIGATION_HIDE = 1001;
    public static final int NAVIGATION_SHOW = 1002;

    public int[] boomTitleNormal = {R.string.boom_tile1, R.string.boom_tile2, R.string.boom_tile3, R.string.boom_tile4};
    public int[] boomTitleText = {R.string.boom_tile5, R.string.boom_tile2, R.string.boom_tile3, R.string.boom_tile4};

    /**
     * 默认页码数
     */
    private static final int PAGE_NUMBER_DEFAULT = 1;

    /**
     * 页码
     */
    private int mPageNum = PAGE_NUMBER_DEFAULT;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        int currentPosition = mViewPager.getCurrentItem();
                        mViewPager.setCurrentItem(++currentPosition);
                        handler.sendEmptyMessageDelayed(1, 3500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private CircleImageView mHeadImg;
    private ImageView mSearch;
    private SearchView mSearchView;

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);//删除所有的消息，防止内存泄露
        mPresenter.stop();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_study;
    }


    public static StudyFragment newInstance() {
        return new StudyFragment();
    }


    @Override
    public void onAttach(Context context) {
        BaseActivity activity = (BaseActivity) context;
        activity.addOnTurnBackListener(this);
        super.onAttach(context);
        mPresenter.start();
    }

    @Override
    public void initView() {
        mRefreshLayout = getView(R.id.refreshLayout);
        mRecylerview = getView(R.id.rv);
        llTitleContainer = getView(R.id.title_bar);
        mHeadImg = getView(R.id.iv_head);
        mSearch = getView(R.id.iv_search);
        mSearchView = getView(R.id.searchView);
        mHeadImg.setOnClickListener(this);
        mSearch.setOnClickListener(this);


        //BoomMenu
        BoomMenuButton bmb = getView(R.id.boom_menu);
        bmb.setOnBoomListener(new OnBoomListener() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {

            }

            @Override
            public void onBackgroundClick() {

            }

            @Override
            public void onBoomWillHide() {

            }

            @Override
            public void onBoomDidHide() {

            }

            @Override
            public void onBoomWillShow() {
                VibratorUtil.Vibrate(getActivity(), 50);   //震动70ms
            }

            @Override
            public void onBoomDidShow() {

            }
        });

        for (int i = 0; i < bmb.getButtonPlaceEnum().buttonNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            // When the boom-button corresponding this builder is clicked.
                            Toast.makeText(getActivity(), "Clicked " + index, Toast.LENGTH_SHORT).show();
                        }
                    });

            builder.normalTextRes(R.string.boom_tile1)
                    .subNormalTextRes(boomTitleNormal[i])
                    .normalTextRes(boomTitleText[i])
                    .normalImageRes(imageResources[i]);
            bmb.addBuilder(builder);
        }
    }


    @Override
    public void initData() {
        //数据库搜索历史记录
        mPresenter.requestAllDatabase(Injection.provideLocalSearchDataSource(getActivity()));

        mSearchView.setEditTextListener(new SearchView.EditTextListener() {
            @Override
            public void editTextMessage(String message) {
                mPresenter.searchKeyWord(message);
            }
        });


        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPageNum = PAGE_NUMBER_DEFAULT;
                mPresenter.requestBannerAndStutyInfo(mPageNum);
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //加载更多功能的代码
                mPresenter.requestStudyInfo(++mPageNum);
            }
        });
        mRefreshLayout.autoRefresh();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mCtx, LinearLayoutManager.VERTICAL, false);
        mRecylerview.setLayoutManager(linearLayoutManager);
        mRecylerview.addItemDecoration(new SpaceItemDecoration(22));
        homeAdapter = new HomeAdapter(getActivity());
        homeAdapter.setButtonLikeListener(new HomeAdapter.ButtonLikeListener() {
            @Override
            public void like(int id) {
                if (LoginContext.getInstance().isLogined()) {
                    mPresenter.collectArtist(id);
                }
            }

            @Override
            public void unLike(int id) {
                if (LoginContext.getInstance().isLogined()) {
                    mPresenter.unCollect(id);
                }
            }
        });
        mRecylerview.setAdapter(homeAdapter);
        mRecylerview.setItemAnimator(new DefaultItemAnimator());
        mRecylerview.addOnScrollListener(new MyRecyclerViewScrooListener());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sumY = 0;
    }

    @Override
    protected boolean isNeedToBeSubscriber() {
        return true;
    }

    /**
     * 第一次可见加载数据
     */
    @Override
    protected void refreshData() {

    }


    /**
     * 接受EveneBus响应事件
     *
     * @param msg
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(Message msg) {
        if (msg.what == 1) {
            if (mViewPager == null) {
                StudyFragment.this.mViewPager = (ViewPager) msg.obj;
            }
            handler.removeCallbacksAndMessages(null);
            handler.sendEmptyMessageDelayed(1, 3500);

        } else if (msg.what == 2) {

        }
    }


    @Override
    public void setPresenter(StudyFragmentContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void setStudyInfo(HomeItemBean result) {
        mRefreshLayout.finishLoadMore();
        homeAdapter.addHomeInfo(result.getData().getDatas(), false);
    }

    @Override
    public void setBannerAndStudyInfo(Object result) {
        mRefreshLayout.finishRefresh();
        if (result instanceof BannerBean) {
            BannerBean bannerBean = (BannerBean) result;
            List<BannerBean.DataBean> datas = bannerBean.getData();
            homeAdapter.addBanner(datas);

        } else {
            HomeItemBean homeItem = (HomeItemBean) result;
            HomeItemBean.DataBean data = homeItem.getData();
            List<HomeItemBean.DataBean.DatasBean> datas = data.getDatas();
            homeAdapter.addHomeInfo(datas, true);
        }

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void collectSuccess() {
        ToastUtil.showApp("收藏成功");
    }

    @Override
    public void collectFail(String errorMsg) {
        ToastUtil.showApp(errorMsg);
    }

    @Override
    public void unCollectSuccess() {

    }

    /**
     * 取消收藏失败
     *
     * @param errorMsg
     */
    @Override
    public void unCollectFail(String errorMsg) {
        ToastUtil.showApp(errorMsg);
    }

    /**
     * 获取首页内容失败
     *
     * @param errorMsg
     */
    @Override
    public void requestBannerAndStudyInfoFail(String errorMsg) {
        mRefreshLayout.finishRefresh();
    }

    /**
     * 获取关键词搜索内容
     *
     * @param response
     */
    @Override
    public void setKeyWordInfo(KeyWordResponse response) {
        mSearchView.setKeyWordData(response);
    }

    /**
     * 获取历史内容
     *
     * @param list
     */
    @Override
    public void setSearchData(List<SearchHistory> list) {
        mSearchView.setSearchData(list, mPresenter);
    }

    /**
     * 清除历史成功
     */
    @Override
    public void deleteDatabaseSuccess() {
        mSearchView.deletaDatabaseSuccess();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_head:
                ((MainActivity) getActivity()).toggle();
                break;

            case R.id.iv_search:
                mSearchView.showView();
                break;
        }
    }

    @Override
    public boolean onTurnBack() {
        return mSearchView.onTurnBack();
    }


    class MyRecyclerViewScrooListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            //监听y轴,或者x轴上的偏移量
            sumY += dy;
            int bgColor = 0;
            if (sumY <= 0) {
                //顶部title显示的是透明颜色
                llTitleContainer.setVisibility(View.INVISIBLE);
            } else if (sumY > 350) {
                llTitleContainer.setBackgroundResource(R.color.title_bar);
                llTitleContainer.setVisibility(View.VISIBLE);
            } else {
                //滚动过程中修改顶部的颜色值
                bgColor = (int) argbEvaluator.evaluate(sumY / duration, Color.TRANSPARENT, Color.parseColor("#00c3a6"));
                llTitleContainer.setVisibility(View.VISIBLE);
                llTitleContainer.setBackgroundColor(bgColor);
            }

            //控制底部导航栏隐藏或展示
            if (dy > 30) {//up -> hide
                Message message = new Message();
                message.what = NAVIGATION_HIDE;
                EventBus.getDefault().post(message);
                if (mSearchView.isShown()) {
                    mSearchView.hideView();
                }

            } else if (dy < -30) {//down -> show
                Message message = new Message();
                message.what = NAVIGATION_SHOW;
                EventBus.getDefault().post(message);
                if (mSearchView.isShown()) {
                    mSearchView.hideView();
                }
            }
            super.onScrolled(recyclerView, dx, dy);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            //滚动状态变化
//                RecyclerView.SCROLL_STATE_SETTLING    惯性滑动
//                RecyclerView.SCROLL_STATE_IDLE        滚动空闲(滚动---->停止)
//                RecyclerView.SCROLL_STATE_DRAGGING    拖拽recyclerView滚动

            if (newState == RecyclerView.SCROLL_STATE_IDLE && sumY > 300) {
                //执行引导动画
                GuidanceHelper.guide(getContext(), mHeadImg, "mHeadImg", "点击我展开侧拉菜单哦！\n 手指侧滑也可以的。");
            }
            super.onScrollStateChanged(recyclerView, newState);

        }


    }
}