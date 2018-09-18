chapter3-软件也要拼脸蛋
=====================

## 3.1 如何编写程序界面

推荐使用XML来编写界面

## 3.2 常用控件的使用方法

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!--match_parent: 和父布局一样-->
    <!--wrap_content: 包含内容一样大小-->
    <!--gravity: 文字对齐方式, center = center_vertical|center_horizontal-->
    <TextView
        android:id="@+id/text_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:textSize="24sp"
        android:textColor="#00ff00"
        android:text="This is TextView" />

    <!--默认对Button中英文大写-->
    <Button
        android:id="@+id/button"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Button"
        android:textAllCaps="false" />

    <!--maxLines指定最大行数,不会继续拉伸,文本可以滚动-->
    <EditText
        android:id="@+id/edit_text"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Type something here"
        android:maxLines="2" />

    <!--drawable-xhdpi来指定分辨率放置图片,drawable下不放置图片-->
    <ImageView
        android:id="@+id/image_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@drawable/img_1"/>

    <!--android:visibility-->
    <!--visible: 默认可见-->
    <!--invisible: 仍占据原来位置,可理解为透明-->
    <!--gone: 不可见且不占据空间-->
    <ProgressBar
        android:id="@+id/progress_bar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        style="?android:attr/progressBarStyleHorizontal"
        android:max="100" />

    <!--置顶于所有界面元素之上,屏蔽其他控件交互能力-->
    <!--dialog-->

</LinearLayout>
```

```java
// 注册监听器1
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ...
            }
        });
    }
}

// 注册监听器2
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // ...
    }
}

// 获取TextView
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button:
                        String inputText = editText.getText().toString();
                        Toast.makeText(MainActivity.this, inputText, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

// 修改图片
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button:
                        imageView.setImageResource(R.drawable.img_2);
                        break;
                    default:
                        break;
                }
            }
        });

// 进度条设置
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button:
//                        if (progressBar.getVisibility() == View.GONE) {
//                            progressBar.setVisibility(View.VISIBLE);
//                        } else {
//                            progressBar.setVisibility(View.GONE);
//                        }

//                        最多增加到110
                        int progress = progressBar.getProgress();
                        progress += 10;
                        progressBar.setProgress(progress);
                        Log.d("MainActivity", Integer.toString(progress));
                        break;
                    default:
                        break;
                }
            }
        });

// 对话框
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button:
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setTitle("This is Dialog");
                        dialog.setMessage("Something important.");
                        // 是否可以用back键关闭对话框
                        dialog.setCancelable(false);
                        // 按钮确定事件
                        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        // 按钮取消事件
                        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        dialog.show();
                        break;
                    default:
                        break;
                }
            }
        });

// 进度条对话框
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button:
                        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                        progressDialog.setTitle("This is ProgressDialog");
                        progressDialog.setMessage("Loading...");
                        progressDialog.setCancelable(true);
                        progressDialog.show();

                        // 加载完后需要调用dismiss关闭对话框,否则一直存在
                        progressDialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });
```