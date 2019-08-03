package com.battery.bms.SSP;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import com.battery.bms.BluetoothTools.BluetoothTools;


public class BluetoothClientService extends Service {
    public String TAG = "SecondActivity";
    public String actionCon;
    public BluetoothCommunThread communThread;
    private BroadcastReceiver controlReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            actionCon = intent.getAction();

            //如果匹配成功
            if (BluetoothTools.ACTION_PAIRING_SUCC.equals(actionCon)){
                Bundle mBundle = intent.getExtras();
                BluetoothDevice mBluetoothDevice = mBundle.getParcelable("Pairing_Succ");
                BluetoothClientConnThread mThread = new BluetoothClientConnThread(handler,mBluetoothDevice);
                mThread.start();
            }

            //如果键盘点击send发送数据
            if(BluetoothTools.ACTION_DATA_TO_GAME.equals(actionCon)){

                //String editData = (String)intent.getExtras().get("editViewData");
              //  String editData = "0AB00042";
               // byte[] bytes = hexTobytes(convertStringToHex(editData));
                byte[] bytes =new byte[4];
                //bytes[0] = 0;
               // bytes[1] = -80;
               // bytes[2] = 0;
               // bytes[3] = 66;

                bytes[0] = 10;
                bytes[1] = -80;
                bytes[2] = 00;
                bytes[3] = 66;
                //byte[] bytes = convertStringToHex(editData).getBytes("gbk");
                communThread.write(bytes);
                //communThread.write(bytes);
                communThread.write(bytes);
            }
        }
    };

    public byte[] hexTobytes(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i = i + 2) {
            String subStr = hex.substring(i, i + 2);
            boolean flag = false;
            int intH = Integer.parseInt(subStr, 16);
            if (intH > 127) flag = true;
            if (intH == 128) {
                intH = -128;
            } else if (flag) {
                intH = 0 - (intH & 0x7F);
            }
            byte b = (byte) intH;
            bytes[i / 2] = b;
        }
        return bytes;
    }


    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
    public String convertStringToHex(String str){
        char[] chars = str.toCharArray();
        StringBuffer hex = new StringBuffer();
        for(int i = 0; i < chars.length; i++){
            hex.append(Integer.toHexString((int)chars[i]));
        }
        return hex.toString();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Service创建时的回调函数
     */
    @Override
    public void onCreate() {

        //controlReceiver的IntentFilter
        IntentFilter controlFilter = new IntentFilter();
        controlFilter.addAction(BluetoothTools.ACTION_PAIRING_SUCC);
        controlFilter.addAction(BluetoothTools.ACTION_DATA_TO_GAME);

        //注册BroadcastReceiver
        registerReceiver(controlReceiver, controlFilter);
        super.onCreate();
    }


    Handler handler = new Handler()    {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                //子线程socket连接成功
                case BluetoothTools.MESSAGE_CONNECT_SUCCESS:{
                    Intent mIntent = new Intent(BluetoothTools.ACTION_CONNECT_SUC);
                    sendBroadcast(mIntent);
                    communThread  = new BluetoothCommunThread(handler,(BluetoothSocket)msg.obj);
                    communThread.start();
                    break;

                //子线程连接错误
                } case BluetoothTools.MESSAGE_CONNECT_ERROR:{
                    Intent mIntent = new Intent(BluetoothTools.ACTION_CONNECT_ERROR);
                    sendBroadcast(mIntent);
                    break;

                //子线程读取到数据
                } case BluetoothTools.MESSAGE_READ_OBJECT: {
                    //读取到对象
                    //发送数据广播（包含数据对象）
                    byte[] readBuf = null;
                    readBuf =(byte[]) msg.obj;
                    String ss = bytesTohex(readBuf);
                   /* String ss1 = new String(readBuf, 0,msg.arg1);
                    String ss = null;
                    try {
                        ss = new String(readBuf,"gbk");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }*/
                   // Log.e(TAG,"获取到的数据："+ss);
                    Intent recIntent = new Intent(BluetoothTools.ACTION_RECEIVE_DATA);
                    recIntent.putExtra("recData",ss);
                    sendBroadcast(recIntent);
                    break;
                }
            }
            super.handleMessage(msg);
        }
    };


    @Override
    public void onDestroy() {

        super.onDestroy();
        communThread.cancel();
        unregisterReceiver(controlReceiver);
    }
    public String bytesTohex(byte[] bytes) {
        String strHex = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < bytes.length; n++) {
            strHex = Integer.toHexString(bytes[n] & 0xFF);
            sb.append((strHex.length() == 1) ? "0" + strHex : strHex); // 每个字节由两个字符表示，位数不够，高位补0
        }
        return sb.toString().trim();
    }
}
