chapter2-探究活动
================

## 2.1 活动是什么

Activity是一直包含用户界面的组件,一个应用可以包含任意个活动

## 2.2 活动基本用法

add no activity后,app/src/main/java/com.example.activitytest目录为空

项目的任何activity需要重写onCreate方法

Android: 逻辑与视图分离,最好每个活动对应一个布局(用于显示界面内容)

### 2.2.2 创建布局

```xml
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

```java
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

```java
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

```java
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

## 2.4 活动的生命周期

### 2.4.1 返回栈

Task = 一组在back stack中的acitvities,系统总是显示栈顶的活动给用户

### 2.4.2 活动状态

1. 运行状态: 栈顶的活动处于运行状态,最不愿意回收
2. 暂停状态: 活动不再处于栈顶但仍然可见(不是所有活动都会占满整个屏幕),仍完全存活,内存极低时才回收
3. 停止状态: 不再栈顶且完全不可见,系统会保持相应状态和成员变量,但可能会被系统回收
4. 销毁状态: 从返回栈移除后就成为销毁状态

### 2.4.3 活动的生存期

Activity的7个回调方法: 除onStart,两两相对
1. onCreate(): 活动第一次创建时调用,完成活动初始化操作(加载布局,绑定事件)
2. onStart(): 不可见变可见时调用
3. onResume(): 准备好和用户交互时调用,活动必定处于栈顶且运行状态
4. onPause(): 准备启动或者恢复另一个活动时调用,通常用于释放消耗CPU的资源,保持一些关键数据,这个方法执行速度要快,不然影响新的栈顶活动的使用
5. onStop(): 活动完全不可见时调用.和onPause区别: 启动新活动是对话框,则onPause会执行,onStop不执行
6. onDestroy(): 活动销毁前调用,调用后处于销毁状态
7. onRestart(): 活动由停止状态变为运行状态前调用

1. 完整生存期: onCreate -> onDestroy,onCreate完成初始化,onDestroy释放内存
2. 可见生存期: onStart -> onStop,此期间活动对用户总是可见,即使不能交互
3. 前台生存期: onResume -> onPause,活动处于运行且可与用户交互,看到最多是这个状态的活动

ActivityLifeCycleTest了解整个周期

### 2.4.5 活动被回收

停止状态的活动被回收后back不是调用onRestart,而是使用onCreate

通过onSaveInstanceState回调,在活动回收前必定被调用,解决数据保存问题

```java
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String tempData = "Something you just typed";
        outState.putString("data_key", tempData);
        Log.d(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.main_layout);
        // 如果被手动关闭应用是不会有保存状态的
        if (savedInstanceState != null) {
            String tempData = savedInstanceState.getString("data_key");
            Log.d(TAG, tempData);
        }

        Button startNormalActivity = (Button) findViewById(R.id.start_normal_activity);
        Button startDialogActivity = (Button) findViewById(R.id.start_dialog_activity);

        startNormalActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NormalActivity.class);
                startActivity(intent);
            }
        });
        startDialogActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DialogActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }
}
```