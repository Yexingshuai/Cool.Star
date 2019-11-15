package com.example.myapp.myapp.component.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.component.map.listener.LocationListener;
import com.example.myapp.myapp.component.map.overlayutil.PoiOverlay;
import com.example.myapp.myapp.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends BaseActivity {


    private MapView mMapView = null;
    private BaiduMap mMapViewMap;
    private LocationHelper mLocationHelper;
    private boolean isFirstIn = true;
    private ImageView mIvLocation;
    private PoiSearch mPoiSearch;

    /**
     * 当前经纬度信息
     *
     * @return
     */
    private double mlatitude;
    private double mlongitude;

    private static final int Distance = 200;// 默认搜索范围

    @Override
    public int inflateContentView() {
        return R.layout.activity_map;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        mMapView = (MapView) findViewById(R.id.mapView);
        mIvLocation = getView(R.id.img_location);
        setCommonClickListener(mIvLocation);
        mMapViewMap = mMapView.getMap();
        //普通地图 ,mBaiduMap是地图控制器对象
        mMapViewMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
    }

    @Override
    protected void initData() {

        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18.0f);//设置缩放等级
        mMapViewMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        mMapViewMap.setMyLocationEnabled(true);  //开启地图的定位图层  标识当前位置。

        mMapViewMap.setIndoorEnable(true);//打开室内图，默认为关闭状态

        mMapViewMap.showMapPoi(true); //显示poi

        mLocationHelper = LocationHelper.getInstance();
        mLocationHelper.init(this, locationCallBack);
        mLocationHelper.startLocation();//显示定位

        UiSettings uiSettings = mMapViewMap.getUiSettings();
        mMapView.showZoomControls(false); //去掉缩放按钮

        //poi
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);


        checkNearPoi(Distance);
        //Marker设置点击事件
        mMapViewMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                PoiInfo info = (PoiInfo) marker.getExtraInfo().get("marker");//点击的marker
                createInfoWindow(info);
                return true;
            }
        });
    }


    /**
     * 展示弹框
     *
     * @param bean
     */
    private void createInfoWindow(PoiInfo bean) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_marker, null);
        TextView tv_location = view.findViewById(R.id.tv_location);
        TextView tv_go = view.findViewById(R.id.tv_go); //去这里
        setCommonClickListener(tv_go);
        tv_location.setText(bean.address);
        InfoWindow infoWindow = new InfoWindow(view, bean.location, -100);
        //使InfoWindow生效
        mMapViewMap.showInfoWindow(infoWindow);
    }


    //请求附近的数据
    private void checkNearPoi(int distance) {
        mPoiSearch.searchNearby(new PoiNearbySearchOption()
                .location(new LatLng(mlatitude, mlongitude))
                .radius(distance)
                .keyword("公共厕所")
                .pageNum(0));
    }


    //设置检索监听器
    private OnGetPoiSearchResultListener onGetPoiSearchResultListener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                mMapViewMap.clear();

                List<PoiInfo> allPoi = poiResult.getAllPoi();
                addMarker(allPoi);


//                //创建PoiOverlay对象
//                PoiOverlay poiOverlay = new PoiOverlay(mMapViewMap, MapActivity.this);
//
//                //设置Poi检索数据
//                poiOverlay.setData(poiResult);
//
//                //将poiOverlay添加至地图并缩放至合适级别
//                poiOverlay.addToMap();
//                poiOverlay.zoomToSpan();


            } else {
                mMapViewMap.clear();
                ToastUtil.showApp("对不起，当前距离范围内没有搜索到厕所，请扩大范围！");
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }

        //废弃
        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }
    };

    private LocationListener.LocationCallBack locationCallBack = new LocationListener.LocationCallBack() {
        @Override
        public void setLocationInfo(MyLocationData locData, BDLocation location) {
            mlatitude = locData.latitude;
            mlongitude = locData.longitude;

            if (isFirstIn) {
                isFirstIn = false;
                LatLng latLng = new LatLng(mlatitude, mlongitude);
                MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(latLng);
                mMapViewMap.animateMapStatus(status);  //带有动画的移动到中心点
            } else {
                mMapViewMap.setMyLocationData(locData);
            }

        }
    };


    /**
     * 添加marker
     */
    private void addMarker(List<PoiInfo> allPoi) {

        for (int i = 0; i < allPoi.size(); i++) {
            PoiInfo poiInfo = allPoi.get(i);
            //定义Maker坐标点
            LatLng point = new LatLng(poiInfo.getLocation().latitude, poiInfo.getLocation().longitude);
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.mipmap.toilet_bs);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point) //必传参数
                    .icon(bitmap) //必传参数
                    .draggable(true)
                    //设置平贴地图，在地图中双指下拉查看效果
                    .flat(true)
                    .alpha(0.5f);
            //在地图上添加Marker，并显示
            Marker marker = (Marker) mMapViewMap.addOverlay(option);//显示一个
            Bundle bundle = new Bundle();
            bundle.putParcelable("marker", poiInfo);   //携带对象数据
            marker.setExtraInfo(bundle);
        }

//        mMapViewMap.addOverlays(list);//显示一组

    }

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mMapView = null;
        mMapViewMap.setMyLocationEnabled(false);
        mLocationHelper.stop();
        mLocationHelper = null;
        mPoiSearch.destroy();

    }

    @Override
    protected void onClickImpl(View view) {
        super.onClickImpl(view);
        switch (view.getId()) {
            case R.id.img_location:
                isFirstIn = true;
                break;
            case R.id.tv_go:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.find_200:
                checkNearPoi(200);
                break;
            case R.id.find_500:
                checkNearPoi(500);
                break;
            case R.id.find_1000:
                checkNearPoi(1000);
                break;
        }
        return true;
    }


    public class InfoWindowHolder {


    }
}
