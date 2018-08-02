package com.example.myapp.myapp.ui.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.myapp.R;

public class HomeActivity extends AppCompatActivity {

    private View.OnClickListener mOnNavClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


        ActionBar actionBar = getSupportActionBar();

        //不显示默认的Title
//        actionBar.setDisplayShowTitleEnabled(true);

        //不显示默认的Home（an activity icon or logo.）
//        actionBar.setDisplayShowHomeEnabled(false);


        //是否显示左上角的返回按钮
//        actionBar.setDisplayHomeAsUpEnabled(false);

        //点击事件
        toolbar.setNavigationOnClickListener(mOnNavClickListener);

        toolbar.setTitle("里日天");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        toolbar.setNavigationIcon(R.mipmap.icon_back);


        //Animation.
//        getWindow().setEnterTransition(new Slide(Gravity.RIGHT).setDuration(500));
//        getWindow().setEnterTransition(new ChangeBounds().setDuration(500));
//        getWindow().setEnterTransition(new Fade().setDuration(500));
        getWindow().setEnterTransition(new Explode().setDuration(500));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.node_one_key_report_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.preview_zoom_delete:
                Toast.makeText(HomeActivity.this, "666", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
