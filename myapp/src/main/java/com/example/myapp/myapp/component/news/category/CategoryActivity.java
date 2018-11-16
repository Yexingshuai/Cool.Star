package com.example.myapp.myapp.component.news.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.component.news.helper.MyItemTouchHelper;
import com.example.myapp.myapp.component.news.helper.OnRecyclerItemClickListener;
import com.example.myapp.myapp.room.news.entity.Category;
import com.example.myapp.myapp.ui.adapter.OnItemClickListener;
import com.example.myapp.myapp.ui.adapter.RecyclerAdapter;
import com.example.myapp.myapp.ui.adapter.RecyclerHolder;
import com.example.myapp.myapp.utils.Utils;
import com.example.myapp.myapp.utils.VibratorUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 新闻频道条目筛选
 * </p>
 */
public class CategoryActivity extends BaseActivity implements View.OnClickListener, MyItemTouchHelper.ItemTouchAdapter, CategoryContract.View {


    private RecyclerView mRecyclerActive;
    private RecyclerView mRecyclerInActive;
    private TextView tvExit;
    private boolean mIsEdit;
    private RecyclerAdapter<Category> mActiveAdapter;
    private RecyclerAdapter<Category> mInActiveAdapter;
    private List<Category> mActiveList = new ArrayList<>();
    private List<Category> mInActiveList = new ArrayList();
    private ItemTouchHelper itemTouchHelper;
    private CategoryContract.Presenter mPresenter;
    private boolean isLoad;


    private OnItemClickListener mOnActiveItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            if (mIsEdit & !Utils.isFastDoubleClick()) {
                //先保存到另一个集合，在删除它
                Category resultBean = mActiveList.get(position);
                resultBean.setSave(false);
                mInActiveList.add(resultBean);
                mActiveList.remove(position);
                mActiveAdapter.notifyItemRemoved(position);
                mInActiveAdapter.notifyItemInserted(mInActiveList.size());
            }
        }
    };

    /**
     * 未添加条目
     */

    private OnItemClickListener mOnInActiveItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            if (mIsEdit & !Utils.isFastDoubleClick()) {
                Category resultBean = mInActiveList.get(position);
                resultBean.setSave(true);
                mActiveList.add(resultBean);
                mInActiveList.remove(position);
                mInActiveAdapter.notifyItemRemoved(position);
                mActiveAdapter.notifyItemInserted(mActiveList.size());
            }
        }
    };


    @Override
    public int inflateContentView() {
        return R.layout.activity_category;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.stop();
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        new CategoryPresenter(this);
        mPresenter.start();
        mRecyclerActive = getView(R.id.recyclerview_active);
        mRecyclerInActive = getView(R.id.recyclerview_inactive);
        tvExit = getView(R.id.tv_exit);
    }

    @Override
    protected void initData() {
        tvExit.setOnClickListener(this);

        mPresenter.queryAll();

        //anim
        itemTouchHelper = new ItemTouchHelper(new MyItemTouchHelper(this));
        itemTouchHelper.attachToRecyclerView(mRecyclerActive);

        mRecyclerActive.setLayoutManager(new GridLayoutManager(this, 4));

        mRecyclerActive.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerActive) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                itemTouchHelper.startDrag(vh);
                VibratorUtil.Vibrate(CategoryActivity.this, 70);   //震动70ms
            }

        });

        mActiveAdapter = new RecyclerAdapter<Category>(R.layout.item_category_text, mActiveList, mOnActiveItemClickListener) {
            @Override
            protected void onBindHolder(RecyclerHolder holder, Category model, int position) {
                holder.text(R.id.tv_text, model.getMessage());
                holder.showView(R.id.img_edit, mIsEdit);
            }
        };
        mRecyclerActive.setItemAnimator(new DefaultItemAnimator());
        mRecyclerActive.setAdapter(mActiveAdapter);

        //Inactive  adapter-----------------------------------------------------

        mInActiveAdapter = new RecyclerAdapter<Category>(R.layout.item_category_text, mInActiveList, mOnInActiveItemClickListener) {
            @Override
            protected void onBindHolder(RecyclerHolder holder, Category model, int position) {
                holder.text(R.id.tv_text, model.getMessage());
            }
        };
        mRecyclerInActive.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerInActive.setItemAnimator(new DefaultItemAnimator());
        mRecyclerInActive.setAdapter(mInActiveAdapter);
    }


    /**
     * 获取到数据库的数据
     *
     * @param list
     */
    @Override
    public void setDatabaseData(List<Category> list) {
        if (!isLoad) {
            arrangeList(list);
            isLoad = true;
        }

    }

    /**
     * 整理集合
     *
     * @param list
     */
    private void arrangeList(List<Category> list) {
        for (int i = 0; i < list.size(); i++) {
            Category category = list.get(i);
            if (category.isSave()) {
                mActiveList.add(category);
            } else {
                mInActiveList.add(category);
            }
        }
        mActiveAdapter.notifyDataSetChanged();
        mInActiveAdapter.notifyDataSetChanged();
    }

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }

    @Override
    public void setPresenter(CategoryContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mActiveList, i, i + 1); //参数2：元素开始索引， 参数3：元素结束索引
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mActiveList, i, i - 1);
            }
        }
        mActiveAdapter.notifyItemMoved(fromPosition, toPosition);
        mPresenter.updateCatogryDatabase(mActiveList, mInActiveList);
    }

    @Override
    public void onSwiped(int position) {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_exit:
                if (mIsEdit) {
                    ((TextView) view).setText("编辑");
                    mPresenter.updateCatogryDatabase(mActiveList, mInActiveList);
                } else {
                    ((TextView) view).setText("完成");
                }
                if (mActiveAdapter != null) {
                    mActiveAdapter.notifyDataSetChanged();   //刷新UI，增加或去除 叉子
                }
                mIsEdit = !mIsEdit;

                break;
            default:
                break;
        }
    }


}


