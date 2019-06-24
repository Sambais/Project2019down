package com.hnkjrjxy.project2019down.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hnkjrjxy.project2019down.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SendPostActivity extends Activity {
    private ImageView post_back;
    private TextView post_send;
    private EditText add_text;
    private ImageView addimage;
    private static final int CROP_PHOTO = 2;
    private static final int PICK_PIC = 3;

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE1 = 4;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 5;
    private File output;
    private Uri imageUri;
    /**
     * 当前选择的图片的路径
     */
    public String mImagePath;
    /**
     * 选择图片的返回码
     */
    public final static int SELECT_IMAGE_RESULT_CODE = 200;

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
                choosePhoto();
            }
        });
    }


    private void takePhoto() {
        // 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {
            /**
             * 通过指定图片存储路径，解决部分机型onActivityResult回调 data返回为null的情况
             */
            //获取与应用相关联的路径
            String imageFilePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
            //根据当前时间生成图片的名称
            String timestamp = "/"+formatter.format(new Date())+".jpg";
            File imageFile = new File(imageFilePath,timestamp);// 通过路径创建保存文件
            mImagePath = imageFile.getAbsolutePath();
            Uri imageFileUri = Uri.fromFile(imageFile);// 获取文件的Uri
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageFileUri);// 告诉相机拍摄完毕输出图片到指定的Uri
            startActivityForResult(intent, SELECT_IMAGE_RESULT_CODE);
        } else {
            Toast.makeText(this, "内存卡不存在！", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * 从相册选取图片
     */
    private void choosePhoto() {
        /**
         * 打开选择图片的界面
         */
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");// 相片类型
        startActivityForResult(intent, PICK_PIC);

    }

    /**
     * 调用相册和拍照之后的返回
     */
    public void onActivityResult(int req, int res, Intent data) {
        switch (req) {
            /**
             * 拍照的请求标志
             */
            case CROP_PHOTO:
                if (res == RESULT_OK) {
                    try {
                        /**
                         * 该uri就是照片文件夹对应的uri
                         */
                        Bitmap bit = BitmapFactory
                                .decodeStream(getContentResolver().openInputStream(
                                        imageUri));
                        addimage.setImageBitmap(bit);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;
            /**
             * 从相册中选取图片的请求标志
             */

            case PICK_PIC:
                if (res == RESULT_OK) {
                    try {
                        /**
                         * 该uri是上一个Activity返回的
                         */
                        Uri uri = data.getData();
                        Bitmap bit = BitmapFactory
                                .decodeStream(getContentResolver().openInputStream(
                                        uri));
                        addimage.setImageBitmap(bit);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            default:
                break;
        }
    }

    /**
     * 动态权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            } else {
                // Permission Denied
                Toast.makeText(SendPostActivity.this, "Permission Denied",
                        Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                // Permission Denied
                Toast.makeText(SendPostActivity.this, "Permission Denied",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
