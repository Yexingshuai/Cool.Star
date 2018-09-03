package com.example.myapp.myapp.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.ui.helper.UiHelper;
import com.example.myapp.myapp.utils.Utils;

import java.util.Random;

public class SplashActivity extends BaseActivity {

    private RelativeLayout ll;
    private TextView[] ts;


    private void startTextInAnim(TextView t) {
        Random r = new Random();
        DisplayMetrics metrics = Utils.getMetrics(this);
        int x = r.nextInt(metrics.widthPixels * 4 / 3);   //1440不在内，只产生0-1439   乐事:1080*1920
        int y = r.nextInt(metrics.heightPixels * 4 / 3);  //2560
        float s = r.nextFloat() + 4.0f;  //r.nextFloat会产生一个0.0-1.0之间的  float
        ValueAnimator tranY = ObjectAnimator.ofFloat(t, "translationY", y - t.getY() , 0);//
        ValueAnimator tranX = ObjectAnimator.ofFloat(t, "translationX", x - t.getX(), 0);//
        ValueAnimator scaleX = ObjectAnimator.ofFloat(t, "scaleX", s, 1.0f);
        ValueAnimator scaleY = ObjectAnimator.ofFloat(t, "scaleY", s, 1.0f);
        ValueAnimator alpha = ObjectAnimator.ofFloat(t, "alpha", 0.0f, 1.0f);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(1800);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.play(tranX).with(tranY).with(scaleX).with(scaleY).with(alpha);
        if (t == findViewById(R.id.tv7)) {
            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    startImgAnim();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        set.start();
    }

    private void startImgAnim() {

        ImageView image = (ImageView) findViewById(R.id.splash_logo);
        image.setVisibility(View.VISIBLE);
        ValueAnimator alpha = ObjectAnimator.ofFloat(image, "alpha", 0.0f, 1.0f);
        ValueAnimator tranY = ObjectAnimator.ofFloat(image, "translationY", -image.getHeight() / 2, 0);
        AnimatorSet set = new AnimatorSet();
        set.play(tranY).with(alpha);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UiHelper.skipToOtherActivity(SplashActivity.this, MainActivity.class);
                    }
                }, 2000);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set.start();

    }

    @Override
    public int inflateContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ll = findViewById(R.id.ll);
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR,
                new int[]{
                        getResources().getColor(R.color.xblue),
                        getResources().getColor(R.color.xxblue)
                });
//        ll.setBackground(gradientDrawable);
        ts = new TextView[]{
                (TextView) findViewById(R.id.tv1),
                (TextView) findViewById(R.id.tv2),
                (TextView) findViewById(R.id.tv3),
                (TextView) findViewById(R.id.tv4),
                (TextView) findViewById(R.id.tv5),
                (TextView) findViewById(R.id.tv6),
                (TextView) findViewById(R.id.tv7)
        };
        ts[0].post(new Runnable() {
            @Override
            public void run() {
                for (TextView t : ts) {
                    t.setVisibility(View.VISIBLE);
                    startTextInAnim(t);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }
}
