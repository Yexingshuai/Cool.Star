package com.example.myapp.myapp.component.life;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.component.life.fragment.JokeFragment;
import com.example.myapp.myapp.component.life.view.AppBarStateChangeListener;
import com.example.myapp.myapp.component.life.view.JudgeNestedScrollView2;
import com.example.myapp.myapp.data.bean.JokeResponse;
import com.example.myapp.myapp.ui.adapter.FragmentAdapter;
import com.example.myapp.myapp.utils.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by yexing on 2018/3/28.
 */

public class LifeFragment3 extends BaseFragment implements LifeFragmentContract.View, View.OnClickListener {


    /**
     * 默认页码数
     */
    private static final int PAGE_NUMBER_DEFAULT = 1;

    private List<BaseFragment> mFragmentList;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private LifeFragmentContract.Presenter mPresenter;
    private View mSearchLayout;
    private AppBarLayout mAppbarlayout;
    private ImageView mUpImg;
    private LinearLayout mRootHeaderView;
    private int headerHeight = -1;
    private boolean isShowImg = false;


    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }

    @Override
    protected void refreshData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_life3;
    }

    @Override
    public void initView() {
        mTabLayout = getView(R.id.tabLayout);
        mViewPager = getView(R.id.viewpager);
        mSearchLayout = getView(R.id.searchLayout);
        ImageView ivScanCode = getView(R.id.scan_code);
        mAppbarlayout = getView(R.id.appbar);
        mUpImg = getView(R.id.iv_up);
        mRootHeaderView = getView(R.id.layout_header_root);
//        setVectorDrawable(getActivity(), ivScanCode, R.drawable.ic_home_menu_scan, true);
        mSearchLayout.setOnClickListener(this);
        mUpImg.setOnClickListener(this);
        ivScanCode.setOnClickListener(this);
        initAppBarLayout();


    }

    @Override
    public void initData() {
        mFragmentList = new ArrayList<>();
        mPresenter.addJokeFg(mFragmentList);
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
        mViewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), mFragmentList, Arrays.asList(getActivity().getResources().getStringArray(R.array.joke_fg))));
//        // 将TabLayout和ViewPager进行关联，让两者联动起来
        mTabLayout.setupWithViewPager(mViewPager);

    }


    private void setHeaderView(boolean isHide) {
        if (isHide) {
            AppBarLayout.LayoutParams mParams = (AppBarLayout.LayoutParams) mRootHeaderView.getLayoutParams();
            mParams.setScrollFlags(0);
            mRootHeaderView.setLayoutParams(mParams);
            mRootHeaderView.setVisibility(View.GONE);
            isShowImg = true;
        } else {
            AppBarLayout.LayoutParams mParams = (AppBarLayout.LayoutParams) mRootHeaderView.getLayoutParams();
            mParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
                    AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
            mRootHeaderView.setLayoutParams(mParams);
            mRootHeaderView.setVisibility(View.VISIBLE);
            mAppbarlayout.setExpanded(true);
            isShowImg = false;
            mUpImg.setVisibility(View.GONE);
        }
    }


    // 初始化AppBarLayout
    private void initAppBarLayout() {

        mAppbarlayout.post(new Runnable() {
            @Override
            public void run() {
                CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) mAppbarlayout.getLayoutParams()).getBehavior();
                if (behavior instanceof AppBarLayout.Behavior) {
                    final AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
                    appBarLayoutBehavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                        @Override
                        public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                            return true;
                        }
                    });
                }
            }
        });

        LayoutTransition mTransition = new LayoutTransition();
        /**
         * 添加View时过渡动画效果
         */
        ObjectAnimator addAnimator = ObjectAnimator.ofFloat(null, "translationY", 0, 1.f).
                setDuration(mTransition.getDuration(LayoutTransition.APPEARING));
        mTransition.setAnimator(LayoutTransition.APPEARING, addAnimator);

        mAppbarlayout.setLayoutTransition(mTransition);
        mAppbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                verticalOffset = Math.abs(verticalOffset);

                if (verticalOffset == 0&isShowImg) {
                    mUpImg.setVisibility(View.VISIBLE);
                }

                if (mRootHeaderView.getHeight() > 0) {
                    headerHeight = mRootHeaderView.getHeight();
                }

                if (verticalOffset >= headerHeight) {
                    //当偏移量超过顶部layout的高度时，我们认为他已经完全移动出屏幕了
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setHeaderView(true);
                        }
                    }, 100);
                }
            }
        });
    }


    public static LifeFragment3 newInstance() {
        return new LifeFragment3();
    }


    @Override
    public void setJokeInfo(JokeResponse response) {

    }

    @Override
    public void requestJokeFail(String errorMsg) {

    }

    @Override
    public void setPresenter(LifeFragmentContract.Presenter presenter) {
        mPresenter = presenter;
    }


    public static void setVectorDrawable(Context context, ImageView imageView, int drawableId, boolean isBlack) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        if (isBlack) {
            // icon修改为黑色
            bitmap = tintBitmap(bitmap, Color.parseColor("#ff000000"));
        }
        imageView.setImageBitmap(bitmap);
    }

    public static Bitmap tintBitmap(Bitmap inBitmap, int tintColor) {
        if (inBitmap == null) {
            return null;
        }
        Bitmap outBitmap = Bitmap.createBitmap(inBitmap.getWidth(), inBitmap.getHeight(), inBitmap.getConfig());
        Canvas canvas = new Canvas(outBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(inBitmap, 0, 0, paint);
        return outBitmap;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_up:
                setHeaderView(false);
                break;
            case R.id.searchLayout:

                break;
        }
    }



}
