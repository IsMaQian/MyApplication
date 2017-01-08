package com.example.mq.dataextract.o.Activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mq.dataextract.R;
import com.example.mq.dataextract.o.Activity.TabMainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/11/12.
 */
public class LinkedTooth extends Activity{
    TextView textView;
    ListView listView;
    BluetoothAdapter adapter = TabMainActivity.adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linkedtooth);
        textView = (TextView) findViewById(R.id.tet);
        List<String> devices = new ArrayList<>();
        Set<BluetoothDevice> bondedDevices = adapter.getBondedDevices();
                System.out.println("已配对蓝牙有"+bondedDevices.size()+"个");
        if (bondedDevices.size() > 0) {
            for (BluetoothDevice device : bondedDevices) {
                devices.add(device.getName() + "-" + device.getAddress()+"\n");
            }
        }
//                StringBuilder text = new StringBuilder();
                for (String device : devices) {
                    textView.append(device+"\n");
                    System.out.println(textView);
                }
    }
}
