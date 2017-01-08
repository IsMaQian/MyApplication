package com.example.mq.tryagain.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ScrollView;

import com.example.mq.tryagain.R;
import com.example.mq.tryagain.Scroller.SildingFinishLayout;

/**
 * Created by Administrator on 2016/12/4.
 */

public class ScrollActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        ScrollView mScrollView = (ScrollView) findViewById(R.id.scrollView1);
        SildingFinishLayout mSilding = (SildingFinishLayout) findViewById(
                R.id.sildingFinishLayout
        );
        mSilding.setOnSildingFinishListener(new SildingFinishLayout.OnSildingFinishListener() {
            @Override
            public void onSildingFinish() {
                ScrollActivity.this.finish();
            }
        });
        mSilding.setTouchView(mScrollView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
    }
}
