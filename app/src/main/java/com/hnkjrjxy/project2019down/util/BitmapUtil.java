package com.hnkjrjxy.project2019down.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class BitmapUtil {
    //将bitmap类型转换为base64字符串
    public static String bitmapToBase64(String url) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(url);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(fis);
        String result = null; ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush(); baos.close(); byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush(); baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 将图片转换成Base64编码的字符串
     */
    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

        public static String getRealPath(Uri fileUrl , Context context) {
        String fileName = null;
        if( fileUrl != null ) {
            if( fileUrl.getScheme( ).toString( ).compareTo( "content" ) == 0 ) // content://开头的uri
            {
                Cursor cursor = context.getContentResolver( ).query( fileUrl, null, null, null, null );
                if( cursor != null && cursor.moveToFirst( ) ) {
                    try {
                        int column_index = cursor.getColumnIndexOrThrow( MediaStore.Images.Media.DATA );
                        fileName = cursor.getString( column_index ); // 取出文件路径
                    } catch( IllegalArgumentException e ) {
                        e.printStackTrace();
                    }finally{
                        cursor.close( );
                    }
                }
            } else if( fileUrl.getScheme( ).compareTo( "file" ) == 0 ) // file:///开头的uri
            {
                fileName = fileUrl.getPath( );
            }
        }
        return fileName;
    }

}
