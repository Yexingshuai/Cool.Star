package com.example.myapp.myapp.ui.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.myapp.data.bean.BannerBean;
import com.example.myapp.myapp.data.bean.HomeItemBean;
import com.example.myapp.myapp.ui.activity.WebActivity;

import com.example.myapp.myapp.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.example.myapp.myapp.ui.helper.ItemAnimHelper;

/**
 * Created by yexing on 2018/3/29.
 */

public class HomeAdapter extends RecyclerView.Adapter {
    public static final int HEAD = 0;
    public static final int NORMAL = 1;
    private List<BannerBean.DataBean> banner = new ArrayList<>();
    private Context mCtx;
    private ArrayList<HomeItemBean.DataBean.DatasBean> data = new ArrayList<>();
    private int[] array;
    public NormalHolder normalHolder;
    public HeadHolder headHolder;
    private ItemAnimHelper helper = new ItemAnimHelper();
    private BannerAdapter bannerAdapter;


    public HomeAdapter(Context mCtx) {
        this.mCtx = mCtx;
        array = new int[]{mCtx.getResources().getColor(R.color.cardView),
                mCtx.getResources().getColor(R.color.cardView2),
                mCtx.getResources().getColor(R.color.colorAccent),
                mCtx.getResources().getColor(R.color.xxblue)};
    }

    public void addBanner(List<BannerBean.DataBean> banner) {
        this.banner.clear();
        this.banner.addAll(banner);
        if (bannerAdapter != null) {
            bannerAdapter.notifyDataSetChanged();
        }
        notifyDataSetChanged();
    }

    public void addHomeInfo(List<HomeItemBean.DataBean.DatasBean> datas, boolean clear) {
        if (clear) {
            data.clear();
        }
        data.addAll(datas);

//        notifyItemRangeInserted(datas.size(),20);
        notifyItemRangeChanged(datas.size(), 20);
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD;
        } else {
            return NORMAL;
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEAD:
                View head = LayoutInflater.from(mCtx).inflate(R.layout.item_home_banner, parent, false);
                headHolder = new HeadHolder(head);
                return headHolder;
            case NORMAL:
                View normal = LayoutInflater.from(mCtx).inflate(R.layout.item_home_normal, parent, false);
                normalHolder = new NormalHolder(normal);
                return normalHolder;

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);

        if (itemViewType == HEAD) {
            //轮播图处理
            if (banner != null && banner.size() > 0) {
                setBanner(holder);
            }

        } else {

            //一般条目处理
            setNomal(holder, position - 1);
            helper.showItemAnim(holder.itemView, position - 1);
        }

    }


    private void setNomal(RecyclerView.ViewHolder holder, final int position) {
        if (data != null) {
            ((NormalHolder) holder).tv_author.setText(data.get(position).getAuthor() != null ? data.get(position).getAuthor() : "");
            ((NormalHolder) holder).tv_time.setText(data.get(position).getNiceDate() != null ? data.get(position).getNiceDate() : "");
            ((NormalHolder) holder).tv_title.setText(data.get(position).getTitle() != null ? data.get(position).getTitle() : "");
            ((NormalHolder) holder).tv_type.setText(data.get(position).getChapterName() != null ? data.get(position).getChapterName() : "");

            Random random = new Random();
            int i = random.nextInt(array.length);
            ((NormalHolder) holder).ll_normal.setCardBackgroundColor(array[i]);

            ((NormalHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String link = data.get(position).getLink();
                    Bundle bundle = new Bundle();
                    bundle.putString("title", data.get(position).getTitle());
                    bundle.putString("webUrl", link);
                    Intent intent = new Intent(mCtx, WebActivity.class);
                    intent.putExtras(bundle);
                    mCtx.startActivity(intent);
                }
            });
        }
    }


    private void setBanner(final RecyclerView.ViewHolder holder) {
        bannerAdapter = new BannerAdapter(banner, mCtx);

//        ((HeadHolder) holder).ll_guide_points.removeAllViews();
        if (((HeadHolder) holder).ll_guide_points.getChildCount() < 1) {

            for (int i = 0; i < banner.size(); i++) {
                ImageView imageView = new ImageView(mCtx);
                imageView.setBackgroundResource(R.drawable.guide_point_normal);
                // 获取10dp对应的像素值
                int dp2px = Utils.dp2px(mCtx, 6);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dp2px, dp2px);
                // 设置灰点间距
                if (i != 0) {
                    params.leftMargin = dp2px;
                }
                imageView.setLayoutParams(params);
                ((HeadHolder) holder).ll_guide_points.addView(imageView);
            }
        }
        ((HeadHolder) holder).vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (false) {
                    position = position % (banner.size());
                    int redPointX = (int) ((positionOffset + position) * Utils.dp2px(mCtx, 12));// dp2px(14)
                    if (position == banner.size()) {
                        redPointX = 0;
                    }
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ((HeadHolder) holder).iv_guide_whitepoint.getLayoutParams();
                    params.leftMargin = redPointX;
                    ((HeadHolder) holder).iv_guide_whitepoint.setLayoutParams(params);
                }

            }

            @Override
            public void onPageSelected(int position) {
                position = position % (banner.size());
                int redPointX = (int) (position) * Utils.dp2px(mCtx, 12);// dp2px(14)
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ((HeadHolder) holder).iv_guide_whitepoint.getLayoutParams();
                params.leftMargin = redPointX;
                ((HeadHolder) holder).iv_guide_whitepoint.setLayoutParams(params);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        ((HeadHolder) holder).vp.setAdapter(bannerAdapter);

    }

    @Override
    public int getItemCount() {

        if (banner.size() > 0 && data.size() > 0) {
            return data.size() + 1;
        } else if (banner.size() > 0 && data.size() == 0) {
            return 1;
        }
        return 0;
    }


    public class HeadHolder extends RecyclerView.ViewHolder {

        public ViewPager vp;
        private LinearLayout ll_guide_points;
        private ImageView iv_guide_whitepoint;

        public HeadHolder(View itemView) {
            super(itemView);
            vp = itemView.findViewById(R.id.vp);

            ll_guide_points = itemView.findViewById(R.id.ll_guide_points);
            iv_guide_whitepoint = itemView.findViewById(R.id.iv_guide_whitepoint);

            Message message = new Message();
            message.what = 1;
            message.obj = vp;
            EventBus.getDefault().post(message);

        }
    }

    class NormalHolder extends RecyclerView.ViewHolder {
        private TextView tv_author;
        private TextView tv_time;
        private TextView tv_title;
        private TextView tv_type;
        private CardView ll_normal;

        public NormalHolder(View itemView) {
            super(itemView);
            tv_author = itemView.findViewById(R.id.tv_author);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_type = itemView.findViewById(R.id.tv_type);
            ll_normal = itemView.findViewById(R.id.ll_normal);
        }
    }


}
