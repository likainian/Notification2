package com.feicui.notificationdemo;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * 内存信息获取
 */
public class MemoryManager {
    /**
     * 获取手机内置sdcard路径, 为null表示无
     */
    public static String getPhoneInSDCardPath(){
        String  sdcardState= Environment.getExternalStorageState();
        if (!sdcardState.equals(Environment.MEDIA_MOUNTED)){
            return null;
        }
        return Environment.getExternalStorageDirectory()
                .getAbsolutePath();
    }
    /**
     * 获取手机外置sdcard路径, 为null表示无
     */
    public static String getPhoneOutSDCardPath(){
        Map<String,String>map=System.getenv();
        if (map.containsKey("SECONDARY_STORAGE")){
            String paths=map.get("SECONDARY_STORAGE");
            // /storage/extSdCard
            String path[]=paths.split(":");
            if (path.length<=0){
                return null;
            }
            return path[0];
        }
        return null;
    }
    /**
     * 设备外置存储SDCard全部大小 单位B , 当没有外置卡时,大小为0
     */
    public static long getPhoneOutSDCardSize(Context context){
        try {
            File path=new File(getPhoneOutSDCardPath());
            if (path==null){
                return 0;
            }
            StatFs stat=new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long blockCount = stat.getBlockCount();
            return blockCount * blockSize;
        }catch (Exception e){
            Toast.makeText(context, "外置存储卡异常", Toast.LENGTH_SHORT).show();
        return  0;
        }
    }
    /**
     * 设备外置存储SDCard空闲大小 单位B
     */
    public static long getPhoneOutSDCardFreeSize(Context context){
        try {
            File path=new File(getPhoneOutSDCardPath());
            if (path==null){
                return 0;
            }
            StatFs stat=new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availaBlock = stat.getAvailableBlocks();
            return availaBlock * blockSize;
        }catch (Exception e){
            Toast.makeText(context, "外置存储卡异常", Toast.LENGTH_SHORT).show();
       return 0;
        }
    }
    /**
     * 设备自身存储全部大小 单位B
     */
    public static long getPhoneSelfSize() {
        File path = Environment.getRootDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long blockCount = stat.getBlockCountLong();
        long rootFileSize = blockCount * blockSize;

        path = Environment.getDownloadCacheDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSizeLong();
        blockCount = stat.getBlockCountLong();
        long cacheFileSize = blockCount * blockSize;

        return rootFileSize + cacheFileSize;
    }
    /**
     * 设备自身存储空闲大小 单位B
     */
    public static long getPhoneSelfFreeSize() {

        File path = Environment.getRootDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long blockCount = stat.getAvailableBlocksLong();
        long rootFileSize = blockCount * blockSize;

        path = Environment.getDownloadCacheDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSizeLong();
        blockCount = stat.getAvailableBlocksLong();
        long cacheFileSize = blockCount * blockSize;

        return rootFileSize + cacheFileSize;
    }
    /**
     * 获取到的是手机自带的储存空间 单位是byte
     */
    public static long getPhoneSelfSDCardSize() {
        String sdcardState = Environment.getExternalStorageState();
        if (!sdcardState.equals(Environment.MEDIA_MOUNTED)) {
            return 0;
        }
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long blockCount = stat.getBlockCountLong();
        return blockCount * blockSize;
    }
    /**
     * 获取到的是手机自带的储存空间的剩余空间  单位byte
     */
    public static long getPhoneSelfSDCardFreeSize() {
        String sdcardState = Environment.getExternalStorageState();
        if (!sdcardState.equals(Environment.MEDIA_MOUNTED)) {
            return 0;
        }
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availaBlock = stat.getAvailableBlocksLong();
        return availaBlock * blockSize;
    }

    /**
     * 获取手机总存储大小
     */
    public static long getPhoneAllSize() {
        File path = Environment.getRootDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long blockCount = stat.getBlockCountLong();
        long rootFileSize = blockCount * blockSize;

        path = Environment.getDataDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSizeLong();
        blockCount = stat.getBlockCountLong();
        long dataFileSize = blockCount * blockSize;

        path = Environment.getDownloadCacheDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSizeLong();
        blockCount = stat.getBlockCountLong();
        long cacheFileSize = blockCount * blockSize;

        return rootFileSize + dataFileSize + cacheFileSize;
    }

    /**
     * 获取手机总闲置存储大小
     */
    public static long getPhoneAllFreeSize() {
        File path = Environment.getRootDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long blockCount = stat.getAvailableBlocks();
        long rootFileSize = blockCount * blockSize;

        path = Environment.getDataDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSize();
        blockCount = stat.getAvailableBlocks();
        long dataFileSize = blockCount * blockSize;

        path = Environment.getDownloadCacheDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSize();
        blockCount = stat.getAvailableBlocks();
        long cacheFileSize = blockCount * blockSize;

        return rootFileSize + dataFileSize + cacheFileSize;
    }

    /**
     * 设备空闲运行内存 单位B
     */
    public static long getPhoneFreeRamMemory(Context context) {
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        am.getMemoryInfo(info);
        return info.availMem;
    }

    /**
     * 设备完整运行内存 单位B
     */
    public static long getPhoneTotalRamMemory() {
        try {
            FileReader fr = new FileReader("/proc/meminfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split("\\s+");
            return Long.valueOf(array[1]) * 1024; // 原为kb, 转为b
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
