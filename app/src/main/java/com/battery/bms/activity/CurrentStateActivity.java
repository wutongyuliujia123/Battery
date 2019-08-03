package com.battery.bms.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Button;
import com.battery.battery.R;
import com.battery.battery.databinding.ActivityCurrentStateBinding;
import com.battery.bms.model.CurrentState;

public class CurrentStateActivity extends AppCompatActivity {

    Button bt ;
    CurrentState currentState = new CurrentState();;
    ObservableList cellvoltagelist = new ObservableArrayList();
    ObservableList minmax = new ObservableArrayList();
    ObservableList templist = new ObservableArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCurrentStateBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_current_state);

        Intent intent=getIntent();
        String data = intent.getStringExtra("data");
         //data ="05E8036C07EF083407D9083E06A409C209BE07F707AB0608065408C2087B07E7000078D4000000000BD90BD60BDC000009C40000000000002B560022002E00FB00D4";

        dataConversion(data);

        binding.setCurrentstate(currentState);


       // setContentView(R.layout.activity_current_state_demo);
       /* bt = (Button) findViewById(R.id.bt_Current);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"修改成功",Toast.LENGTH_LONG).show();
                finish();
            }
        });*/
    }

    public void dataConversion(String data){
        char[] ch = data.toCharArray();
        int[] intarray = new int[66];
        int j = 0;
        int i = 0;
        for(i=0;i<66;i++){
            intarray[i] = Integer.valueOf("" + ch[j] + ch[j+1],16);
            j+=2;
        }
        int[] arr = new int[29];
        i=0;
        j=0;
        int min =0,max=0;
        while(i<29){
            if(i==16 || i==17 || i==21 || i==22){
                arr[i] = intarray[j]<<24|intarray[j+1]<<16|intarray[j+2]<<8|intarray[j+3];
                j+=4;
                i++;
            }else{
                arr[i] = intarray[j]<<8|intarray[j+1];
                j+=2;
                i++;
            }
        }



        for(i=0;i<29;i++){
            if(i<16){
                cellvoltagelist.add(arr[i]+"");

                if(arr[min]>arr[i])
                    min=i;
                if(arr[max]<arr[i])
                    max=i;

            }else if(i==18 || i== 19 || i==20){
                templist.add(arr[i]/100+"."+arr[i]%100);
            }else{
                switch(i){
                    case 16 :
                        currentState.setPackvoltage(new ObservableField<String>(arr[i]+""));
                        break;
                    case 17 :
                        currentState.setCurrent(new ObservableField<String>(arr[i]+""));
                        break;
                    case 21 :
                        currentState.setDesignedcapacity(new ObservableField<String>(arr[i]+""));
                        break;
                    case 22 :
                        currentState.setRemainingcapacity(new ObservableField<String>(arr[i]+""));
                        break;
                    case 23 :
                        currentState.setSoc(new ObservableField<String>(arr[i]+""));
                        break;
                    case 24 :
                        currentState.setCyclecount(new ObservableField<String>(arr[i]+""));
                        break;
                    case 25 :
                        currentState.setPackstatus(new ObservableField<String>(arr[i]+""));
                        break;
                    case 26 :
                        currentState.setBatterystaus(new ObservableField<String>(arr[i]+""));
                        break;
                    case 27 :
                        currentState.setPackconfig(new ObservableField<String>(arr[i]+""));
                        break;
                    case 28 :
                        currentState.setManufactureaccess(new ObservableField<String>(arr[i]+""));
                        break;
                    default :
                        break;
                }
            }

        }
        for(i=0;i<16;i++){
            if(i==min || i==max){
                minmax.add(false);
            }else{
                minmax.add(true);
            }
        }
        currentState.setMinmax(minmax);
        currentState.setBalancevoldiff(new ObservableField<String>((arr[max]-arr[min])+""));
        currentState.setCellvoltagelist(cellvoltagelist);
        currentState.setTemplist(templist);
    }

}
