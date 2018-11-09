package com.example.myapp.myapp.component.news;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.component.news.category.CategoryActivity;
import com.example.myapp.myapp.component.news.detail.NewsDetailFragment;
import com.example.myapp.myapp.component.news.detail.NewsDetailPresenter;
import com.example.myapp.myapp.data.bean.NewsCategoryResponse;
import com.example.myapp.myapp.data.source.news.detail.NewsDetailRepository;
import com.example.myapp.myapp.room.news.entity.Category;
import com.example.myapp.myapp.ui.adapter.FragmentAdapter;
import com.example.myapp.myapp.ui.helper.UiHelper;
import com.example.myapp.myapp.ui.view.ColorFlipPagerTitleView;
import com.example.myapp.myapp.utils.ToastUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yexing on 2018/10/10.
 */

public class NewsFragment extends BaseFragment implements NewsContract.View, View.OnClickListener {
    private NewsContract.Presenter mPresenter;
    private ViewPager mViewPager;
    private MagicIndicator magicIndicator;
    private List<BaseFragment> mNewsFragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();
    private ImageView mAddBtn;
    private FragmentAdapter mFragmentAdapter;

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }


    @Override
    public void initView() {
        magicIndicator = getView(R.id.magic_indicator);
        mViewPager = getView(R.id.view_pager);
        mAddBtn = getView(R.id.add_btn);
        mAddBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        //先从数据库查询数据，没有则从网络获取
        mPresenter.queryDatabase();
    }

    private void initIndicator() {
        mViewPager.setOffscreenPageLimit(3);
        mFragmentAdapter = new FragmentAdapter(getChildFragmentManager(), mNewsFragmentList);
        mViewPager.setAdapter(mFragmentAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setScrollPivotX(0.65f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.setText(mTitleList.get(index));
                simplePagerTitleView.setNormalColor(Color.WHITE);
                simplePagerTitleView.setSelectedColor(Color.parseColor("#4CAF50"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 6));
                indicator.setLineWidth(UIUtil.dip2px(context, 10));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(Color.parseColor("#00c853"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }


    @Override
    public void setPresenter(NewsContract.Presenter presenter) {
        mPresenter = presenter;
    }


    /**
     * 请求成功
     *
     * @param result
     */
    @Override
    public void requestSuccess(NewsCategoryResponse result) {
        List<NewsCategoryResponse.ResultBean> categoryList = result.getResult();
        for (int i = 0; i < categoryList.size(); i++) {
            NewsCategoryResponse.ResultBean resultBean = categoryList.get(i);
            mTitleList.add(resultBean.getName());
            NewsDetailFragment newsDetailFragment = NewsDetailFragment.newInstance(resultBean.getCid());
            new NewsDetailPresenter(newsDetailFragment, new NewsDetailRepository());
            mNewsFragmentList.add(newsDetailFragment);
        }
        initIndicator();
        //存储到数据库当中
        mPresenter.saveToDatabase(categoryList);
    }

    /**
     * 请求失败
     *
     * @param errorMsg
     */
    @Override
    public void requestFail(String errorMsg) {
        ToastUtil.showApp(errorMsg);
    }

    @Override
    public void setDatabaseData(List<Category> categoryList) {
        if (categoryList.size() == 0) {
            mPresenter.requestCategory();
        } else {
            mTitleList.clear();
            mNewsFragmentList.clear();

            for (int i = 0; i < categoryList.size(); i++) {
                Category category = categoryList.get(i);
                mTitleList.add(category.getMessage());
                NewsDetailFragment newsDetailFragment = NewsDetailFragment.newInstance(category.getCid());
                new NewsDetailPresenter(newsDetailFragment, new NewsDetailRepository());
                mNewsFragmentList.add(newsDetailFragment);
            }
            if (mFragmentAdapter != null) {
                mFragmentAdapter.notifyDataSetChanged();
            }
//            else {
            initIndicator();
//            }


        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.stop();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_btn:
                UiHelper.skipActivityNofinish(getActivity(), CategoryActivity.class);
                break;
        }
    }
}
