package com.example.mq.tryagain.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.mq.tryagain.R;
import com.example.mq.tryagain.Scroller.SildingFinishLayout;

/**
 * Created by Administrator on 2016/12/3.
 */

public class NormalActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        SildingFinishLayout mSilding = (SildingFinishLayout)
                findViewById(R.id.sildingFinishLayout);
        mSilding.setOnSildingFinishListener(new SildingFinishLayout.OnSildingFinishListener() {
            @Override
            public void onSildingFinish() {
                NormalActivity.this.finish();
            }
        });
        mSilding.setTouchView(mSilding);
    }

}
