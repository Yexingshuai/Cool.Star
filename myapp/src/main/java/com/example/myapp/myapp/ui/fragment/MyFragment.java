package com.example.myapp.myapp.ui.fragment;

import android.content.Intent;
import android.os.Message;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.myapp.R;
import com.example.myapp.myapp.ui.activity.AboutActivity;
import com.example.myapp.myapp.ui.activity.HomeActivity;

import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.test.RxJavaTest;
import com.example.myapp.myapp.ui.activity.TestMenuActivity;
import com.example.myapp.myapp.ui.dialog.DesignDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by daixiankade on 2018/3/28.
 */

public class MyFragment extends BaseFragment implements View.OnClickListener {



    private ImageView iv_me;
    private LinearLayout ll_home;
    private LinearLayout ll_design;
    private LinearLayout ll_about;

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }

    @Override
    protected void refreshData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void initData() {
    }


    @Override
    public void initView() {
        iv_me = getView(R.id.iv_me);
        iv_me.setOnClickListener(this);
        ll_home = getView(R.id.ll_home);
        ll_home.setOnClickListener(this);
        ll_design = getView(R.id.ll_desgin);
        ll_design.setOnClickListener(this);
        ll_about = getView(R.id.ll_about);
        ll_about.setOnClickListener(this);
        RxJavaTest.test2();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_me:
                Toast.makeText(mCtx, "别点我，我很帅...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_home:
//                startActivity(new Intent(mCtx, HomeActivity.class));
                startActivity(new Intent(mCtx, HomeActivity.class), ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity()).toBundle());
                break;
            case R.id.ll_desgin:
                DesignDialog designDialog = new DesignDialog();
                designDialog.show(getFragmentManager(),"tag");
                break;
            case R.id.ll_about:
//                startActivity(new Intent(mCtx, AboutActivity.class));
                startActivity(new Intent(mCtx, TestMenuActivity.class));
//                startActivity(new Intent(mCtx, TestAboutActivity.class), ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity()).toBundle());
                break;

        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(Message msg) {

    }
}
