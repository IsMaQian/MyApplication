package com.example.mq.dataextract.o.State;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by Administrator on 2016/12/13.
 */

public class PagerSlidingTabStrip extends HorizontalScrollView {
    public PagerSlidingTabStrip(Context context) {
        this(context, null);
    }
    public PagerSlidingTabStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs,defStyle);
    }

}
