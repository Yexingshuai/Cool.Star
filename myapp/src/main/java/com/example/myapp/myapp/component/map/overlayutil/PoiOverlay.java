package com.example.myapp.myapp.component.map.overlayutil;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.search.poi.PoiResult;
import com.example.myapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 用于显示poi的overly
 */
public class PoiOverlay extends OverlayManager {

    private static final int MAX_POI_SIZE = 10;

    private PoiResult mPoiResult = null;
    private Context mContext;

    /**
     * 构造函数
     *
     * @param baiduMap 该 PoiOverlay 引用的 BaiduMap 对象
     */
    public PoiOverlay(BaiduMap baiduMap, Context context) {
        super(baiduMap);
        mContext = context;
    }

    /**
     * 设置POI数据
     *
     * @param poiResult 设置POI数据
     */
    public void setData(PoiResult poiResult) {
        this.mPoiResult = poiResult;
    }

    @Override
    public final List<OverlayOptions> getOverlayOptions() {
        if (mPoiResult == null || mPoiResult.getAllPoi() == null) {
            return null;
        }

        List<OverlayOptions> markerList = new ArrayList<>();
        int markerSize = 0;

        for (int i = 0; i < mPoiResult.getAllPoi().size() && markerSize < MAX_POI_SIZE; i++) {
            if (mPoiResult.getAllPoi().get(i).location == null) {
                continue;
            }

            markerSize++;
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            markerList.add(new MarkerOptions()
//                .icon(BitmapDescriptorFactory.fromAssetWithDpi("icon_mark" + markerSize + ".png"))
                    .icon(BitmapDescriptorFactory.fromResource(getResource(String.valueOf(i + 1))))
                    .extraInfo(bundle)
                    .position(mPoiResult.getAllPoi().get(i).location));

        }

        return markerList;
    }

    /**
     * 获取该PoiOverlay的poi数据
     *
     * @return POI数据
     */
    public PoiResult getPoiResult() {
        return mPoiResult;
    }

    /**
     * 覆写此方法以改变默认点击行为
     *
     * @param i 被点击的poi在
     *          {@link PoiResult#getAllPoi()} 中的索引
     * @return true--事件已经处理，false--事件未处理
     */
    public boolean onPoiClick(int i) {
//        if (mPoiResult.getAllPoi() != null
//                && mPoiResult.getAllPoi().get(i) != null) {
//            Toast.makeText(BMapManager.getInstance().getContext(),
//                    mPoiResult.getAllPoi().get(i).name, Toast.LENGTH_LONG)
//                    .show();
//        }
        return false;
    }

    @Override
    public final boolean onMarkerClick(Marker marker) {
        if (!mOverlayList.contains(marker)) {
            return false;
        }

        if (marker.getExtraInfo() != null) {
            return onPoiClick(marker.getExtraInfo().getInt("index"));
        }

        return false;
    }

    @Override
    public boolean onPolylineClick(Polyline polyline) {
        return false;
    }

    public int getResource(String postion) {
        String resStr = String.format(Locale.ENGLISH, "icon_mark%s", postion);//
        final Resources resources = mContext.getResources();
        String packageName = mContext.getApplicationInfo().packageName;
        int resId = resources.getIdentifier(resStr, "mipmap", packageName);
        if (resId != 0) {
            return resId;
        }
        return -1;
    }
}
