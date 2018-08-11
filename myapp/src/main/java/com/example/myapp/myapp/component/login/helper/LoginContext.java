package com.example.myapp.myapp.component.login.helper;

/**
 * Created by yexing on 2018/8/10.
 */

import com.example.myapp.myapp.base.MyApp;
import com.example.myapp.myapp.common.AppFlag;
import com.example.myapp.myapp.utils.PreferencesUtils;
import com.example.myapp.myapp.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 登录环境控制类
 * </p>
 */
public class LoginContext {

    private LoginContext() {

    }

    public static LoginContext getInstance() {
        return Holder.INSTANCE;
    }


    static class Holder {
        private static LoginContext INSTANCE = new LoginContext();
    }

    /**
     * 判断是否为登录状态
     *
     * @return
     */
    public boolean isLogined() {
        return PreferencesUtils.contains(MyApp.mContext, AppFlag.USERCOOKIE);
    }

    /**
     * 退出登录
     *
     * @return
     */
    public boolean logout() {
        if (!isLogined()) {
            ToastUtil.showApp("您已退出，无法再次操作退出登录！");
            return false;
        } else {
            return clearUserInfo();
        }
    }

    /**
     * 清除用户信息
     *
     * @return
     */
    private boolean clearUserInfo() {
        return PreferencesUtils.remove(MyApp.mContext, AppFlag.USERCOOKIE);
    }

    /**
     * 保存用户Cookie信息
     *
     * @param cookies
     */
    public void saveUserCookieInfo(List<String> cookies) {
        String userName = cookies.get(1);
        userName = userName.substring(0, userName.indexOf(";") + 1);  //loginUserName
        String psd = cookies.get(2);
        psd = psd.substring(0, psd.indexOf(";") + 1);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(userName + "-");
        stringBuffer.append(psd);
        String s = stringBuffer.toString();
        PreferencesUtils.putString(MyApp.mContext, AppFlag.USERCOOKIE, s);
    }

    /**
     * 获取用户Cookie信息
     */
    public ArrayList<String> getUserCookieInfo() {
        ArrayList<String> cookies = new ArrayList<>();
        String string = PreferencesUtils.getString(MyApp.mContext, AppFlag.USERCOOKIE, null);
        if (string != null) {
            String[] split = string.split("-");
            String userName = split[0];
            String psd = split[1];
            cookies.add(userName);
            cookies.add(psd);
            return cookies;
        }
        return null;
    }

    /**
     * 获取用户账户名称
     *
     * @return
     */
    public String getUserName() {
        String userInfo = PreferencesUtils.getString(MyApp.mContext, AppFlag.USERCOOKIE, null);
        if (userInfo != null) {
            String[] split = userInfo.split("-");
            String userName = split[0];
            String subName = userName.substring(14, userName.indexOf(";"));
            return subName;
        } else {
            return null;
        }
    }


}
