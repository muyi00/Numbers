package com.muyi.numbers.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by YJ on 2018/1/19.
 */

public class FileUtil {
    /**
     * 读取assets下的txt文件，返回utf-8 String
     *
     * @param context
     * @param fileName 不包括后缀
     * @return
     */
    public static String readAssetsTxt(Context context, String fileName) {
        try {
            //Return an AssetManager instance for your application's package
            InputStream is = context.getAssets().open(fileName + ".txt");
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            String text = new String(buffer, "utf-8");
            // Finally stick the string into the text view.
            return text;
        } catch (IOException e) {
            // Should never happen!
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return "读取错误，请检查文件名";
    }

    /**
     * 将Assets中文件复制到指定位置
     *
     * @param context
     * @param assetsPath 文件夹或者文件名
     * @param savePath   新的文件绝对路径
     */
    public static void copyFilesFromAssets(Context context, String assetsPath, String savePath) {
        try {
            String fileNames[] = context.getAssets().list(assetsPath);// 获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {// 如果是目录
                File file = new File(savePath);
                file.mkdirs();// 如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    copyFilesFromAssets(context, assetsPath + "/" + fileName,
                            savePath + "/" + fileName);
                }
            } else {// 如果是文件
                InputStream is = context.getAssets().open(assetsPath);
                FileOutputStream fos = new FileOutputStream(new File(savePath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                    // buffer字节
                    fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
                }
                fos.flush();// 刷新缓冲区
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void writeBytesToFile(InputStream is, File file) throws IOException {
        FileOutputStream fos = null;
        try {
            byte[] data = new byte[2048];
            int nbread = 0;
            fos = new FileOutputStream(file);
            while ((nbread = is.read(data)) > -1) {
                fos.write(data, 0, nbread);
            }
        } catch (Exception ex) {
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }


    /**
     * 获取App缓存路径
     *
     * @param context
     * @return
     */
    public static String getDiskCacheDir(Context context) {
        String cachePath = null;
        File file = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            file = context.getExternalCacheDir();
        } else {
            file = context.getCacheDir();
        }
        if (file != null) {
            cachePath = file.getPath();
        } else {
            cachePath = getSDPath(context);
        }
        existOrNewDir(cachePath);
        return cachePath;
    }

    public static String getSDPath(Context context) {
        if (isExternalStorageAvailable()) {
            StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            try {
                String[] paths = (String[]) sm.getClass().getMethod("getVolumePaths", new Class[0]).invoke(sm, new Object[]{});
                long maxblocks = 0;
                String lastpath = "";
                for (String tempPath : paths) {
                    long tempblocks = getAvailableInternalMemorySize(tempPath);
                    if (tempblocks > maxblocks) {
                        maxblocks = tempblocks;
                        lastpath = tempPath;
                    }
                }

                /****
                 * 外置sd卡存在，可以获取路径、文件读写权限， 但是由于Android版本差异和各系统厂商的定制，
                 * 外置sd卡可能出现无法创建文件夹的情况, 且创建不成功也不会出现异常，需要验证.
                 */
                File lastDir = new File(lastpath + "/DLH/");
                if (lastDir.exists() == false) {
                    lastDir.mkdirs();
                    if (lastDir.exists() == false) {// 验证是否能创建文件夹
                        lastpath = Environment.getExternalStorageDirectory().toString();
                    }
                }

                return lastpath;

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        }
        return Environment.getExternalStorageDirectory().toString();
    }


    public static void existOrNewDir(String dir) {
        File file = new File(dir);
        if (file.exists() == false) {
            file.mkdirs();
        }

    }


    public static boolean isExternalStorageAvailable() {
        boolean state = false;
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            state = true;
        }
        return state;
    }

    public static long getAvailableInternalMemorySize(String path) {

        StatFs stat = new StatFs(path);
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }
}
