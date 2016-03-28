package com.test.frank.allandroidlab;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
/**
 * Created by Frank on 16/3/16.
 */
public class MainActivity extends AppCompatActivity {

    private Button btnstart,btnstop,btnmap,btncamera,btnmulti;
    private ImageView ivCamera;
    public static final String TAG = "MainActivity";
    private static final String FILE_PATH = "/sdcard/syscamera.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBtn();

    }

    private void initBtn() {
        btnstart = (Button) findViewById(R.id.btnstart);
        btnstop = (Button) findViewById(R.id.btnstop);
        btnstop.setVisibility(View.GONE);
        btnmap= (Button) findViewById(R.id.btnmap);
        btncamera= (Button) findViewById(R.id.btncamera);
        btnmulti= (Button) findViewById(R.id.btnmulti);
        ivCamera= (ImageView) findViewById(R.id.ivcamera);

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(getBaseContext(),MyService.class));
                btnstart.setVisibility(View.GONE);
                btnstop.setVisibility(View.VISIBLE);
            }
        });

        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(getBaseContext(), MyService.class));
                btnstart.setVisibility(View.VISIBLE);
                btnstop.setVisibility(View.GONE);
            }
        });
        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });

        btnmulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getBaseContext(),MultiTestActivity.class));
            }
        });

        btncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getBaseContext(),MyCamera.class));

                //SavePicInUri();


                SavePicNoUri();
            }

            /**
             指定相机拍摄照片保存地址
             */

            private void SavePicNoUri() {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivityForResult(intent, 1);
            }
            /**
             * // 不指定相机拍摄照片保存地址
             *
             */
            private void SavePicInUri() {
                Intent intent = new Intent();
                // 指定开启系统相机的Action
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                // 根据文件地址创建文件
                File file = new File(FILE_PATH);
                if (file.exists()) {
                    file.delete();
                }
                // 把文件地址转换成Uri格式
                Uri uri = Uri.fromFile(file);
                // 设置系统相机拍摄照片完成后图片文件的存放地址
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "系统相机拍照完成，resultCode=" + resultCode);

        if (requestCode == 0) {
            File file = new File(FILE_PATH);
            Uri uri = Uri.fromFile(file);
            ivCamera.setImageURI(uri);
        } else if (requestCode == 1) {
            Log.i(TAG, "默认content地址："+data.getData());
            ivCamera.setImageURI(data.getData());
        }
    }
}
