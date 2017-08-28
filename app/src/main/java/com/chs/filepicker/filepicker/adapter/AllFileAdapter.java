package com.chs.filepicker.filepicker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.chs.filepicker.R;
import com.chs.filepicker.filepicker.model.FileEntity;
import com.chs.filepicker.filepicker.util.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

/**
 * 作者：chs on 2017-08-08 14:40
 * 邮箱：657083984@qq.com
 */

public class AllFileAdapter extends RecyclerView.Adapter<FilePickerViewHolder> {
    private List<FileEntity> mListData;
    private Context mContext;
    private FileFilter mFileFilter;
    private OnFileItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnFileItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AllFileAdapter(Context context, List<FileEntity> listData, FileFilter fileFilter) {
        mListData = listData;
        mContext = context;
        mFileFilter = fileFilter;
    }

    @Override
    public FilePickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_file_picker, parent, false);
        return new FilePickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FilePickerViewHolder holder, int positon) {
        final FileEntity entity = mListData.get(positon);
        final File file = entity.getFile();
        holder.tvName.setText(file.getName());
        if (file.isDirectory()) {
            holder.ivType.setImageResource(R.mipmap.file_picker_folder);
            holder.ivChoose.setVisibility(View.GONE);
        } else {
            if(entity.getFileType()!=null){
                String title = entity.getFileType().getTitle();
                if (title.equals("IMG")) {
                    Glide.with(mContext).load(new File(entity.getPath())).into(holder.ivType);
                } else {
                    holder.ivType.setImageResource(entity.getFileType().getIconStyle());
                }
            }else {
                holder.ivType.setImageResource(R.mipmap.file_picker_def);
            }
            holder.ivChoose.setVisibility(View.VISIBLE);
            holder.tvDetail.setText(FileUtils.getReadableFileSize(file.length()));

            if (entity.isSelected()) {
                holder.ivChoose.setImageResource(R.mipmap.file_choice);
            } else {
                holder.ivChoose.setImageResource(R.mipmap.file_no_selection);
            }
        }
        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.click(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    /**
     * 更新数据源
     *
     * @param mListData
     */
    public void updateListData(List<FileEntity> mListData) {
        this.mListData = mListData;
    }
}
