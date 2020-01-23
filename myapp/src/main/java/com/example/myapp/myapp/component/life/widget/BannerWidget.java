package com.example.myapp.myapp.component.life.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.myapp.R;
import com.example.myapp.myapp.utils.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Response;

public class BannerWidget extends BaseWidget {


    private ViewPager mViewPager;
    private ArrayList<String> mList = new ArrayList<>();
    private LinearLayout mIndicator;
    private int lastPagePosition;
    private Disposable disposable;
    private int interval_time = 3; //图片自动轮播时间间隔
    private boolean onTouching;
    public static final String url = "https://www.easy-mock.com/mock/5dd368407e157d38e7e41058/coolstar/getBanner";
    private Adapter mAdapter;


    public BannerWidget(Context context) {
        super(context);
    }

    @Override
    public int getWidgetLayout() {
        return R.layout.widget_banner;
    }


    @Override
    public void initView() {
        super.initView();
        mViewPager = mRootView.findViewById(R.id.viewPager);
        mIndicator = mRootView.findViewById(R.id.ll_indicator);
    }

    @Override
    public void initData() {
        super.initData();
        mAdapter = new Adapter();
        mViewPager.addOnPageChangeListener(new PagerChangeListener());

        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    onTouching = true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    onTouching = false;
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    onTouching = true;
                } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    onTouching = false;
                }

                if (disposable != null) {
                    disposable.dispose();
                    disposable = null;
                }

                if (!onTouching) {
                    autoScroll();
                }

                return false;
            }
        });

        getBanner();
    }

    private void getBanner() {
        OkGo.get(url)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray data = jsonObject.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                String url = data.getString(i);
                                mList.add(url);
                            }

                            mViewPager.setAdapter(mAdapter);
                            fillIndicator();
                            autoScroll();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    private void autoScroll() {
        if (interval_time > 0) {
            if (disposable != null) {
                disposable.dispose();
                disposable = null;
            }
            disposable = Observable.interval(interval_time, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            if (!onTouching) {
                                int position = mViewPager.getCurrentItem();
                                mViewPager.setCurrentItem(position + 1, true);
                            }
                        }
                    });
        }
    }

    private void fillIndicator() {
        mIndicator.removeAllViews();

        for (int i = 0; i < mList.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setBackgroundResource(R.drawable.banner_indicator_check);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Utils.dp2px(mContext, 10), Utils.dp2px(mContext, 2));
            if (i != 0) {
                layoutParams.leftMargin = Utils.dp2px(mContext, 5);
                imageView.setBackgroundResource(R.drawable.banner_indicator_normal);
            }
            imageView.setLayoutParams(layoutParams);
            mIndicator.addView(imageView);
        }

    }


    class Adapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            position = position % mList.size();
            ImageView imageView = new ImageView(mContext);
            Glide.with(mContext).load(mList.get(position)).into(imageView);
//            imageView.setImageResource(imgArray[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }


    class PagerChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            position = position % mList.size();
            ((ImageView) mIndicator.getChildAt(position)).setBackgroundResource(R.drawable.banner_indicator_check);
            ((ImageView) mIndicator.getChildAt(lastPagePosition)).setBackgroundResource(R.drawable.banner_indicator_normal);
            lastPagePosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}

