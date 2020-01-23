package com.example.myapp.myapp.component.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.component.login.helper.LoginContext;
import com.example.myapp.myapp.data.api.WandroidApi;
import com.example.myapp.myapp.data.bean.ScheduleListResponse;
import com.example.myapp.myapp.data.http.HttpContext;
import com.example.myapp.myapp.ui.adapter.OnItemClickListener;
import com.example.myapp.myapp.ui.adapter.RecyclerAdapter;
import com.example.myapp.myapp.ui.adapter.RecyclerHolder;
import com.example.myapp.myapp.ui.helper.UiHelper;
import com.example.myapp.myapp.utils.ToastUtil;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodayThingActivity extends BaseActivity implements
        CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener, CalendarView.OnCalendarLongClickListener {

    private TextView mTextMonthDay;
    private TextView mTextYear;
    private TextView mTextLunar;
    private TextView mTextCurrentDay;
    private RelativeLayout mRelativeTool;
    private CalendarView mCalendarView;
    private CalendarLayout mCalendarLayout;
    private int mYear;
    private int mMonth;
    private int mDay;
    private RecyclerView mRecyclerView;
    private List<ScheduleListResponse.DataBean.DatasBean> mList = new ArrayList();
    private RecyclerAdapter mAdapter;


    @Override
    public int inflateContentView() {
        return R.layout.activity_todaything;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {


        mTextMonthDay = getView(R.id.tv_month_day);  //几月几号
        mTextYear = getView(R.id.tv_year);
        mRelativeTool = getView(R.id.rl_tool);
        mTextLunar = getView(R.id.tv_lunar);
        mCalendarView = getView(R.id.calendarView);
        mTextCurrentDay = getView(R.id.tv_current_day);
        mCalendarLayout = getView(R.id.calendarLayout);
        mRecyclerView = getView(R.id.recyclerView);
        FrameLayout fl_Current = getView(R.id.fl_current);
        setCommonClickListener(fl_Current);
        setCommonClickListener(mTextMonthDay);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnCalendarLongClickListener(this, false);
    }

    @Override
    protected void initData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mYear = mCalendarView.getCurYear();
        mMonth = mCalendarView.getCurMonth();
        mDay = mCalendarView.getCurDay();
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));

        mAdapter = new RecyclerAdapter<ScheduleListResponse.DataBean.DatasBean>(R.layout.item_schedule, mList, onItemClickListener) {
            @Override
            protected void onBindHolder(RecyclerHolder holder, ScheduleListResponse.DataBean.DatasBean model, int position) {
                String content = model.getContent();
                String[] splitContent = content.split("#");
                if (splitContent.length == 7) {
                    holder.text(R.id.tv_title, model.getTitle());
                    holder.text(R.id.tv_content, splitContent[0]);
                    String isAllday = splitContent[3];
                    String startTime = splitContent[4];
                    if (!TextUtils.isEmpty(isAllday)) {
                        if (TextUtils.equals("0", isAllday)) {//全天
                            holder.text(R.id.txt_start_time, "全天");

                            if (!TextUtils.isEmpty(startTime)) {
                                holder.text(R.id.tv_date, startTime);
                            }

                        } else { //不是全天
                            holder.text(R.id.tv_date, startTime);
                            String[] time = startTime.split(" ");

                            //开始时间
                            if (!TextUtils.isEmpty(time[1])) {
                                holder.text(R.id.txt_start_time, time[1]);
                            }
                            //结束时间
                            String endTime = splitContent[5];
                            String[] endTimes = endTime.split(" ");
                            if (endTimes.length == 2) {
                                holder.text(R.id.txt_end_time, endTimes[1]);
                            }


                        }

                    }

                } else {
                    holder.text(R.id.tv_title, "");
                    holder.text(R.id.tv_content, "");
                    holder.text(R.id.txt_start_time, "");
                    holder.text(R.id.tv_date, "");
                }


            }
        };
        mRecyclerView.setAdapter(mAdapter);
        requestTodoList();
    }

    /**
     * 条目点击事件
     */
    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            ScheduleListResponse.DataBean.DatasBean datasBean = mList.get(position);
            Intent intent = new Intent(TodayThingActivity.this, PreviewScheduleActivity.class);
            intent.putExtra(PreviewScheduleActivity.SCHEDULEINFO, datasBean);
            startActivityForResult(intent, 2);
        }
    };

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

    /**
     * 请求todo列表
     */
    private void requestTodoList() {
        HttpContext httpContext = new HttpContext();
        WandroidApi api = httpContext.createApi(WandroidApi.class);
        httpContext.execute(api.getScheduleList(), new HttpContext.Response<ScheduleListResponse>() {
                    @Override
                    public void success(ScheduleListResponse result) {
                        if (result.getErrorCode() == 0) {

                            List<ScheduleListResponse.DataBean.DatasBean> datas = result.getData().getDatas();
                            if (datas != null && datas.size() >= 0) {
                                mList.clear();
                                mList.addAll(datas);
                                mAdapter.notifyDataSetChanged();
                                if (mList.size() > 0) {
                                    renderLogo();
                                } else {
                                    renderDefaultLogo();
                                }

                            }
                        } else {
                            renderDefaultLogo();
                            ToastUtil.showApp(result.getErrorMsg());
                        }
                    }

                }
        );
    }


    //默认状态下填充logo
    private void renderDefaultLogo() {

        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();

        mCalendarView.clearSchemeDate();
        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(year, month, 3, 0xFF40db25, "假").toString(),
                getSchemeCalendar(year, month, 3, 0xFF40db25, "假"));
        map.put(getSchemeCalendar(year, month, 6, 0xFFe69138, "事").toString(),
                getSchemeCalendar(year, month, 6, 0xFFe69138, "事"));
        map.put(getSchemeCalendar(year, month, 9, 0xFFdf1356, "议").toString(),
                getSchemeCalendar(year, month, 9, 0xFFdf1356, "议"));
        map.put(getSchemeCalendar(year, month, 13, 0xFFedc56d, "记").toString(),
                getSchemeCalendar(year, month, 13, 0xFFedc56d, "记"));
        map.put(getSchemeCalendar(year, month, 14, 0xFFedc56d, "记").toString(),
                getSchemeCalendar(year, month, 14, 0xFFedc56d, "记"));
        map.put(getSchemeCalendar(year, month, 15, 0xFFaacc44, "假").toString(),
                getSchemeCalendar(year, month, 15, 0xFFaacc44, "假"));
        map.put(getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记").toString(),
                getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记"));
        map.put(getSchemeCalendar(year, month, 25, 0xFF13acf0, "假").toString(),
                getSchemeCalendar(year, month, 25, 0xFF13acf0, "假"));
        map.put(getSchemeCalendar(year, month, 27, 0xFF13acf0, "多").toString(),
                getSchemeCalendar(year, month, 27, 0xFF13acf0, "多"));
        mCalendarView.setSchemeDate(map);

    }

    /**
     * 渲染图标
     */
    private void renderLogo() {
        mCalendarView.clearSchemeDate();
        Map<String, Calendar> map = new HashMap<>();
        int year, month, day, color;
        for (int i = 0; i < mList.size(); i++) {
            ScheduleListResponse.DataBean.DatasBean datasBean = mList.get(i);
            String content = datasBean.getContent();
            String[] contentSplit = content.split("#");
            if (contentSplit.length == 7) {
                String startTime = contentSplit[4];
                String[] startTimeSplit = startTime.split(" ");
                String[] time = startTimeSplit[0].split("-");
                year = Integer.parseInt(time[0]);
                month = Integer.parseInt(time[1]);
                day = Integer.parseInt(time[2]);
                String thingType = contentSplit[6];
                color = getColor(thingType);
                map.put(getSchemeCalendar(year, month, day, color, thingType).toString(),
                        getSchemeCalendar(year, month, day, color, thingType));
                mCalendarView.setSchemeDate(map);
            }

        }
    }

    /**
     * 获取图标颜色
     *
     * @param type
     * @return
     */
    private int getColor(String type) {
        int color = 0;
        switch (type) {
            case "事":
                color = 0xFFe69138;
                break;
            case "议":
                color = 0xFFdf1356;
                break;
            case "假":
                color = 0xFF40db25;
                break;
            case "玩":
                color = 0xFF13acf0;
        }
        return color;
    }


    @Override
    protected void onClickImpl(View view) {
        switch (view.getId()) {
            case R.id.tv_month_day:
                if (!mCalendarLayout.isExpand()) {
                    mCalendarLayout.expand();
                    return;
                }
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));
                break;

            case R.id.fl_current:
                mCalendarView.scrollToCurrent();
                break;
        }
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
        mMonth = calendar.getMonth();
        mDay = calendar.getDay();

    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }

    @Override
    public void onCalendarLongClickOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarLongClick(Calendar calendar) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todaything_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_schedule:
                if (!LoginContext.getInstance().isLogined()) {
                    ToastUtil.showApp("请先登录！");
                    return super.onOptionsItemSelected(item);
                }
                Intent intent = new Intent(this, AddScheduleActivity.class);
                intent.putExtra("year", mYear);
                intent.putExtra("month", mMonth);
                intent.putExtra("day", mDay);
                startActivityForResult(intent, 1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        requestTodoList();
    }
}
