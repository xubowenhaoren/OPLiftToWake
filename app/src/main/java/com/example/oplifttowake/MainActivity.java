package com.example.oplifttowake;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "OPLiftMainActicity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");
        Utils.checkDozeService(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "OnResume");
//        Utils.checkDozeService(this);

    }
}
