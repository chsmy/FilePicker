package com.chs.filepicker.filepicker.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：chs on 2017-08-24 15:19
 * 邮箱：657083984@qq.com
 */

public class FileType implements Parcelable{
    private String title;
    private int iconStyle;
    public String[] filterType;

    public FileType(String title,String[] filterType,int iconStyle) {
        this.title = title;
        this.iconStyle = iconStyle;
        this.filterType = filterType;
    }

    protected FileType(Parcel in) {
        title = in.readString();
        iconStyle = in.readInt();
        filterType = in.createStringArray();
    }

    public static final Creator<FileType> CREATOR = new Creator<FileType>() {
        @Override
        public FileType createFromParcel(Parcel in) {
            return new FileType(in);
        }

        @Override
        public FileType[] newArray(int size) {
            return new FileType[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public int getIconStyle() {
        return iconStyle;
    }

    public String[] getFilterType() {
        return filterType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(iconStyle);
        dest.writeStringArray(filterType);
    }
}
