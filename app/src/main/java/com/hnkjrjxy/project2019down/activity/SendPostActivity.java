package com.hnkjrjxy.project2019down.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hnkjrjxy.project2019down.R;

import java.io.File;

public class SendPostActivity extends Activity {
    private ImageView post_back;
    private TextView post_send;
    private EditText add_text;
    private ImageView addimage;
    // 拍照回传码
    public final static int CAMERA_REQUEST_CODE = 0;
    // 相册选择回传吗
    public final static int GALLERY_REQUEST_CODE = 1;

    private static final int PHOTO_REQUEST_CAREMA = 1;
    private static final int PHOTO_REQUEST_GALLERY = 2;
    private static final int PHOTO_REQUEST_CUT = 3;
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newpost);
        initView();
    }

    private void initView() {
        post_back = (ImageView) findViewById(R.id.post_back);
        post_send = (TextView) findViewById(R.id.post_send);
        add_text = (EditText) findViewById(R.id.add_text);
        addimage = (ImageView) findViewById(R.id.addimage);

        post_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT>22){
                    if (ContextCompat.checkSelfPermission(SendPostActivity.this,
                            android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                        //先判断有没有权限 ，没有就在这里进行权限的申请
                        ActivityCompat.requestPermissions(SendPostActivity.this,
                                new String[]{android.Manifest.permission.CAMERA},1);

                    }else {
                        //说明已经获取到摄像头权限了 想干嘛干嘛
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,1);//启动相机程序
                    }
                }else {
//这个说明系统版本在6.0之下，不需要动态获取权限。

                }

//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");//相片类型
//                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch(requestCode){
            case 1:
                Bitmap photo = data.getParcelableExtra("data");
                addimage.setImageBitmap(photo);


//                Uri selectedImage = data.getData();
//                String[] filePathColumn = { MediaStore.Images.Media.DATA };
//                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                cursor.moveToFirst();
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                String picturePath = cursor.getString(columnIndex);
//                cursor.close();
//                Bitmap bm = BitmapFactory.decodeFile(picturePath);
//                addimage.setImageBitmap(bm);
                break;
        }
    }
}
