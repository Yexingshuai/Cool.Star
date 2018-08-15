package com.example.myapp.myapp.component.life;

import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.widget.ImageView;

import com.example.myapp.R;

import com.example.myapp.myapp.ui.adapter.LifeHeadAdapter;

import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.ui.helper.VpTransformer;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by daixiankade on 2018/3/28.
 */

public class LifeFragment extends BaseFragment {

    private ImageView iv;
    private FloatingActionButton fab;
    private boolean flag;
    private int hypotenuse;
    private int centerX;
    private int centerY;
    private ViewPager vp_module;


    @Override
    public int getLayoutId() {
        return R.layout.fragment3;
    }

    @Override
    public void initData() {
        showContentView();
        LifeHeadAdapter lifeHeadAdapter = new LifeHeadAdapter(getActivity());
        vp_module.setAdapter(lifeHeadAdapter);
    }


    @Override
    public void initView() {
        vp_module = getView(R.id.vp_module);
        vp_module.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                50, getResources().getDisplayMetrics()));
        vp_module.setPageTransformer(false, new VpTransformer());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(Message msg) {

    }

//    private void loadAnimation() {
//
//        int[] vLocation = new int[2];
//        fab.getLocationOnScreen(vLocation);
//        centerX = vLocation[0] + fab.getWidth() / 2;
//        centerY = vLocation[1] + fab.getHeight() / 2;
//        int imgHeight = iv.getHeight();
//        int imgWidth = iv.getWidth();
//        hypotenuse = (int) Math.hypot(imgHeight, imgWidth);
//        Animator animator;
//        if (flag) {
//            animator = MyAnimationUtils.makeCircularReveal(iv, centerX, centerY, hypotenuse, 0);
//            animator.setDuration(2000);
//            flag = false;
//        } else {
//            animator = MyAnimationUtils.makeCircularReveal(iv, centerX, centerY, 0, hypotenuse);
//            animator.setDuration(2000);
//            flag = true;
//        }
//        animator.start();
//    }
}
