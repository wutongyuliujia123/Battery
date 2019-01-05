package com.battery.bms;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.battery.battery.R;
import com.battery.bms.utils.DateUtils;
import com.battery.bms.utils.SharedPreferencesUtil;
import com.littlejie.circleprogress.DialProgress;
import com.littlejie.circleprogress.WaveProgress;

import java.math.BigDecimal;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnResetAll;
    private WaveProgress mWaveProgress;
    private DialProgress mDialProgress;
    private Switch mSwitch;
    private Random mRandom;
    private TextView tv_date;
    private TextView tv_current_mileage;
    private TextView tv_total_mileage;
    private LocationManager lm;
    private LocationListener locationListener;
    private float currentMileage;
    private float totalMileage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!isGpsAble(lm)) {
            Toast.makeText(MainActivity.this, "请打开GPS~", Toast.LENGTH_SHORT).show();
            openGPS2();
        }
        //  Location lc = SpeedUtil.getLastKnownLocation(MainActivity.this);

        mDialProgress = (DialProgress) findViewById(R.id.dial_progress_bar);
        mWaveProgress = (WaveProgress) findViewById(R.id.wave_progress_bar);
        tv_current_mileage = (TextView) findViewById(R.id.current_mileage);
        tv_total_mileage = (TextView) findViewById(R.id.total_mileage);
        mSwitch = (Switch) findViewById(R.id.switch_button);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_date.setText(DateUtils.getDate());
        mDialProgress.setOnClickListener(this);
        mWaveProgress.setOnClickListener(this);
        mSwitch.setOnClickListener(this);
        mRandom = new Random();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //            case R.id.btn_reset_all:
            //                mDialProgress.reset();
            //                break;
            case R.id.dial_progress_bar:
                mDialProgress.setValue(mRandom.nextFloat() * mWaveProgress.getMaxValue());
                break;
            case R.id.wave_progress_bar:
                mWaveProgress.setValue(mRandom.nextFloat() * mWaveProgress.getMaxValue());
                break;
            case R.id.switch_button:
                boolean isChecked = mSwitch.isChecked();
                if (isChecked) {
                    Toast.makeText(MainActivity.this, "开启", Toast.LENGTH_SHORT).show();
                    locationListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            float s = (location != null ? location.getSpeed() : 0);
                            double speed =  s * 3.6;
                            tv_date.setText(speed + "千米/h");
                            mDialProgress.setValue((float) speed);
                            currentMileage += (s * 2 /1000);
                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    };
                    if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        Toast.makeText(MainActivity.this, "请打开GPS~", Toast.LENGTH_SHORT).show();
                        openGPS2();
                        return;
                    }
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 8,
                        locationListener);
                } else {
                    Toast.makeText(MainActivity.this, "关闭", Toast.LENGTH_SHORT).show();
                    BigDecimal currentM = new BigDecimal(currentMileage);
                    tv_current_mileage.setText("本次里程："+currentM.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue()+" km");
                    String total = SharedPreferencesUtil.getSharedPreferencesValue(MainActivity.this,"totalMileage","0");
                    totalMileage = Float.valueOf(total) + currentMileage;
                    BigDecimal totalM = new BigDecimal(totalMileage);
                    tv_total_mileage.setText("总里程："+totalM.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue()+" km");
                    SharedPreferencesUtil.setSharedPreferences(MainActivity.this,"totalMileage",String.valueOf(totalMileage));                          currentMileage = 0;
                    if (lm != null) {
                        // 关闭程序时将监听器移除
                        lm.removeUpdates(locationListener);
                        locationListener = null;
                    }
                }
                break;
            default:
                break;
        }
    }

    private boolean isGpsAble(LocationManager lm) {
        return lm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER) ? true : false;
    }


    //打开设置页面让用户自己设置
    private void openGPS2() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, 0);
    }

}
