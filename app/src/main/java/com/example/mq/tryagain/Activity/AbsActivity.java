package com.example.mq.tryagain.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.mq.tryagain.R;
import com.example.mq.tryagain.Scroller.SildingFinishLayout;
import com.example.mq.tryagain.Scroller.SlideCutListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/4.
 */

public class AbsActivity extends Activity implements SlideCutListView.RemoveListener{
    private List<String> list = new ArrayList<String>();
    private SlideCutListView slideCutListView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abslistview);
        for (int i=0;i<=30;i++) {
            list.add("数据" + i);
        }
        slideCutListView = (SlideCutListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        slideCutListView.setAdapter(adapter);
        slideCutListView.setRemoveListener(this);
        SildingFinishLayout mSildingFinishLayout = (SildingFinishLayout) findViewById
                (R.id.sildingFinishLayout);
        mSildingFinishLayout.setOnSildingFinishListener(new SildingFinishLayout.OnSildingFinishListener() {
            @Override
            public void onSildingFinish() {
                AbsActivity.this.finish();
            }
        });
        mSildingFinishLayout.setTouchView(slideCutListView);
        slideCutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(AbsActivity.this, NormalActivity.class));
                overridePendingTransition(R.anim.right_in,R.anim.left_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    @Override
    public void removeItem(SlideCutListView.RemoveDirection direction, int position) {
        adapter.remove(adapter.getItem(position));
        switch (direction) {
           case RIGHT:
               Toast.makeText(this, "向右删除", Toast.LENGTH_LONG).show();
               break;
            case LEFT:
                Toast.makeText(this, "向左删除", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
