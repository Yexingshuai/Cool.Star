package com.example.myapp.myapp.component.schedule;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MyLocationData;
import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.component.map.LocationHelper;
import com.example.myapp.myapp.component.map.listener.LocationListener;
import com.example.myapp.myapp.data.api.WandroidApi;
import com.example.myapp.myapp.data.bean.AddscheduleResponse;
import com.example.myapp.myapp.data.http.HttpContext;
import com.example.myapp.myapp.utils.PermissonUtil;
import com.example.myapp.myapp.utils.ToastUtil;

import java.util.Calendar;
import java.util.Locale;


public class AddScheduleActivity extends BaseActivity implements TextWatcher {

    private TextView mButtonSave;
    private EditText mEt_input;
    private EditText mEt_input_address;
    private LocationHelper mLocationHelper;
    private EditText mEt_contact;
    private EditText mEt_title;
    private TextView mStartTime;
    private TextView mFinshTime;
    private Calendar calendar;
    private TimePickerDialog mStartTimePicker;
    private TimePickerDialog mEndTimePicker;
    private RelativeLayout mRlStart;
    private RelativeLayout mRlEnd;
    private DatePickerDialog mStartDatePicker;
    private DatePickerDialog mEndDatePicker;
    private SwitchCompat mSwitch;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mStartHour;
    private int mStartMin;
    private boolean isOk;

    @Override
    public int inflateContentView() {
        return R.layout.acitvity_add_schedule;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        //日程内容
        mEt_input = findViewById(R.id.et_input);
        ImageView img_location = findViewById(R.id.img_location);
        mEt_input_address = findViewById(R.id.edt_input_address);
        ImageView img_invite = findViewById(R.id.img_invite);
        mEt_contact = findViewById(R.id.et_contact);
        mEt_title = findViewById(R.id.et_title);
        mStartTime = findViewById(R.id.tv_start);
        mFinshTime = findViewById(R.id.tv_finish);
        mRlStart = findViewById(R.id.rl_start);
        mRlEnd = findViewById(R.id.rl_end);
        mSwitch = findViewById(R.id.sw_all_day);
        setCommonClickListener(mRlStart);
        setCommonClickListener(mRlEnd);
        setCommonClickListener(img_invite);
        setCommonClickListener(mStartTime);
        setCommonClickListener(mFinshTime);
        setCommonClickListener(img_location);
        mEt_input.addTextChangedListener(this);
        mEt_title.addTextChangedListener(this);
    }


