package fragment;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myapp.R;
import com.example.myapp.ui.FilmDetailActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import adapter.HomeAdapter;
import adapter.SpaceItemDecoration;
import base.BaseFragment;
import bean.BannerBean;
import bean.HomeItemBean;
import http.HttpContext;
import http.HttpUtil;
import utils.SPUtils;


/**
 * Created by daixiankade on 2018/3/28.
 */

public class StudyFragment extends BaseFragment {


    private RecyclerView rv;
    private RelativeLayout llTitleContainer;
    private int sumY = 0;

    //色值渐变工具类
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private float duration = 350f;
    private HomeAdapter homeAdapter;
    private SwipeRefreshLayout swipe;
    private static int index = 1;
    private HttpContext httpContext;
    private HttpUtil httpUtil;
    private ViewPager vp;


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        int currentPosition = vp.getCurrentItem();
                        vp.setCurrentItem(++currentPosition);
                        handler.sendEmptyMessageDelayed(1, 3500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private int color=-1;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_study;
    }


    @Override
    public void initData() {
        llTitleContainer.setBackgroundColor(getResources().getColor(R.color.theme_red_base));
        rv.setVisibility(View.VISIBLE);
        httpContext = new HttpContext();
        httpUtil = httpContext.createApi(HttpUtil.class);
        httpContext.execute(httpUtil.getBanner(), new HttpContext.Response<BannerBean>() {
            @Override
            public void success(BannerBean banner) {
                if (banner != null) {
                    if (homeAdapter == null) {
                        homeAdapter = new HomeAdapter(mCtx);
                        homeAdapter.addBanner(banner);
                    }
                    requestHomeData();
                }
            }

            @Override
            public void fail(String error) {
                Toast.makeText(mCtx, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestHomeData() {
        //首页请求
        swipe.setRefreshing(true);
        httpContext.execute(httpUtil.getHome(index), new HttpContext.Response<HomeItemBean>() {
            @Override
            public void success(HomeItemBean homeItem) {
                swipe.setRefreshing(false);
                HomeItemBean.DataBean data = homeItem.getData();
                List<HomeItemBean.DataBean.DatasBean> datas = data.getDatas();
                homeAdapter.addHomeInfo(datas);
                if (index == 1) {
                    if (checkDatas(datas)) {
                        showContentView();
                        rv.setAdapter(homeAdapter);
                    }
                } else {
                    homeAdapter.notifyDataSetChanged();
                }
                index = index + 1;
            }

            @Override
            public void fail(String error) {
                Toast.makeText(mCtx, error, Toast.LENGTH_SHORT).show();
            }
        });
        homeAdapter.setHolderListener(new HomeAdapter.bannerHolderListener() {
            @Override
            public void finsh(ViewPager vp) {
                //两种方式获取vp
//                StudyFragment.this.vp = vp;
//                handler.removeCallbacksAndMessages(null);
//                handler.sendEmptyMessageDelayed(1, 3500);
            }
        });
    }

    @Override
    public void initView() {

        swipe = getView(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestHomeData();
            }
        });
        rv = getView(R.id.rv);
        llTitleContainer = getView(R.id.title_bar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mCtx, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.addItemDecoration(new SpaceItemDecoration(22));
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //监听y轴,或者x轴上的偏移量
                sumY += dy;
                int bgColor = 0X553190E8;
                if (sumY <= 0) {
                    //顶部title显示的是透明颜色
                    bgColor = Color.TRANSPARENT;   // 0X553190E8
                    llTitleContainer.setVisibility(View.INVISIBLE);
                } else if (sumY > 350) {
                    //顶部显示蓝色
                    //终点颜色
                    if (color == -1) {
                        bgColor = 0XFF3190E8;
                    } else {
                        bgColor=color;
                    }

                    llTitleContainer.setVisibility(View.VISIBLE);
                } else {
                    //滚动过程中修改顶部的颜色值
                    bgColor = (int) argbEvaluator.evaluate(sumY / duration, Color.TRANSPARENT, bgColor);
                    llTitleContainer.setVisibility(View.VISIBLE);
                }
                llTitleContainer.setBackgroundColor(bgColor);
                super.onScrolled(recyclerView, dx, dy);
            }


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //滚动状态变化
//                RecyclerView.SCROLL_STATE_SETTLING    惯性滑动
//                RecyclerView.SCROLL_STATE_IDLE        滚动空闲(滚动---->停止)
//                RecyclerView.SCROLL_STATE_DRAGGING    拖拽recyclerView滚动
                super.onScrollStateChanged(recyclerView, newState);
                //设置什么布局管理器,就获取什么的布局管理器
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当停止滑动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition ,角标值
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    //所有条目,数量值
                    int totalItemCount = manager.getItemCount();
                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem == (totalItemCount - 1)) {
                        //加载更多功能的代码
//                        AppUrl.index = AppUrl.index + 1;
                        requestHomeData();
                    }
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sumY = 0;
        index = 1;
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(Message msg) {
        if (msg.what == 1) {
            StudyFragment.this.vp = (ViewPager) msg.obj;
            handler.removeCallbacksAndMessages(null);
            handler.sendEmptyMessageDelayed(1, 3500);

        } else if (msg.what == 2) {
            color = SPUtils.getInt(getActivity(), "color", 0);

        }
    }
}
