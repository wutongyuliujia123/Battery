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
import com.battery.bms.utils.SpeedUtil;
import com.littlejie.circleprogress.DialProgress;
import com.littlejie.circleprogress.WaveProgress;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnResetAll;
    private WaveProgress mWaveProgress;
    private DialProgress mDialProgress;
    private Switch mSwitch;
    private Random mRandom;
    private TextView tv_date;
    private LocationManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!isGpsAble(lm)) {
            Toast.makeText(MainActivity.this, "请打开GPS~", Toast.LENGTH_SHORT).show();
            openGPS2();
        }
        Location lc = SpeedUtil.getLastKnownLocation(MainActivity.this);

        mDialProgress = (DialProgress) findViewById(R.id.dial_progress_bar);
        mWaveProgress = (WaveProgress) findViewById(R.id.wave_progress_bar);
        mSwitch = (Switch) findViewById(R.id.switch_button);
        tv_date = (TextView) findViewById(R.id.tv_date);
        //tv_date.setText(DateUtils.getDate());


        tv_date.setText((lc != null ? lc.getSpeed() : 0) + "km/h");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 8, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                tv_date.setText((location!=null?location.getSpeed() : 0)*3.6 + "千米/h");
                mDialProgress.setValue((location!=null?location.getSpeed() : 0)*36/10);
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
        });

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
                } else {
                    Toast.makeText(MainActivity.this, "关闭", Toast.LENGTH_SHORT).show();
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
