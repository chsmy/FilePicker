package com.chs.filepicker.filepicker.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

/**
 * 作者：chs on 2017-08-24 14:41
 * 邮箱：657083984@qq.com
 */

public class FileEntity implements Parcelable {
    private int id;
    private String name;
    private String path;
    private String mimeType;
    private String size;
    private String date;
    private FileType fileType;
    private boolean isSelected;
    /* File 用于全部文件*/
    private File mFile;

    public FileEntity(int id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public FileEntity(File file, boolean isSelected) {
        mFile = file;
        this.isSelected = isSelected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileEntity)) return false;

        FileEntity document = (FileEntity) o;

        return id == document.id;
    }
    protected FileEntity(Parcel in) {
        id = in.readInt();
        name = in.readString();
        path = in.readString();
        mimeType = in.readString();
        size = in.readString();
        date = in.readString();
    }

    public static final Creator<FileEntity> CREATOR = new Creator<FileEntity>() {
        @Override
        public FileEntity createFromParcel(Parcel in) {
            return new FileEntity(in);
        }

        @Override
        public FileEntity[] newArray(int size) {
            return new FileEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(path);
        dest.writeString(mimeType);
        dest.writeString(size);
        dest.writeString(date);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public File getFile() {
        return mFile;
    }

    public void setFile(File file) {
        mFile = file;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
