package com.chs.filepicker.filepicker.util;

import com.chs.filepicker.filepicker.PickerManager;
import com.chs.filepicker.filepicker.model.FileEntity;

import java.io.File;
import java.io.FileFilter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileUtils {
    /**
     * 根据路径获取file的集合
     *
     * @param path
     * @param filter
     * @return
     */
    public static List<FileEntity> getFileListByDirPath(String path, FileFilter filter) {
        File directory = new File(path);
        File[] files = directory.listFiles(filter);

        if (files == null) {
            return new ArrayList<>();
        }

        List<File> result = Arrays.asList(files);
        Collections.sort(result, new FileComparator());

        List<FileEntity> entities = new ArrayList<>();
        for (File f : result) {
            FileEntity e;
            if (PickerManager.getInstance().files.contains(f.getAbsolutePath())) {
                e = new FileEntity(f, true);
            } else {
                e = new FileEntity(f, false);
            }
            entities.add(e);
        }
        return entities;
    }


    public static String getReadableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#.##").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
