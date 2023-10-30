package com.sics.sxt.utils;

import java.io.File;

public class FileUtil {

    public static void deleteFile(String filePath)
    {
        File file = new File(filePath);
        if (!file.exists()){
            boolean mkdir = file.mkdir();
        }else {
            if (file.isFile()) {
                boolean delete = file.delete();
            } else {
                String[] childFilePath = file.list();
                if (childFilePath != null) {
                    for (String path : childFilePath) {
                        deleteFile(file.getAbsoluteFile() + "/" + path);
                    }
                }
                //file.delete(); //保留文件夹
            }
        }

    }
}
