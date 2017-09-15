package com.chs.filepicker.filepicker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chs.filepicker.R;
import com.chs.filepicker.filepicker.model.FileEntity;

import java.io.File;
import java.util.ArrayList;

/**
 * 作者：chs on 2017-08-28 10:54
 * 邮箱：657083984@qq.com
 * 选择附件后显示
 */

public class FilePickerShowAdapter extends RecyclerView.Adapter<FilePickerShowAdapter.FileShowViewHolder> {
    private Context mContext;
    private ArrayList<FileEntity> mDataList;
    private OnDeleteListener mOnDeleteListener;
    private OnFileItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnFileItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        mOnDeleteListener = onDeleteListener;
    }

    public FilePickerShowAdapter(Context context, ArrayList<FileEntity> dataList) {
        mContext = context;
        mDataList = dataList;
    }


    @Override
    public FileShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_file_picker_show, parent, false);
        return new FileShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FileShowViewHolder holder, int position) {
        final FileEntity fileEntity = mDataList.get(position);
        holder.mTvName.setText(fileEntity.getName());
        holder.mTvDetail.setText(fileEntity.getSize());
        if (fileEntity.getFileType() != null) {
            String title = fileEntity.getFileType().getTitle();
            if (title.equals("IMG")) {
                Glide.with(mContext).load(new File(fileEntity.getPath())).into(holder.mIvType);
            } else {
                holder.mIvType.setImageResource(fileEntity.getFileType().getIconStyle());
            }
        } else {
            holder.mIvType.setImageResource(R.mipmap.file_picker_def);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.click(holder.getAdapterPosition());
                }
            }
        });
        holder.mIvDelete.setVisibility(View.VISIBLE);
        holder.mIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataList.remove(holder.getAdapterPosition());
                if (mOnDeleteListener != null)
                    mOnDeleteListener.delete(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class FileShowViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvType, mIvDelete;
        private TextView mTvName, mTvDetail;

        public FileShowViewHolder(View itemView) {
            super(itemView);
            mIvType = (ImageView) itemView.findViewById(R.id.iv_type);
            mIvDelete = (ImageView) itemView.findViewById(R.id.iv_delete);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
            mTvDetail = (TextView) itemView.findViewById(R.id.tv_detail);
        }
    }
}
