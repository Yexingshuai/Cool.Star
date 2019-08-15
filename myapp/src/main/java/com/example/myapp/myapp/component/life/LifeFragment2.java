package com.example.myapp.myapp.component.life;

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
import android.support.design.widget.AppBarLayout;
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
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.component.life.fragment.JokeFragment;
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

public class LifeFragment2 extends BaseFragment implements LifeFragmentContract.View {


    /**
     * 默认页码数
     */
    private static final int PAGE_NUMBER_DEFAULT = 1;

    private List<BaseFragment> mFragmentList;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private LifeFragmentContract.Presenter mPresenter;
    private JudgeNestedScrollView2 mNestScrollview;
    private LinearLayout mLlHeader;
    private Toolbar mToolbar;
    private View mSearchLayout;
    private AppBarLayout mAppbarlayout;


    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }

    @Override
    protected void refreshData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_life2;
    }

    @Override
    public void initView() {
        mTabLayout = getView(R.id.tabLayout);
        mViewPager = getView(R.id.viewpager);
        mNestScrollview = getView(R.id.nestScrollview);
        mLlHeader = getView(R.id.ll_header_layout);
        mToolbar = getView(R.id.toolbar);
        mSearchLayout = getView(R.id.searchLayout);
        ImageView ivScanCode = getView(R.id.scan_code);
        mAppbarlayout = getView(R.id.appbar);
        setVectorDrawable(getActivity(), ivScanCode, R.drawable.ic_home_menu_scan, true);
        mSearchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNestScrollview.scrollTo(0, 0);
            }
        });
    }


    @Override
    public void initData() {
        mFragmentList = new ArrayList<>();
        mPresenter.addJokeFg(mFragmentList);
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
        mViewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), mFragmentList, Arrays.asList(getActivity().getResources().getStringArray(R.array.joke_fg))));
//        // 将TabLayout和ViewPager进行关联，让两者联动起来
        mTabLayout.setupWithViewPager(mViewPager);

        mFragmentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewGroup.LayoutParams layoutParams = mViewPager.getLayoutParams();
                int height = mToolbar.getMinimumHeight();
                int rootHeight = mFragmentView.getHeight();
                int mViewPagerHeight = rootHeight - height - mSearchLayout.getHeight() - mTabLayout.getHeight() - Utils.dp2px(getActivity(), 13)+1;
                layoutParams.height = mViewPagerHeight;
                mViewPager.setLayoutParams(layoutParams);
                //要将OnGlobalLayoutListener注销掉
                mFragmentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

//                mNestScrollview.scrollTo(0, 0);
            }
        });




        mNestScrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                BaseFragment baseFragment = mFragmentList.get(mViewPager.getCurrentItem());

                Log.e("-=-=-=-=-=", scrollY + "");
                if (scrollY > mLlHeader.getMeasuredHeight()) {
                    mNestScrollview.setNeedScroll(false);


                } else {

                    mNestScrollview.setNeedScroll(true);
                }
            }
        });

        Log.e("lglglglg", mLlHeader.getMeasuredHeight() + "");

//        setTabItemClickListener();


//        mNestScrollview.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mNestScrollview.scrollTo(0, 0);
//            }
//        },5000);





    }


    public static LifeFragment2 newInstance() {
        return new LifeFragment2();
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


    private void setTabItemClickListener() {
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab == null) return;
            //这里使用到反射，拿到Tab对象后获取Class
            Class c = tab.getClass();
            try {
                //Filed “字段、属性”的意思,c.getDeclaredField 获取私有属性。
                //"mView"是Tab的私有属性名称(可查看TabLayout源码),类型是 TabView,TabLayout私有内部类。
                Field field = c.getDeclaredField("mView");
                //值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false 则指示反射的对象应该实施 Java 语言访问检查。
                //如果不这样会报如下错误
                // java.lang.IllegalAccessException:
                //Class com.test.accessible.Main
                //can not access
                //a member of class com.test.accessible.AccessibleTest
                //with modifiers "private"
                field.setAccessible(true);
                final View view = (View) field.get(tab);
                if (view == null) return;
                view.setTag(i);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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


}
