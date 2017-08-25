package com.chs.filepicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chs.filepicker.filepicker.FilePickerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goFilePicker(View view) {
        Intent intent = new Intent(this, FilePickerActivity.class);
        startActivity(intent);
    }
}
