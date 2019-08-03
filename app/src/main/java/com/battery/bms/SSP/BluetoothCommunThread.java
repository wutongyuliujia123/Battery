package com.battery.bms.SSP;

import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import com.battery.bms.BluetoothTools.BluetoothTools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 蓝牙通讯线程
 */
public class BluetoothCommunThread extends Thread {

    private Handler serviceHandler;        //与Service通信的Handler
    private BluetoothSocket socket;
    private InputStream mmInStream;        //对象输入流
    private OutputStream mmOutStream;    //对象输出流
    public volatile boolean isRun = true;    //运行标志位

    /**
     * 构造函数
     *
     * @param handler 用于接收消息
     * @param socket
     */
    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    public BluetoothCommunThread(Handler handler, BluetoothSocket socket) {
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        this.serviceHandler = handler;
        this.socket = socket;
        try {
            tmpOut = socket.getOutputStream();
            tmpIn = socket.getInputStream();
        } catch (Exception e) {
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            //发送连接失败消息
//            serviceHandler.obtainMessage(BluetoothTools.MESSAGE_CONNECT_ERROR).sendToTarget();
            e.printStackTrace();
        }
        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    @Override
    public void run() {

         // bytes returned from read()

        int totalBytes = 0;
        byte[] totalbuffer = new byte[66];
        while (true) {
            try {
                byte[] buffer = new byte[132];  // buffer store for the stream
                int bytes;

                bytes = mmInStream.read(buffer);
                System.out.println("数据长度："+bytes);
                for(int j=0;j<bytes;j++){
                    totalbuffer[totalBytes] = buffer[j];
                    totalBytes++;
                    if(totalBytes == 66){
                        break;
                    }
                }
               // String s = bytesTohex(buffer);
               // System.out.println("获取到的数据："+s);

                if(totalBytes == 66){
                    serviceHandler.obtainMessage(BluetoothTools.MESSAGE_READ_OBJECT, totalBytes, -1, totalbuffer)
                        .sendToTarget();
                    totalBytes = 0;
                }


            } catch (IOException e) {
                break;
            }
        }


        //关闭流
        if (mmInStream != null) {
            try {
                mmInStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (mmOutStream != null) {
            try {
                mmOutStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* Call this from the main activity to send data to the remote device */
    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) { }
    }


    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            socket.close();
        } catch (IOException e) { }
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
