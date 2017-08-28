package com.chs.filepicker.filepicker;

import com.chs.filepicker.R;
import com.chs.filepicker.filepicker.model.FileType;

import java.util.ArrayList;

/**
 * 作者：chs on 2017-08-24 14:34
 * 邮箱：657083984@qq.com
 */

public class PickerManager {
    public static PickerManager getInstance() {
        return SingletonHolder.INSTANCE;
    }
    private static class SingletonHolder{
        private static final PickerManager INSTANCE = new PickerManager();
    }
    /**
     * 最多能选的文件的个数
     */
    public int maxCount = 3;
    /**
     * 保存结果
     */
    public ArrayList<String> files;
    /**
     * 筛选条件 类型
     */
    public ArrayList<FileType> mFileTypes;
    /**
     * 文件夹筛选
     * 这里包括 微信和QQ中的下载的文件和图片
     */
    public String[] mFilterFolder = new String[]{"MicroMsg/Download","WeiXin","QQfile_recv","MobileQQ/photo"};
    private PickerManager() {
        files = new ArrayList<>();
        mFileTypes = new ArrayList<>();
        addDocTypes();
    }
    public void addDocTypes()
    {
        String[] pdfs = {"pdf"};
        mFileTypes.add(new FileType("PDF",pdfs, R.mipmap.file_picker_pdf));

        String[] docs = {"doc","docx", "dot","dotx"};
        mFileTypes.add(new FileType("DOC",docs,R.mipmap.file_picker_word));

        String[] ppts = {"ppt","pptx"};
        mFileTypes.add(new FileType("PPT",ppts,R.mipmap.file_picker_ppt));

        String[] xlss = {"xls","xlt","xlsx","xltx"};
        mFileTypes.add(new FileType("XLS",xlss,R.mipmap.file_picker_excle));

        String[] txts = {"txt"};
        mFileTypes.add(new FileType("TXT",txts,R.mipmap.file_picker_txt));

        String[] imgs = {"png","jpg","jpeg","gif"};
        mFileTypes.add(new FileType("IMG",imgs,0));
    }

    public ArrayList<FileType> getFileTypes() {
        return mFileTypes;
    }


    public PickerManager setMaxCount(int maxCount) {
        this.maxCount = maxCount;
        return this;
    }
}
