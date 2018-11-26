# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# AvloadingView------------
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }

#HeWeather
#排除okhttp
  -dontwarn com.squareup.**
  -dontwarn okio.**
  -keep public class org.codehaus.* { *; }
  -keep public class java.nio.* { *; }

#排除HeWeather
  -dontwarn interfaces.heweather.com.interfacesmodule.**
  -keep class interfaces.heweather.com.interfacesmodule.** { *;}

#引导
-keep class com.wooplr.spotlight.** { *; }
-keep interface com.wooplr.spotlight.**
-keep enum com.wooplr.spotlight.**

#推送
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

