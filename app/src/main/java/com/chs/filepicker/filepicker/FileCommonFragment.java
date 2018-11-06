package com.chs.filepicker.filepicker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chs.filepicker.R;
import com.chs.filepicker.filepicker.adapter.CommonFileAdapter;
import com.chs.filepicker.filepicker.adapter.OnFileItemClickListener;
import com.chs.filepicker.filepicker.model.FileEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：chs on 2017-08-24 11:04
 * 邮箱：657083984@qq.com
 * 常用文件
 */

public class FileCommonFragment extends BaseFragment implements FileScannerTask.FileScannerListener {
    private RecyclerView mRecyclerView;
    private TextView mEmptyView;
    private ProgressBar mProgressBar;
    private CommonFileAdapter mCommonFileAdapter;
    private OnUpdateDataListener mOnUpdateDataListener;

    public void setOnUpdateDataListener(OnUpdateDataListener onUpdateDataListener) {
        mOnUpdateDataListener = onUpdateDataListener;
    }

    public static FileCommonFragment newInstance(){
        return new FileCommonFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_normal,null);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rl_normal_file);
        mRecyclerView.setLayoutManager(layoutManager);
        mEmptyView = (TextView) view.findViewById(R.id.empty_view);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void initData() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new FileScannerTask(getContext(), this).execute();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_STORAGE);
            }
        }else{
            new FileScannerTask(getContext(), this).execute();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new FileScannerTask(getContext(), this).execute();
            } else {
                Toast.makeText(getContext(),"权限被禁止，无法选择本地图片",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void iniEvent(final List<FileEntity> entities) {
        mCommonFileAdapter.setOnItemClickListener(new OnFileItemClickListener() {
            @Override
            public void click(int position) {
                FileEntity entity = entities.get(position);
                String absolutePath = entity.getPath();
                ArrayList<FileEntity> files = PickerManager.getInstance().files;
                if(files.contains(entity)){
                    files.remove(entity);
                    if(mOnUpdateDataListener!=null){
                        mOnUpdateDataListener.update(-Long.parseLong(entity.getSize()));
                    }
                    entity.setSelected(!entity.isSelected());
                    mCommonFileAdapter.notifyDataSetChanged();
                }else {
                    if(PickerManager.getInstance().files.size()<PickerManager.getInstance().maxCount){
                        files.add(entity);
                        if(mOnUpdateDataListener!=null){
                            mOnUpdateDataListener.update(Long.parseLong(entity.getSize()));
                        }
                        entity.setSelected(!entity.isSelected());
                        mCommonFileAdapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(getContext(),getString(R.string.file_select_max,PickerManager.getInstance().maxCount),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void scannerResult(List<FileEntity> entities) {
        mProgressBar.setVisibility(View.GONE);
        if(entities.size()>0){
            mEmptyView.setVisibility(View.GONE);
        }else {
            mEmptyView.setVisibility(View.VISIBLE);
        }
        mCommonFileAdapter = new CommonFileAdapter(getContext(),entities);
        mRecyclerView.setAdapter(mCommonFileAdapter);
        iniEvent(entities);
    }
}
