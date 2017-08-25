package com.chs.filepicker.filepicker;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.chs.filepicker.filepicker.model.FileEntity;
import com.chs.filepicker.filepicker.model.FileType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.MediaColumns.DATA;
/**
 * 作者：chs on 2017-08-24 14:39
 * 邮箱：657083984@qq.com
 * 扫描媒体库获取数据
 */

public class FileScannerTask extends AsyncTask<Void ,Void ,List<FileEntity>>{
    public interface FileScannerListener{
        void scannerResult(List<FileEntity> entities);
    }
    final String[] DOC_PROJECTION = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Files.FileColumns.TITLE
    };
    private Context context;
    private FileScannerListener mFileScannerListener;

    public FileScannerTask(Context context, FileScannerListener fileScannerListener) {
        this.context = context;
        mFileScannerListener = fileScannerListener;
    }
    //MediaStore.Files存储了所有应用中共享的文件，包括图片、文件、视频、音乐等多媒体文件，包括非多媒体文件。
    @Override
    protected List<FileEntity> doInBackground(Void... params) {
        List<FileEntity> fileEntities = new ArrayList<>();
        final String[] projection = DOC_PROJECTION;
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "!="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE + " AND "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "!="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO ;
        final Cursor cursor = context.getContentResolver().query(
                MediaStore.Files.getContentUri("external"),//数据源
                projection,//查询类型
                selection,//查询条件
                null,
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC");
        if(cursor!=null){
            fileEntities = getFiles(cursor);
            cursor.close();
        }
        return fileEntities;
    }

    @Override
    protected void onPostExecute(List<FileEntity> entities) {
        super.onPostExecute(entities);
        if(mFileScannerListener!=null){
            mFileScannerListener.scannerResult(entities);
        }
    }

    private List<FileEntity> getFiles(Cursor cursor) {
        List<FileEntity> fileEntities = new ArrayList<>();
        while (cursor.moveToNext()){
            int id  = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
            String path = cursor.getString(cursor.getColumnIndexOrThrow(DATA));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.TITLE));
            if(path!=null){
                FileType fileType = getFileType(PickerManager.getInstance().getFileTypes(),path);
                if(fileType!=null && !(new File(path).isDirectory())) {

                    FileEntity entity = new FileEntity(id, title, path);
                    entity.setFileType(fileType);

                    String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE));
                    if (mimeType != null && !TextUtils.isEmpty(mimeType))
                        entity.setMimeType(mimeType);
                    else {
                        entity.setMimeType("");
                    }

                    entity.setSize(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)));

                    if (!fileEntities.contains(entity))
                        fileEntities.add(entity);
                }
            }
        }
        return fileEntities;
    }

    private FileType getFileType(ArrayList<FileType> fileTypes, String path) {
        for (String str : PickerManager.getInstance().mFilterFolder) {//按文件夹筛选
            if (path.contains(str)) {
                for (int index = 0; index < fileTypes.size(); index++) {//按照文件类型筛选
                    for (String string : fileTypes.get(index).filterType) {
                        if (path.endsWith(string))
                            return fileTypes.get(index);
                    }
                }
            }
        }
        return null;
    }
}
