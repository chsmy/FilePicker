package com.chs.filepicker.filepicker;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

/**
 * 作者：chs
 * 时间：2018-11-06 12:47
 * 描述：
 */
public class BaseFragment extends Fragment {
    public static final int REQUEST_PERMISSION_STORAGE = 0x01;

    public boolean checkPermission(@NonNull String permission) {
        return ActivityCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }
}
