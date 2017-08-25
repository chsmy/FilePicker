package com.chs.filepicker.filepicker;

import java.io.File;
import java.io.FileFilter;

/**
 * 作者：chs on 2017-08-08 14:30
 * 邮箱：657083984@qq.com
 * File的筛选
 */

public class FileSelectFilter implements FileFilter {
    private String[] mTypes;
    public FileSelectFilter(String[] types) {
        this.mTypes = types;
    }
    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        if (mTypes != null && mTypes.length > 0) {
            for (int i = 0; i < mTypes.length; i++) {
                if (file.getName().endsWith(mTypes[i].toLowerCase()) || file.getName().endsWith(mTypes[i].toUpperCase())) {
                    return true;
                }
            }
        }else {
            return true;
        }
        return false;
    }
}
