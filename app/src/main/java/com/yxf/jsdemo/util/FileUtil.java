package com.yxf.jsdemo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.os.Environment.DIRECTORY_MUSIC;

/**
 * @Description:
 * @Author: yxf
 * @CreateDate: 2021/5/31 17:40
 * @UpdateUser: yxf
 * @UpdateDate: 2021/5/31 17:40
 */
public class FileUtil {
    public static byte[] fileToByte(String path) {
        //FileInputStream
        try {
            FileInputStream fis = new FileInputStream(path);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b, 0, b.length)) != -1) {
                bos.write(b, 0, length);
            }
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void testFilePath(Context context) {
        //存储路径和app包名相关的都需要使用context来调用
        File externalCacheDir = context.getExternalCacheDir();
        File[] externalCacheDirs = context.getExternalCacheDirs();
        File externalFilesDir = context.getExternalFilesDir(DIRECTORY_MUSIC);
        Logger.w("externalCacheDir:%s", externalCacheDir.getAbsolutePath());
        Logger.w("externalFilesDir:%s", externalFilesDir.getAbsolutePath());

        File filesDir = context.getFilesDir();//data/data目录，和sharePreference同级  手机的internalStorage
        File cacheDir = context.getCacheDir();//data/data目录，和sharePreference同级  手机的internalStorage

        Logger.w("filesDir:%s", filesDir.getAbsolutePath());
        Logger.w("cacheDir:%s", cacheDir.getAbsolutePath());
        File fileDir = new File(filesDir, "/test");
        fileDir.mkdirs();


        File externalStorageDirectory = Environment.getExternalStorageDirectory();//手机内存的根目录（externalStorage）
        Logger.w("externalStorageDirectory:%s", externalStorageDirectory.getAbsolutePath());

        //test sharePreference目录肯定和application相关
        SharedPreferences sp_db = context.getSharedPreferences("sp_db",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp_db.edit();
        edit.putString("test","ceshi");
        edit.commit();

        File fileTestCache = new File(externalFilesDir, "/test.txt");
        FileOutputStream fos = null;
        try {
            fos=new FileOutputStream(fileTestCache);
            String str="test";
            fos.write(str.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
