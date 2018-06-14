package cn.carbs.a2048.common;

import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by carbs on 2017/12/5.
 */

public class FileUtils {

    public static void copyFile(File sourceFile, File targetFile) {
        try {
            // 新建文件输入流并对它进行缓冲
            FileInputStream input = new FileInputStream(sourceFile);
            BufferedInputStream inBuff = new BufferedInputStream(input);

            // 新建文件输出流并对它进行缓冲
            FileOutputStream output = new FileOutputStream(targetFile);
            BufferedOutputStream outBuff = new BufferedOutputStream(output);

            // 缓冲数组
            byte[] b = new byte[1024 * 8];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();

            //关闭流
            inBuff.close();
            outBuff.close();
            output.close();
            input.close();
        }catch (Exception e){

        }
    }

    public static String getFileSuffixName(String absoluteFileName){
        String[] a = absoluteFileName.split(File.separator);
        if(a == null){
            return "";
        }
        String fileName = a[a.length - 1];
        int index = fileName.lastIndexOf(".");
        if(index  < 0){
            return "";
        }
        String ret = fileName.substring(index);
        if(ret != null && ret.length() > 0){
            ret = ret.substring(1);
        }
        return ret;
    }

    //获取文件夹大小
    public static long getFolderSize(File file){

        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++)
            {
                if (fileList[i].isDirectory())
                {
                    size = size + getFolderSize(fileList[i]);

                }else{
                    size = size + fileList[i].length();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    //删除指定目录下文件及目录
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //格式化单位
    public static String getFormatSize(double size) {
        double kiloByte = size/1024;
        if(kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte/1024;
        if(megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte/1024;
        if(gigaByte < 1) {
            BigDecimal result2  = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte/1024;
        if(teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }


    private String getImagePathSuffix(String fileName){
        if (fileName == null || fileName.trim().length() == 0){
            return "";
        }
        return FileUtils.getFileSuffixName(fileName);
    }

    private String getLubanCompressedPath(String suffix){
        Date date = new Date();
        return "image_" + DateUtils.dateTime2String(date, DateUtils.YYYY_MM_DD_HH_MM_SS_S_file_name)
                + suffix;
    }

    public static String inputStreamToString(InputStream inputStream){
        if (inputStream == null){
            return null;
        }
        String ret = null;
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            ret = sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }
}