package com.example.mq.dataextract.o.Activity;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.mq.dataextract.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/8.
 */
public class DataExtract extends ListActivity {
    private SimpleAdapter simpleAdapter;
    Intent intent;
    BluetoothAdapter adapter = TabMainActivity.adapter;
    List<Map<String, String>> listItems;
    BluetoothSocket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listItems = new ArrayList<Map<String, String>>();
        Map<String, String> listItem = new HashMap<String, String>();
        intent= getIntent();
        listItem.put("name1",intent.getStringExtra("name"));
        listItem.put("address1", intent.getStringExtra("address"));
        listItems.add(listItem);

        simpleAdapter = new SimpleAdapter(this, listItems, R.layout.bluetooth_item,
                new String[]{"name1", "address1"}, new int[]{R.id.tooth_name, R.id.tooth_address});
        setListAdapter(simpleAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        adapter.cancelDiscovery();
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("address2",listItems.get(position).get("address1"));
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
