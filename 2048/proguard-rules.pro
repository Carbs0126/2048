#指定压缩级别
-optimizationpasses 5

#忽略警告
-ignorewarnings

#不跳过非公共的库的类成员
-dontskipnonpubliclibraryclassmembers

#混淆时采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#把混淆类中的方法名也混淆了
-useuniqueclassmembernames

#优化时允许访问并修改有修饰符的类和类的成员
-allowaccessmodification

#将文件来源重命名为“SourceFile”字符串
-renamesourcefileattribute SourceFile
#保留行号
-keepattributes SourceFile,LineNumberTable

#保持所有实现 Serializable 接口的类成员
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keep public class * extends android.app.Activity

#Fragment不需要在AndroidManifest.xml中注册，需要额外保护下
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment

# 保持测试相关的代码
-dontnote junit.framework.**
-dontnote junit.runner.**
-dontwarn android.test.**
-dontwarn android.support.test.**
-dontwarn org.junit.**

#R
-keepclassmembers class **.R$* {
    public static <fields>;
}

#枚举
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#js
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

#native
-keepclasseswithmembernames class * {
    native <methods>;
}


#反射，依赖注入，注解
-keep class com.google.inject.Binder
-keepclassmembers class * {
    @com.google.inject.Inject <init>(...);
}

-dontwarn java.lang.invoke.*
-dontwarn javax.annotation.*
-dontwarn javax.annotation.**

#系统接口回调，如onEvent
-keepclassmembers class * {
    void *(**On*Event);
}
-keepclassmembers,includedescriptorclasses class ** { public void on*Event*(**); }

#android support library
-dontwarn android.support.**
-keep class android.support.** { *; }
-keep interface android.support.** { *; }
-dontwarn  java.lang.reflect.*
-dontwarn org.**
-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }


-dontwarn com.tencent.**
-keep class com.tencent.** { *;}

-dontwarn com.qq.**
-keep class com.qq.** { *;}

#GSON
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-dontwarn com.google.**
-keep class com.google.gson.** {*;}


# Retrofit and OkHttp
-dontwarn com.squareup.okhttp3.**
-dontwarn okio.**
-dontwarn retrofit2.**

-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

-keep class cn.carbs.a2048.net.json.** {*;}