    @Override
    protected void initData() {
        mLocationHelper = LocationHelper.getInstance();
        mLocationHelper.init(this, locationCallBack);
        calendar = Calendar.getInstance(Locale.CHINA);


        //初始日期
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        mYear = getIntent().getIntExtra("year", 0);
        mMonth = getIntent().getIntExtra("month", 0);
        mDay = getIntent().getIntExtra("day", 0);

//        int swithMonth = mMonth + 1;
        int swithMonth = mMonth;

        mStartTime.setText(mYear + "-" + swithMonth + "-" + mDay + " ");
        mFinshTime.setText(mYear + "-" + swithMonth + "-" + mDay + " ");

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mStartTime.setText(mYear + "-" + swithMonth + "-" + mDay + " ");
                    mFinshTime.setText(mYear + "-" + swithMonth + "-" + mDay + " ");
                }
            }
        });

        // 绑定监听器
        // 设置初始时间
        mStartTimePicker = new TimePickerDialog(this, 0,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mStartHour = hourOfDay;
                        mStartMin = minute;
                        String hour = String.valueOf(hourOfDay);
                        String min = String.valueOf(minute);
                        if (hourOfDay < 10) {
                            hour = String.valueOf("0" + hourOfDay);
                        }
                        if (minute < 10) {
                            min = String.valueOf("0" + minute);
                        }
                        mStartTime.append(hour + ":" + min);

                    }
                }

                // 设置初始时间
                , 0
                , 0, true);
        mStartTimePicker.setCancelable(false);


        // 绑定监听器
        // 设置初始时间
        mEndTimePicker = new TimePickerDialog(this, 0,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        if (true) {
                            if (hourOfDay > mStartHour || hourOfDay == mStartHour && mStartMin >= minute || isOk) {
                                String hour = String.valueOf(hourOfDay);

                                String min = String.valueOf(minute);
                                if (hourOfDay < 10) {
                                    hour = String.valueOf("0" + hourOfDay);
                                }
                                if (minute < 10) {
                                    min = String.valueOf("0" + minute);
                                }
                                mFinshTime.append(hour + ":" + min);
                            } else {
                                ToastUtil.showApp("结束时间必须大于开始时间！");
                            }
                        } else {

                        }
                    }
                }
                // 设置初始时间
                , 0
                , 0, true);
        mEndTimePicker.setCancelable(false);


        //年份

        mStartDatePicker = new DatePickerDialog(this, 0, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                monthOfYear = monthOfYear + 1;
                mStartTime.setText(year + "-" + monthOfYear + "-" + dayOfMonth + " ");

                if (mSwitch.isChecked()) {
                    mFinshTime.setText(year + "-" + monthOfYear + "-" + dayOfMonth + " ");
                } else {
                    mStartTimePicker.show();
                }

            }
        }
                // 设置初始日期
                , mYear
                , mMonth
                , mDay);
        mStartDatePicker.setCancelable(false);


        mEndDatePicker = new DatePickerDialog(this, 0, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                DatePicker endPicker = mEndDatePicker.getDatePicker();
                DatePicker startPicker = mStartDatePicker.getDatePicker();


                if (endPicker.getYear() > startPicker.getYear() ||
                        endPicker.getYear() == startPicker.getYear() && endPicker.getMonth() > startPicker.getMonth() ||
                        endPicker.getYear() == startPicker.getYear() && endPicker.getMonth() == startPicker.getMonth() && endPicker.getDayOfMonth() >= startPicker.getDayOfMonth()) {
                    // 此处得到选择的时间，可以进行你想要的操作
                    monthOfYear = monthOfYear + 1;
                    if (endPicker.getYear() == startPicker.getYear() && endPicker.getMonth() == startPicker.getMonth() && endPicker.getDayOfMonth() > startPicker.getDayOfMonth()) {
                        isOk = true;
                    }

                    mFinshTime.setText(year + "-" + monthOfYear + "-" + dayOfMonth + " ");
                    if (!mSwitch.isChecked()) {
                        mEndTimePicker.show();
                    } else {
                        mStartTime.setText(year + "-" + monthOfYear + "-" + dayOfMonth + " ");
                    }

                } else {
                    isOk = false;
//                    mEndDatePicker.getDatePicker().updateDate(mYear, mMonth, mDay);
                    mEndDatePicker.getDatePicker().updateDate(mStartDatePicker.getDatePicker().getYear(), mStartDatePicker.getDatePicker().getMonth(), mStartDatePicker.getDatePicker().getDayOfMonth());
                    int month = mStartDatePicker.getDatePicker().getMonth() + 1;
//                    mFinshTime.setText(mStartDatePicker.getDatePicker().getYear() + "-" + month + "-" + mStartDatePicker.getDatePicker().getDayOfMonth() + " ");
                    mFinshTime.setText(mStartTime.getText());
                    ToastUtil.showApp("结束时间必须大于开始时间！");
                }


            }
        }
                // 设置初始日期
                , mYear
                , mMonth
                , mDay);
        mEndDatePicker.setCancelable(false);

    }


    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_schedule_menu, menu);
        MenuItem item = menu.findItem(R.id.save);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.contact_confirm_button
                , null);
        mButtonSave = (TextView) view.findViewById(R.id.save);
        mButtonSave.setTextColor(getResources().getColor(R.color.colorTextDark2nd));
        setCommonClickListener(mButtonSave);
        item.setActionView(view);
        return true;
    }

    private LocationListener.LocationCallBack locationCallBack = new LocationListener.LocationCallBack() {
        @Override
        public void setLocationInfo(MyLocationData locData, BDLocation location) {

            mEt_input_address.setText(location.getAddrStr());
            mEt_input_address.setSelection(mEt_input_address.getText().toString().length());
            mLocationHelper.stop();
        }
    };


    private void searchContacts() {
//        Uri uri = Uri.parse("content://contacts/people");
//        Intent intent = new Intent(Intent.ACTION_PICK, uri);
//        startActivityForResult(intent, 0);


        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 0);
    }


    /**
     * 添加日程
     */
    private void addSchedule() {
        String title = mEt_title.getText().toString().trim(); //标题
        StringBuilder sb = new StringBuilder();

        sb.append(mEt_input.getText().toString().trim() + "#"); //正文
        sb.append(mEt_input_address.getText().toString().trim() + "#"); //地址
        sb.append(mEt_contact.getText().toString().trim() + "#");
        sb.append(mSwitch.isChecked() ? 0 + "#" : 1 + "#"); //
        sb.append(mStartTime.getText() + "#"); //正文
        sb.append(mFinshTime.getText() + "#"); //正文

        HttpContext httpContext = new HttpContext();
        WandroidApi wandroidApi = httpContext.createApi(WandroidApi.class);

        httpContext.execute(wandroidApi.addSchedule(title, sb.toString(), null, null, null), new HttpContext.Response<AddscheduleResponse>() {
            @Override
            public void success(AddscheduleResponse result) {
                if (result.getErrorCode() == 0) {
                    ToastUtil.showApp("添加成功！");
                    setResult(1001);
                } else {
                    ToastUtil.showApp(result.getErrorMsg());
                }

                finish();
            }

            @Override
            public void error(String error) {
                super.error(error);
                ToastUtil.showApp(error);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onClickImpl(View view) {
        super.onClickImpl(view);
        switch (view.getId()) {
            case R.id.save:
                if (mEt_input.getText().toString().trim().length() > 0 && mEt_title.getText().toString().trim().length() > 0
                        && mEt_input_address.getText().toString().trim().length() > 0) {
                    addSchedule();
                } else {
                    ToastUtil.showApp("请输入标题和内容与地址！");
                }
                break;
            case R.id.img_location:
                mLocationHelper.startLocation();
                break;
            case R.id.img_invite:
                PermissonUtil.requestPermisson(this, Manifest.permission.READ_CONTACTS, "读取联系人", new PermissonUtil.PermissonListener() {
                    @Override
                    public void onPermissonGranted() {
                        searchContacts();
                    }

                    @Override
                    public void onPermissonDenied() {
//                        ToastUtil.showApp("权限被拒绝");
                    }
                });

                break;
            case R.id.rl_start:
            case R.id.tv_start:

                mStartDatePicker.show();

                break;
            case R.id.rl_end:
            case R.id.tv_finish:

                mEndDatePicker.show();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (s.length() > 0) {
            if (mEt_title.getText().toString().length() > 0 & mEt_input.getText().toString().length() > 0
                    & mEt_input_address.getText().toString().trim().length() > 0) {

                mButtonSave.setTextColor(getResources().getColor(R.color.black));
            } else {
                mButtonSave.setTextColor(getResources().getColor(R.color.colorTextDark2nd));
            }
        } else {
            mButtonSave.setTextColor(getResources().getColor(R.color.colorTextDark2nd));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationHelper.stop();
        mLocationHelper = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 0:
                if (data == null) {
                    return;
                }
                Uri uri = data.getData();
                ContentResolver cr = getContentResolver();
                Cursor cursor = cr.query(uri, null, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    //取得联系人姓名
                    int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                    String name = cursor.getString(nameFieldColumnIndex);
                    String contact = mEt_contact.getText().toString().trim();
                    if (contact.length() > 0) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(contact + ",");
                        sb.append(name);
                        mEt_contact.setText(sb.toString());
                    } else {
                        mEt_contact.setText(name);
                    }
                    mEt_contact.setSelection(mEt_contact.getText().toString().trim().length());
                    cursor.close();
                }
                break;
        }
    }
}
