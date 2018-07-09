package service;


import android.app.Service;
import android.content.Intent;

import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


import com.carmelo.library.KeepliveService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yexing on 2018/4/26.
 */

public class MapLocationSerivce extends KeepliveService {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("fgMapLocationService---", "onCreate");
//        EventBus.getDefault().register(this);

        //发送网络请求
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Log.e("fg......", "12112");
            }
        };
        Timer timer = new Timer();

        timer.schedule(task, 5000, 5000);
    }
//
//    @Override
//    public void onStart(Intent intent, int startId) {
//        super.onStart(intent, startId);
//        Log.e("onStart---", "eeeee");
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }
//
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(LocationInfo info) {
//        Double latitude = info.getLatitude();
//        Double longitude = info.getLongitude();
//        Log.e("In Service---", "latitude--" + 111 + "----longitude---" + 222);
//    }


//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        //设置service为前台服务，提高优先级
////        if (Build.VERSION.SDK_INT < 18) {
////            //Android4.3以下 ，此方法能有效隐藏Notification上的图标
////            this.startForeground(GRAY_SERVICE_ID, new Notification());
////        } else if(Build.VERSION.SDK_INT>18 && Build.VERSION.SDK_INT<25){
////            //Android4.3 - Android7.0，此方法能有效隐藏Notification上的图标
////            Intent innerIntent = new Intent(service, GrayInnerService.class);
////            service.startService(innerIntent);
////            service.startForeground(GRAY_SERVICE_ID, new Notification());
////        }else{
////            //Android7.1 google修复了此漏洞，暂无解决方法（现状：Android7.1以上app启动后通知栏会出现一条"正在运行"的通知消息）
////            service.startForeground(GRAY_SERVICE_ID, new Notification());
////        }
////        return super.onStartCommand(intent, flags, startId);
//        return START_STICKY; //服务被异常终止后，尝试重启，不能保证百分百成功
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int i = super.onStartCommand(intent, flags, startId);
        //do something
        Log.e("fgMapLocationService---", "onStartCommand----");
        Log.d("keeplive", "DemoService process = " + android.os.Process.myPid());
        return i;
    }
}
