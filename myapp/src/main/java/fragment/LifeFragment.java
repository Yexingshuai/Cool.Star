package fragment;

import android.animation.Animator;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapp.R;

import java.util.ArrayList;

import base.BaseFragment;
import utils.MyAnimationUtils;

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

    @Override
    public int getLayoutId() {
        return R.layout.fragment3;
    }

    @Override
    public void initData() {
        showContentView();
    }


    @Override
    public void onResume() {
        super.onResume();
        fab.post(new Runnable() {
            @Override
            public void run() {
//                int[] vLocation = new int[2];
//                fab.getLocationOnScreen(vLocation);
//                centerX = vLocation[0] + fab.getWidth() / 2;
//                centerY = vLocation[1] + fab.getHeight() / 2;
            }
        });

    }

    @Override
    public void initView() {
        fab = getView(R.id.fab);
        iv = getView(R.id.iv);
        final ArrayList<String> list = new ArrayList<>();
        list.add("111");
        final DemoClass demoClass = new DemoClass(list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAnimation();

            }
        });





    }

    private void loadAnimation() {

        int[] vLocation = new int[2];
        fab.getLocationOnScreen(vLocation);
        centerX = vLocation[0] + fab.getWidth() / 2;
        centerY = vLocation[1] + fab.getHeight() / 2;
        int imgHeight = iv.getHeight();
        int imgWidth = iv.getWidth();
        hypotenuse = (int) Math.hypot(imgHeight, imgWidth);
        Animator animator;
        if (flag) {
            animator = MyAnimationUtils.makeCircularReveal(iv, centerX, centerY, hypotenuse, 0);
            animator.setDuration(2000);
            flag = false;
        } else {
            animator = MyAnimationUtils.makeCircularReveal(iv, centerX, centerY, 0, hypotenuse);
            animator.setDuration(2000);
            flag = true;
        }
        animator.start();
    }
}
