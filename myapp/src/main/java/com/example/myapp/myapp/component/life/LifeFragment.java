package com.example.myapp.myapp.component.life;

import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import com.example.myapp.R;

import com.example.myapp.myapp.ui.adapter.LifeHeadAdapter;

import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.ui.helper.VpTransformer;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by yexing on 2018/3/28.
 */

public class LifeFragment extends BaseFragment {


    private ViewPager vp_module;


    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }

    @Override
    protected void refreshData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_life;
    }

    @Override
    public void initData() {
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


}
