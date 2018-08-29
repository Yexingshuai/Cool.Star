package com.example.myapp.myapp.component.life;

import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseFragment;
import com.example.myapp.myapp.data.bean.JokeResponse;
import com.example.myapp.myapp.ui.adapter.LifeHeadAdapter;
import com.example.myapp.myapp.ui.adapter.SwipeFlingAdater;
import com.example.myapp.myapp.ui.flingswipe.SwipeFlingAdapterView;
import com.example.myapp.myapp.ui.helper.VpTransformer;
import com.example.myapp.myapp.ui.load.LoadingStatusLayout;
import com.example.myapp.myapp.utils.ToastUtil;
import com.example.myapp.myapp.utils.Utils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yexing on 2018/3/28.
 */

public class LifeFragment extends BaseFragment implements LifeFragmentContract.View {


    private ViewPager vp_module;
    private RecyclerView mRecyclerView;
    private LifeFragmentContract.Presenter mPresenter;
    private int pageSize = 20;
    private int pageNum = 1;
    private List<JokeResponse.ResultBean.DataBean> jokeList = new ArrayList<>();
    /**
     * 默认页码数
     */
    private static final int PAGE_NUMBER_DEFAULT = 1;
    private LifeHeadAdapter lifeHeadAdapter;
    private SwipeFlingAdapterView swipeFlingAdapterView;
    private SwipeFlingAdater swipeFlingAdater;
    private LoadingStatusLayout loadingStatusLayout;


    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }

    @Override
    protected void refreshData() {
        mPresenter.requestJoke(pageNum, pageSize);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_life;
    }

    @Override
    public void initView() {
//        vp_module = getView(R.id.vp_module);
        swipeFlingAdapterView = getView(R.id.swipe);


    }


    @Override
    public void initData() {
        mPresenter.requestJoke(pageNum, pageSize);
        swipeFlingAdater = new SwipeFlingAdater(getActivity(), jokeList);
        swipeFlingAdapterView.setAdapter(swipeFlingAdater);
        swipeFlingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                jokeList.remove(0);
                swipeFlingAdater.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
            }

            @Override
            public void onRightCardExit(Object dataObject) {
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });
//        vp_module.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                50, getResources().getDisplayMetrics()));
//        vp_module.setPageTransformer(true, new VpTransformer());
//        lifeHeadAdapter = new LifeHeadAdapter(getActivity(), jokeList);
//        vp_module.setAdapter(lifeHeadAdapter);
    }

    @Override
    public void setPresenter(LifeFragmentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setJokeInfo(JokeResponse response) {
        jokeList.addAll(response.getResult().getData());
//        lifeHeadAdapter.notifyDataSetChanged();
        swipeFlingAdater.notifyDataSetChanged();
    }

    /**
     * 请求失败
     *
     * @param errorMsg
     */
    @Override
    public void requestJokeFail(String errorMsg) {
        ToastUtil.showApp(errorMsg);
    }


    public static LifeFragment newInstance() {
        return new LifeFragment();
    }


    class MyAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_favorite, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

    }


}
