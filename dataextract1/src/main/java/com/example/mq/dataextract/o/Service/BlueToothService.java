package com.example.mq.dataextract.o.Service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.mq.dataextract.o.State.BlueToothState;

/**
 * Created by Administrator on 2016/11/19.
 */
public class BlueToothService extends Service {
    static BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int MSG = intent.getIntExtra("MSG", 0);
        if (MSG == BlueToothState.ToothMSG.OPEN_TOOTH) {

            if (adapter != null) {
                if (!adapter.isEnabled()) {
                    Intent intent1 = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                    intent1
                }
            }

        }
        return super.onStartCommand(intent, flags, startId);
    }
}
