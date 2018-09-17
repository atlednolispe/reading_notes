chapter1-开始启程,你的第一行Android代码
====================================

## 1.1 了解全貌

### 1.1.1 Android系统架构

1. Linux内核层
Linux内核为硬件提供底层驱动

2. 系统运行库层
C/C++库提供主要特性支持
Android运行时库,提供一些核心库,可以使用Java编写Android应用.Android运行时库包含Dalvik虚拟机(5.0后为ART环境),每个Android应用运行于独立进程,并且拥有自己的Dalvik虚拟机实例

3. 应用框架层
提供构建应用时用到的各种API

4. 应用层
所有安装在手机上的应用程序都属于这一层

### 1.1.3 Android应用开发特色

1. 四大组件
Activity, Service, Broadcast Receiver, Content Provider
凡是应用中能看到的都在activity中,即使退出应用Service也可以后台运行,Broadcast Receiver允许应用接受各处的广播,也可以向外发出广播,Content Provider给应用间提供数据共享

## 1.3 创建第一个Android应用

### 1.3.1 HelloWorld

Application name: 安装到手机显示的应用名
Package name: Android根据包名区分不同应用程序,包名需要唯一

配置Mac Android Studio 3.1.4:

1.

settings-build-gradle studio
check + enable embedded Maven repository

2.

#gradle-wrapper.properties
distributionUrl=https\://services.gradle.org/distributions/gradle-4.5-all.zip

之后build-clean project

rm -rf .gradle/caches

3.

sync gradle

Mac如果遇到无法正确import一些包,尝试File - Invalidate Caches

### 1.3.4 分析第一个Android程序

1. .gradle, .idea
Android Studio生成的文件

2. app
code, source, 开发基本在这个目录

```
1. build
编译时自动生成的文件

2. libs
将第三方jar包放到libs下,这个目录下的jar都会被自动添加到构建路径

3. androidTest
Android Test测试用例

4. java
放置java文件的路径

5. res
项目用到的图片,布局,字符串等资源
drawable: 图片
layout: 布局
mipmap: 图标,若只有一份图片就放在drawable-xxhdpi
values: 字符串,样式,颜色...

6. AndroidManifest.xml
整个Android的配置文件,四大组件都要在这里注册,还可以给应用程序添加权限声明

7. test
Unit Test

8. .gitignore

9. app.iml
IDEA生成

10. build.gradle
app模块的构建脚本

11. proguard-rules.pro
指定项目代码的混淆规则,生成安装包是会将代码混淆
```

3. build
编译时自动生成的文件

4. gradle
gradle wrapper配置文件,使用gradle wrapper不需要提前下载gradle,自动更加本地缓存决定是否联网下载gradle,默认启用gradle wrapper

5. .gitignore

6. build.gradle
项目全局的gradle构建脚本

7. gradle.properties
全局gradle配置文件,影响项目全部的gradle编译脚本

8. gradlew, gradlew.bat
用于在cmd中执行gradle命令,Linux/Windows

9. HelloWorld.iml
IDEA生成,标识是一个IDEA项目

10. local.properties
指定本机Android SDK路径

11. settings.gradle
指定项目中所有引入的模块

```
# AndroidManifest.xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.atlednolispe.helloworld">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".HelloWorldActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>

<activity>...</activity>: 对HelloWorldActivity注册,没有在Manifest注册的活动不能使用
MAIN说明HelloWorldActivity是这个项目主活动,LAUNCHER说明点击应用图标,首先启动时这个活动
```

### 1.3.5 res

通过XML语法在AndroidManifest中引用

## build.gradle

外层:
repositories指定代码仓库,dependencies声明gradle插件来构建Android

app:
apply plugin: 'com.android.application'/'com.android.library'
application可以直接运行,library只能依赖别的应用程序运行

applicationId: 用于修改包名
targetSdkVersion: 启用特性的最高版本

buildTypes: 安装文件配置
minifyEnabled: 是否混淆
proguardFiles: 混淆规则文件

AS直接运行项目生成的都是debug版文件

本地依赖: fileTree
库: ':helper'
远程依赖: 'com.android.support:appcompat-v7:28.+' - 域名:组:版本号
测试用例库: 'junit:junit:4.12'

## 1.4 掌握日志工具

android.util.Log

1. Log.v(): verbose,最低一级日志
2. Log.d(): 调试
3. Log.i(): info,分析用户行为数据
4. Log.w()
5. Log.e()

tag: 一般传入当前类名,类中快捷键logt
msg: 打印的具体内容, 方法名 info
Log.d("HelloWorldActivity", "onCreate execute");
