package com.battery.bms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.battery.battery.R;
import com.littlejie.circleprogress.DialProgress;
import com.littlejie.circleprogress.WaveProgress;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mBtnResetAll;
    private WaveProgress mWaveProgress;
    private DialProgress mDialProgress;
    private Random mRandom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDialProgress = (DialProgress) findViewById(R.id.dial_progress_bar);
        mWaveProgress = (WaveProgress) findViewById(R.id.wave_progress_bar);
        mDialProgress.setOnClickListener(this);
        mWaveProgress.setOnClickListener(this);
        mRandom = new Random();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_reset_all:
//                mDialProgress.reset();
//                break;
            case R.id.dial_progress_bar:
                mDialProgress.setValue(mRandom.nextFloat() * mDialProgress.getMaxValue());
                break;
            case R.id.wave_progress_bar:
                mWaveProgress.setValue(mRandom.nextFloat() * mWaveProgress.getMaxValue());
                break;
                default:
                    break;
        }
    }
}
