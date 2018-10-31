package com.example.myapp.myapp.ui.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.myapp.component.study.StudyFragment;
import com.example.myapp.myapp.component.study.StudyFragmentContract;
import com.example.myapp.myapp.data.bean.KeyWordResponse;
import com.example.myapp.myapp.room.Injection;
import com.example.myapp.myapp.room.search.SearchDataSource;
import com.example.myapp.myapp.room.search.entity.SearchHistory;
import com.example.myapp.myapp.ui.adapter.OnItemClickListener;
import com.example.myapp.myapp.ui.adapter.RecyclerAdapter;
import com.example.myapp.myapp.ui.adapter.RecyclerHolder;
import com.example.myapp.myapp.ui.helper.UiHelper;
import com.example.myapp.myapp.utils.MyAnimationUtils;
import com.example.myapp.myapp.utils.ToastUtil;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yexing on 2018/10/17.
 */

public class SearchView extends LinearLayout implements View.OnClickListener {

    private EditText mEditTextSearch;
    private ImageView mBack;
    private ImageView mClear;
    private View rootView;
    private EditTextListener mEditTextListener;
    private List<KeyWordResponse.DataBean.DatasBean> mList = new ArrayList();
    private RecyclerView mRecyclerView;
    private RecyclerAdapter<KeyWordResponse.DataBean.DatasBean> mAdapter;
    private TextView tv_clear;
    private FlowLayout mFlowLayout;
    private StudyFragmentContract.Presenter mPresenter;
    private SpinKitView mSpinKit;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            UiHelper.skipWebActivity(getContext(), "", mList.get(position).getLink());
            hideView();
        }
    };

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        this.setBackgroundColor(getResources().getColor(R.color.white));

        rootView = LayoutInflater.from(getContext()).inflate(R.layout.custom_search, this, false);
        mEditTextSearch = rootView.findViewById(R.id.et_search);
        mBack = rootView.findViewById(R.id.iv_search_back);
        mClear = rootView.findViewById(R.id.iv_search_clear);
        mRecyclerView = rootView.findViewById(R.id.recyclerview);
//        mEditTextSearch.requestFocus();// 请求焦点
        mFlowLayout = rootView.findViewById(R.id.flowlayout);
        //清除历史记录
        tv_clear = rootView.findViewById(R.id.tv_clear);
        //Spinkit
        mSpinKit = rootView.findViewById(R.id.spin_kit);
        tv_clear.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mClear.setOnClickListener(this);

        mAdapter = new RecyclerAdapter<KeyWordResponse.DataBean.DatasBean>(R.layout.item_keyword, mList, onItemClickListener) {

            @Override
            protected void onBindHolder(RecyclerHolder holder, KeyWordResponse.DataBean.DatasBean model, int position) {
                if (!TextUtils.isEmpty(model.getDesc())) {
                    holder.text(R.id.text, model.getDesc());
                } else if (model.getTitle() != null) {
                    holder.text(R.id.text, model.getTitle());
                } else {
                    holder.text(R.id.text, model.getSuperChapterName());
                }

            }
        };

        mRecyclerView.setAdapter(mAdapter);

        mEditTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        mEditTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //判断是否是“完成”键
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //隐藏软键盘
                    InputMethodManager imm = (InputMethodManager) v
                            .getContext().getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(
                                v.getApplicationWindowToken(), 0);
                        if (mEditTextListener != null) {
                            mEditTextListener.editTextMessage(mEditTextSearch.getText().toString().trim());
                        }
                        mSpinKit.setVisibility(View.VISIBLE);

                    }
                    return true;
                }
                return false;
            }
        });

        addView(rootView);
    }

    public void showView() {
        AnimatorSet animatorSet = new AnimatorSet();
        Animator animator = MyAnimationUtils.makeCircularReveal(this, mClear.getRight() + mClear.getWidth() * 3, 50, 0, (float) Math.hypot(this.getWidth(), this.getHeight()));
        //高度
        Animator translationZ = ObjectAnimator.ofFloat(this, "translationZ", 0f, 50f);
        animatorSet.play(animator).with(translationZ);
        animatorSet.setDuration(350);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.start();
        this.setVisibility(View.VISIBLE);
        mEditTextSearch.requestFocus();
        //打开输入法
        InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }


    public void hideView() {
        Animator animator = MyAnimationUtils.makeCircularReveal(this, this.getWidth() - 100, 50, (float) Math.hypot(this.getWidth(), this.getHeight()), 0);
        animator.setDuration(350);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                SearchView.this.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        //关闭输入法
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditTextSearch.getWindowToken(), 0);

    }


    public boolean onTurnBack() {
        if (this.isShown()) {
            hideView();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search_back:
                hideView();
                break;
            case R.id.iv_search_clear:
                mEditTextSearch.setText("");
                break;
            case R.id.tv_clear:
                mPresenter.deleteAll(Injection.provideLocalSearchDataSource(getContext()));
                break;
        }
    }

    public void setKeyWordData(KeyWordResponse keyWordData) {
        mSpinKit.setVisibility(View.GONE);
        mList.clear();
        List<KeyWordResponse.DataBean.DatasBean> datas = keyWordData.getData().getDatas();
        if (datas.size() == 0) {
            ToastUtil.showApp("未查到数据！尝试更换关键词！");
            return;
        }

        if (datas.size() > 5) {
            for (int i = 0; i < 6; i++) {
                KeyWordResponse.DataBean.DatasBean datasBean = datas.get(i);
                mList.add(datasBean);
            }
        } else {

            mList.addAll(datas);
        }

        mAdapter.notifyDataSetChanged();
    }


    public void setSearchData(final List<SearchHistory> list, StudyFragmentContract.Presenter presenter) {
        this.mPresenter = presenter;
        mFlowLayout.removeAllViews();
        mFlowLayout.setVisibility(View.VISIBLE);
        Collections.reverse(list);
        if (list.size() > 3) {
            fillFlow(list, 3);
        } else if (list.size() == 0) {
            tv_clear.setVisibility(View.GONE);
            mFlowLayout.setVisibility(View.GONE);
        } else {
            fillFlow(list, list.size());
        }
    }

    private void fillFlow(List<SearchHistory> list, int size) {
        for (int i = 0; i < size; i++) {
            TextView textView = new TextView(getContext());
            final String message = list.get(i).getMessage();
            textView.setText(message);
            textView.setPadding(6, 6, 6, 6);
            textView.setTextColor(Color.BLACK);
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setBackgroundResource(R.drawable.search_bg);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mEditTextListener != null) {
                        mEditTextListener.editTextMessage(message);
                    }
                    mSpinKit.setVisibility(View.VISIBLE);
                    mEditTextSearch.setText(message);
                    mEditTextSearch.setSelection(message.length());
                }
            });
            mFlowLayout.addView(textView);
        }
        tv_clear.setVisibility(View.VISIBLE);
    }

    /**
     * 清除历史成功
     */
    public void deletaDatabaseSuccess() {
        mFlowLayout.removeAllViews();
        tv_clear.setVisibility(View.GONE);
    }

    public interface EditTextListener {
        void editTextMessage(String message);
    }

    public void setEditTextListener(EditTextListener editTextListener) {

        mEditTextListener = editTextListener;
    }
}
