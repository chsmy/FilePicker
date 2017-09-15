package com.chs.filepicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chs.filepicker.filepicker.FilePickerActivity;
import com.chs.filepicker.filepicker.PickerManager;
import com.chs.filepicker.filepicker.adapter.FilePickerShowAdapter;
import com.chs.filepicker.filepicker.adapter.OnFileItemClickListener;
import com.chs.filepicker.filepicker.util.OpenFile;

public class MainActivity extends AppCompatActivity {
    private static int REQ_CODE = 0X01;
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rl_file);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
    }

    public void goFilePicker(View view) {
        Intent intent = new Intent(this, FilePickerActivity.class);
        startActivityForResult(intent,REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE){
            FilePickerShowAdapter adapter = new FilePickerShowAdapter(this,PickerManager.getInstance().files);
            mRecyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new OnFileItemClickListener() {
                @Override
                public void click(int position) {
                    startActivity(Intent.createChooser(OpenFile.openFile(PickerManager.getInstance().files.get(position).getPath(), getApplicationContext()), "选择程序"));
                }
            });
        }
    }
}
