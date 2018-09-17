chapter2-探究活动
================

## 2.1 活动是什么

Activity是一直包含用户界面的组件,一个应用可以包含任意个活动

## 2.2 活动基本用法

add no activity后,app/src/main/java/com.example.activitytest目录为空

项目的任何activity需要重写onCreate方法

Android: 逻辑与视图分离,最好每个活动对应一个布局(用于显示界面内容)

### 2.2.2 创建布局

```android
// 布局
// res/layout/first_layout.xml
XML引用: @id/id_name
XML定义: @+id/id_name

    <Button
        android:id="@+id/button_1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Button 1"
        />

// AndroidManifest.xml
// 在Manifest中注册activity
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.atlednolispe.activitytest">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".FirstActivity"></activity>  // com.example.atlednolispe.activitytest.FirstActivity的缩写
    </application>

</manifest>

// AndroidManifest.xml
// activity的label会覆盖application的label
// 配置主活动
        <activity android:name=".FirstActivity"
            android:label="This is FirstActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

// 应用若没有声明主活动,无法在启动器看到或打开程序,一般作为第三方服务供其他应用内部调用

// preview layout未正常显示需要修改res/values/styles.xml
// Theme.xxx -> Base.Theme.xxx
<style name="AppTheme" parent="Base.Theme.AppCompat.Light.DarkActionBar">
```

### 2.2.4 Toast

```android
// new View.OnClickListener() 实例
        Button button1 = (Button) findViewById(R.id.button_1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        FirstActivity.this,
                        "You clicked Button 1",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

// back activity
// finish();
```

## 2.3 使用Intent在活动间穿梭

Intent是Android各组件交互的方式,可以指明当前组件想执行的动作,组件间传递数据,启动活动服务以及广播

```android
// 显示intent
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

// 隐式intent
// Manifest
        </activity>
        <activity android:name=".SecondActivity">
            <intent-filter>
                <action android:name="com.example.atlednolispe.activitytest.ACTION_START" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </activity>

action + category都匹配才能相应Intent

Intent只能指定一个action,但可以指定多个category
// Manifest
        <activity android:name=".SecondActivity">
            <intent-filter>
                <action android:name="com.example.atlednolispe.activitytest.ACTION_START" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="com.example.atlednolispe.activitytest.MY_CATEGORY" />
            </intent-filter>
        </activity>

// FirstActivity.java
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 默认会添加一个category "android.intent.category.DEFAULT"
                Intent intent = new Intent("com.example.atlednolispe.activitytest.ACTION_START");
                intent.addCategory("com.example.atlednolispe.activitytest.MY_CATEGORY");
                startActivity(intent);
            }
        });

// 启动浏览器
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent.ACTION_VIEW = "android.intent.action.VIEW"
                Intent intent = new Intent(Intent.ACTION_VIEW);
                // setData指定当前Intent正操作的数据
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
            }
        });

// ThirdActivity可以响应VIEW+DEFAULT的INTENT
        <activity android:name=".ThirdActivity">
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <data android:scheme="http" />
            </intent-filter>
        </activity>

// 启动系统拨号
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:10086"));
                startActivity(intent);
            }
        });
```

### 2.3.4 INTENT在活动间传递数据

```android
// 给下一个活动传递数据
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);

        Button button1 = (Button) findViewById(R.id.button_1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "Hello SecondActivity";
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                intent.putExtra("extra_data", data);
                startActivity(intent);
            }
        });
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
        // getIntent获取启动SecondActivity的Intent
        Intent intent = getIntent();
        // get[Type]Extra获取传递的相应类型的数据
        String data = intent.getStringExtra("extra_data");
        Log.d("SecondActivity", "onCreate " + data);
    }

// 返回数据给上一个活动
// FirstActivity.java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);

        Button button1 = (Button) findViewById(R.id.button_1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                // 第二个请求码参数只要唯一就可以
                // 新启动的activity被销毁后回调FirstActivity的onActivityResult,回到FirstActivity
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        /**
         * requestCode: startActivityForResult(intent, 1);的第二个参数
         * resultCode: setResult返回数据传入的结果
         * data: setResult携带返回数据的Intent
         *
         * 活动中可以调用startActivityForResult启动许多不同活动,每个活动的返回数据都会回调到onActivityResult
         */
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("data_return");
                    Log.d("FirstActivity", "onActivityResult " + returnedData);
                }
                break;
            default:
        }
    }

// SecondActivity.java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
        Button button2 = (Button) findViewById(R.id.button_2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // intent仅用来传递数据
                Intent intent = new Intent();
                intent.putExtra("data_return", "Hello FirstActivity");
                // 向上一个活动返回处理结果, OK/CANCELED
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data_return", "Hello FirstActivity Back Pressed");
        setResult(RESULT_OK, intent);
        finish();
    }
```