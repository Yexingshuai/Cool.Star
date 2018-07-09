package fragment;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapp.R;

import java.util.ArrayList;

import adapter.LifeHeadAdapter;
import adapter.TestAdapter;
import base.BaseFragment;
import utils.MyAnimationUtils;
import view.VpTransformer;

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
        vp_module.setPageTransformer(false,new VpTransformer());
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
