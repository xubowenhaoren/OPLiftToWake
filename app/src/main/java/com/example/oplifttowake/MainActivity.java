package com.example.oplifttowake;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "OPLiftMainActicity";
    private Switch power;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");
//        Utils.checkDozeService(this);
        power = findViewById(R.id.lift_switch);

        power.setOnCheckedChangeListener(this);
    }

    @Override
    public void onResume(){
        super.onResume();
//        Log.d(TAG, "OnResume");
//        Utils.checkDozeService(this);

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        boolean isButtonOn = compoundButton.isChecked();
        String state = isButtonOn ? "ON" :"OFF";
        if (isButtonOn) {
            Utils.startService(this);
        } else {
            Utils.stopService(this);
        }
        Toast.makeText(this,"Lift to Wake: "+state,Toast.LENGTH_SHORT).show();

    }
}
