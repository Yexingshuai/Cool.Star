package com.example.myapp.myapp.component.life;

import android.Manifest;
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
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.component.life.qrcode.QRcodeActivity;
import com.example.myapp.myapp.component.life.widget.BannerWidget;
import com.example.myapp.myapp.component.life.widget.ListWidget;
import com.example.myapp.myapp.data.bean.JokeResponse;
import com.example.myapp.myapp.di.glide.GlideContext;
import com.example.myapp.myapp.ui.adapter.FragmentAdapter;
import com.example.myapp.myapp.ui.helper.UiHelper;
import com.example.myapp.myapp.utils.PermissonUtil;
import com.example.myapp.myapp.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;


/**
 * Created by yexing on 2018/3/28.
 */

public class LifeFragment3 extends BaseFragment implements LifeFragmentContract.View, View.OnClickListener {


    /**
     * 默认页码数
     */
    private static final int PAGE_NUMBER_DEFAULT = 1;

    public static final int RECYCLERVIEW_TOP = 2;

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

    /**
     * weather view
     *
     * @return
     */
    public TextView mText_degree;
    public TextView mText_week;
    public TextView mText_date;
    public TextView mText_location;
    public ImageView mImage_weather_line;
    public TextView mText_weather;
    private ImageView imageView;
    private ImageView mImgDefault;

    private String imgUrl = "https://cdn.heweather.com/cond_icon/%s.png";
    private static final int CODE_SELECT_IMAGE = 1;


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
        ivScanCode.setOnClickListener(this);
        mAppbarlayout = getView(R.id.appbar);
        mUpImg = getView(R.id.iv_up);
        mRootHeaderView = getView(R.id.layout_header_root);
        imageView = getView(R.id.imageView);
        mText_degree = getView(R.id.text_degree);
        mText_week = getView(R.id.text_week);
        mText_date = getView(R.id.text_date);
        mText_location = getView(R.id.text_location);
        mImage_weather_line = getView(R.id.image_weather_line);
        mText_weather = getView(R.id.text_weather);
        mImgDefault = getView(R.id.image_weather_default);
        ImageView mImgFilter = getView(R.id.imageView_filter);
        mImgFilter.setOnClickListener(this);
        mSearchLayout.setOnClickListener(this);
        mUpImg.setOnClickListener(this);
        ivScanCode.setOnClickListener(this);
        initAppBarLayout();


    }

    @Override
    public void initData() {
        getWeatherInfo();
        getTimeInfo();
        //添加widget
        BannerWidget bannerWidget = new BannerWidget(getActivity());
        bannerWidget.init();
        mRootHeaderView.addView(bannerWidget.getRootView());
        ListWidget listWidget = new ListWidget(getActivity());
        listWidget.init();
        mRootHeaderView.addView(listWidget.getRootView());
//        mRootHeaderView.requestLayout();

        mFragmentList = new ArrayList<>();
        mPresenter.addJokeFg(mFragmentList);
        mViewPager.setOffscreenPageLimit(mFragmentList.size());

        mViewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), mFragmentList, Arrays.asList(getActivity().getResources().getStringArray(R.array.joke_fg))));
//        // 将TabLayout和ViewPager进行关联，让两者联动起来
        mTabLayout.setupWithViewPager(mViewPager);


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
            Message message = new Message();
            message.what = RECYCLERVIEW_TOP;
            EventBus.getDefault().post(message);
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

                if (verticalOffset == 0 & isShowImg) {
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

    public void getWeatherInfo() {

        PermissonUtil.requestPermisson(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION, "地理位置", new PermissonUtil.PermissonListener() {
            @Override
            public void onPermissonGranted() {
                HeWeather.getWeatherNow(getActivity(), "auto_ip", Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultWeatherNowBeanListener() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtil.showApp(throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(List<Now> list) {
                        Now now = list.get(0);
                        NowBase nowBase = now.getNow();
                        String cond_code = nowBase.getCond_code();//图标代码
                        String location = now.getBasic().getLocation();
                        mImage_weather_line.setVisibility(View.VISIBLE);
                        mImgDefault.setVisibility(View.GONE);
                        mText_location.setText(location);
                        String tmp = nowBase.getTmp();
                        mText_degree.setText(tmp + "°");
                        mText_weather.setText(nowBase.getCond_txt());
                        mText_week.setText(mWay);
                        mText_date.setText(date);
                        String s = String.format(imgUrl, cond_code);
                        if (TextUtils.equals(cond_code, "100")) {
                            setVectorDrawable(getActivity(), imageView, R.mipmap.weather100, true);
                        } else {
                            GlideContext.loadCommon(getActivity(), s, imageView);
                        }


                    }
                });
            }

            @Override
            public void onPermissonDenied() {
                ToastUtil.showApp("权限被拒绝！");
            }
        });

    }

    public String mWay;
    public String date;

    public void getTimeInfo() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String year = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String month = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String way = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(way)) {
            way = "天";
        } else if ("2".equals(way)) {
            way = "一";
        } else if ("3".equals(way)) {
            way = "二";
        } else if ("4".equals(way)) {
            way = "三";
        } else if ("5".equals(way)) {
            way = "四";
        } else if ("6".equals(way)) {
            way = "五";
        } else if ("7".equals(way)) {
            way = "六";
        }
        mWay = "星期" + way;
        date = year + "-" + month + "-" + day;
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
//            bitmap = tintBitmap(bitmap, Color.parseColor("#ff000000"));
            bitmap = tintBitmap(bitmap, Color.parseColor("#FFD700"));
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
            case R.id.scan_code:
                PermissonUtil.requestPermisson(getActivity(), Manifest.permission.CAMERA, "相机", new PermissonUtil.PermissonListener() {
                    @Override
                    public void onPermissonGranted() {
                        UiHelper.skipActivityNofinish(getActivity(), QRcodeActivity.class);
                    }

                    @Override
                    public void onPermissonDenied() {
//                        ToastUtil.showApp("权限被拒绝");
                    }
                });

                break;
            case R.id.imageView_filter:
                break;
        }
    }


}
