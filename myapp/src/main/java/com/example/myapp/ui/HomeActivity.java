package com.example.myapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;

import com.example.myapp.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        //Animation.
//        getWindow().setEnterTransition(new Slide(Gravity.RIGHT).setDuration(500));
//        getWindow().setEnterTransition(new ChangeBounds().setDuration(500));
//        getWindow().setEnterTransition(new Fade().setDuration(500));
        getWindow().setEnterTransition(new Explode().setDuration(500));
    }
}
