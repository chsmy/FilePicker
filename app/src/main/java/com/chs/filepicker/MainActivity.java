package com.chs.filepicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.chs.filepicker.filepicker.FilePickerActivity;
import com.chs.filepicker.filepicker.PickerManager;
import com.chs.filepicker.filepicker.model.FileEntity;

public class MainActivity extends AppCompatActivity {
    private static int REQ_CODE = 0X01;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goFilePicker(View view) {
        Intent intent = new Intent(this, FilePickerActivity.class);
        startActivityForResult(intent,REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE){
            for (FileEntity e :
                    PickerManager.getInstance().files) {

                Log.i("res", e.getPath());
            }
        }
    }
}
