package com.battery.bms.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.battery.battery.R;

public class ParameterRepairActivity extends AppCompatActivity {

    Button bt ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter_repair);
        bt = (Button) findViewById(R.id.bt_Revise);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"修改成功",Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
