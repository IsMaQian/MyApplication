package com.example.mq.dataextract.o.Activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mq.dataextract.R;
import com.example.mq.dataextract.o.Listener.TabListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/11/20.
 */
public class TabMainActivity extends Activity {

    static BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    private BroadcastReceiver receiver;
    private int ENABLE_BLUETOOTH = 2;
    String address;
    public static String notice1;
    private ConnectThread connectThread;
    private ConnectedThread connectedThread;
    public static final int NOTICE_VIEW = 1;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        openBlueTooth();

        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tab1 = actionBar.newTab()
                .setText("作业参数")
                .setTabListener(
                        new TabListener<ZuoyeActivity>(this, "zuoyeData", ZuoyeActivity.class)
                );
        actionBar.addTab(tab1);
        ActionBar.Tab tab2 = actionBar.newTab()
                .setText("机型设置")
                .setTabListener(
                        new TabListener<FlyStyleActivity>(this, "flystyle", FlyStyleActivity.class)
                );
        actionBar.addTab(tab2);
        ActionBar.Tab tab3 = actionBar.newTab()
                .setText("PID设置")
                .setTabListener(
                        new TabListener<PIDSetActivity>(this, "PIDset", PIDSetActivity.class)
                );
        actionBar.addTab(tab3);
        ActionBar.Tab tab4 = actionBar.newTab()
                .setText("传感器状态")
                .setTabListener(
                        new TabListener<SensorStateActivity>(this, "Sensor", SensorStateActivity.class)
                );
        actionBar.addTab(tab4);

        handler=new Handler(){
            public void handleMessage(Message msg) {
                Bundle bundle = null;
                switch (msg.what) {
                    case NOTICE_VIEW:
                        bundle = msg.getData();
                        String notice = bundle.getString("notice");
                        notice1 = notice;
                        break;
                }
            }
        };



    }

    public void openBlueTooth() {
        if (adapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        } else {
            if (!adapter.isEnabled()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, ENABLE_BLUETOOTH);
//                    Toast.makeText(this, "蓝牙开启成功", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "蓝牙已开启", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.start_bluetooth) {
//            Intent intent = new Intent(this, BlueToothService.class);
//            intent.putExtra("MSG", BlueToothState.ToothMSG.OPEN_TOOTH);
//            startService(intent);
            if (adapter != null) {
                if (!adapter.isEnabled()) {
                    Intent intent1 = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent1, ENABLE_BLUETOOTH);
//                    Toast.makeText(this, "蓝牙开启成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "蓝牙已开启", Toast.LENGTH_LONG).show();
                }
            }
            return true;
        } else if (id == R.id.show_devices) {

            if (adapter != null) {
                if (!adapter.isEnabled()) {
                    Toast.makeText(this, "蓝牙未开启", Toast.LENGTH_SHORT).show();
                    return true;
                }
                Intent intent = new Intent();
                intent.setClass(this, LinkedTooth.class);
                startActivity(intent);
//                List<String> devices = new ArrayList<>();
//                Set<BluetoothDevice> bondedDevices = adapter.getBondedDevices();
//                for (BluetoothDevice device : bondedDevices) {
//                    devices.add(device.getName() + "-" + device.getAddress());
//                }
//                StringBuilder text = new StringBuilder();
//                for (String device : devices) {
//                    textView.append(device + "\n");
//                }
//                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (id == R.id.close_tooth) {
            adapter.disable();
        }  else if (id == R.id.search_bluetooth) {
            if (!adapter.isEnabled()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent,ENABLE_BLUETOOTH);
            }else if (adapter.isDiscovering())
                adapter.cancelDiscovery();
            adapter.startDiscovery();
            receiver = new BlueToothReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
            filter.addAction(BluetoothDevice.ACTION_FOUND);
            registerReceiver(receiver, filter);
        }
        return super.onOptionsItemSelected(item);
    }
    public class BlueToothReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Intent intent1 = new Intent();
                intent1.setClass(TabMainActivity.this, DataExtract.class);
                intent1.putExtra("name", device.getName());
                intent1.putExtra("address", device.getAddress());
                startActivityForResult(intent1,1);
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                switch (device.getBondState()) {
                    case BluetoothDevice.BOND_BONDING:
//                        textView.setText("配对中。。。。。");
                        Toast.makeText(TabMainActivity.this, "配对中。。。。。", Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothDevice.BOND_BONDED:
//                        textView.setText("配对完成");
                        Toast.makeText(TabMainActivity.this, "配对完成", Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothDevice.BOND_NONE:
//                        textView.setText("配对取消");
                        Toast.makeText(TabMainActivity.this, "配对取消", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                address = bundle.getString("address2");
//                Toast.makeText(this, address, Toast.LENGTH_LONG).show();
                BluetoothDevice device = adapter.getRemoteDevice(address);
                connectThread = new ConnectThread(device);
                connectThread.start();
//                goToLink();
            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }
    private class ConnectThread extends Thread {

        private BluetoothSocket socket;
        private BluetoothDevice device;

        public ConnectThread(BluetoothDevice device) {
            this.device = device;
            BluetoothSocket tmp = null;
//            try {
//                tmp = device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            try {
                Method m = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                try {
                    tmp = (BluetoothSocket) m.invoke(device, Integer.valueOf(2));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            this.socket = tmp;
        }

        @Override
        public void run() {
            adapter.cancelDiscovery();
            try {
                socket.connect();
                connectedThread = new ConnectedThread(socket);
                connectedThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        public void cancel() {
            try {
                socket.close();
            } catch (IOException e) {
                return;
            }
        }
    }

    private class ConnectedThread extends Thread {
        private BluetoothSocket socket;
        private InputStream inputStream;
        private OutputStream outputStream;

        public ConnectedThread(BluetoothSocket socket) {
            this.socket = socket;
            InputStream input=null;
            OutputStream output=null;
            try {
                input = socket.getInputStream();
                output = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.inputStream = input;
            this.outputStream = output;
        }

        @Override
        public void run() {
            StringBuilder recvText = new StringBuilder();
            byte[] buff = new byte[1024];
            int bytes;
            Bundle tmpBundle = new Bundle();
            Message tmpMessage = new Message();
            tmpBundle.putString("notice", "连接成功");
            System.out.println("连接成功");
            tmpMessage.what = NOTICE_VIEW;
            tmpMessage.setData(tmpBundle);
            handler.sendMessage(tmpMessage);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
            Toast.makeText(this,"结束",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.icon)
                .setTitle("Quit AYDrone")
                .setMessage("Do you want to quit this Application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }
}
