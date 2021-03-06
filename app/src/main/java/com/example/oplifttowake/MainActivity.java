package com.example.oplifttowake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "OPLiftMainActicity";
    private Switch power;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");
//        Utils.checkDozeService(this);
        power = findViewById(R.id.lift_switch);
        power.setOnCheckedChangeListener(this);
        preferences = getPreferences(MODE_PRIVATE);
        boolean tgpref = preferences.getBoolean("tgpref", false);  //default is false
        power.setChecked(tgpref);
    }

    @Override
    public void onResume(){
        super.onResume();
//        https://stackoverflow.com/questions/32627342/how-to-whitelist-app-in-doze-mode-android-6-0
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivityForResult(intent, 1000);
            }
        }
//        Log.d(TAG, "OnResume");
//        Utils.checkDozeService(this);

    }

//    https://www.twle.cn/l/yufei/android/android-basic-switch.html
//    https://stackoverflow.com/questions/7383752/how-to-save-the-state-of-the-tooglebutton-on-off-selection
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        boolean isButtonOn = compoundButton.isChecked();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("tgpref", isButtonOn); // value to store
        editor.apply();

        String state = isButtonOn ? "ON" :"OFF";
        if (isButtonOn) {
            Utils.startService(this);
        } else {
            Utils.stopService(this);
        }
        Toast.makeText(this, this.getString(R.string.switch_lift) + " "+ state,Toast.LENGTH_SHORT).show();

    }
}
