package com.example.myapp.myapp.ui.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.wooplr.spotlight.SpotlightView;

/**
 * Created by yexing on 2018/9/28.
 */

public class GuidanceHelper {

    public static void guide(Context context, View view, String uniqueId,String text) {
        SpotlightView.Builder builder = new SpotlightView.Builder((Activity) context)
                .introAnimationDuration(400)
                .enableRevealAnimation(false)
                .performClick(true)
                .fadeinTextDuration(400)
                .headingTvColor(Color.parseColor("#eb273f"))
                .headingTvSize(32)
                .headingTvText("Hello")
                .subHeadingTvColor(Color.parseColor("#ffffff"))
                .subHeadingTvSize(16)
                .subHeadingTvText(text)
                .maskColor(Color.parseColor("#dc000000"))
                .target(view)
                .lineAnimDuration(400)
                .lineAndArcColor(Color.parseColor("#eb273f"))
                .dismissOnTouch(true)
                .dismissOnBackPress(true)
                .enableDismissAfterShown(true)
                .usageId(uniqueId);//UNIQUE ID
        builder.show();
    }
}
