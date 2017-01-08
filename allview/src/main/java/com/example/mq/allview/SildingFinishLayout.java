package com.example.mq.allview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * Created by Administrator on 2016/12/4.
 */

public class SildingFinishLayout extends RelativeLayout implements View.OnTouchListener{
    private ViewGroup mparentView;
    private View touchView;
    private int mTouchSlop;
    private int downX;
    private int downY;
    private int tempX;
    private Scroller mScroller;
    private int viewWidth;
    private boolean isSilding;
    private boolean isFinish;
    private OnSildingFinishListener onSildingFinishListener;

    public SildingFinishLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SildingFinishLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs,defStyle);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            mparentView = (ViewGroup) this.getParent();
            viewWidth = this.getWidth();
        }
    }

    public void setOnSildingFinishListener(OnSildingFinishListener onSildingFinishListener) {
        this.onSildingFinishListener = onSildingFinishListener;
    }

    public void setTouchView(View touchView) {
        this.touchView = touchView;
        touchView.setOnTouchListener(this);
    }

    public View getTouchView() {
        return touchView;
    }

    private void scrollRight() {
        final int delta = (viewWidth + mparentView.getScrollX());
        mScroller.startScroll(mparentView.getScrollX(),0,-delta+1,0);
    }

    private void scrollOrigin() {
        int delta = mparentView.getScrollX();
        mScroller.startScroll(mparentView.getScrollX(), 0, -delta, 0, Math.abs(delta));
        postInvalidate();
    }

    private boolean isTouchOnAbsListView() {
        return touchView instanceof AbsListView ? true : false;
    }

    private boolean idTouchOnScrollView() {
        return touchView instanceof ScrollView ? true : false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch(motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = tempX = (int) motionEvent.getRawX();
                downY = (int) motionEvent.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int)motionEvent.getRawX();
                int deltaX = tempX - moveX;
                tempX = moveX;
                if (Math.abs(moveX - downX) > mTouchSlop &&
                        Math.abs((int) motionEvent.getRawY() - downY )< mTouchSlop) {
                    isSilding = true;
                    if (isTouchOnAbsListView()) {
                        MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);
                        cancelEvent
                                .setAction(MotionEvent.ACTION_CANCEL
                                        | (motionEvent.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                        view.onTouchEvent(cancelEvent);
                    }

                }
                if (moveX - downX >= 0 && isSilding) {
                    mparentView.scrollBy(deltaX, 0);
                    if (isTouchOnAbsListView() || isTouchOnAbsListView()) {
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                isSilding = false;
                if (mparentView.getScrollX() <= -viewWidth / 2) {
                    isFinish = true;
                    scrollRight();
                } else {
                    scrollOrigin();
                    isFinish = false;
                }
                break;
        }
        if (idTouchOnScrollView() || isTouchOnAbsListView()) {
            return view.onTouchEvent(motionEvent);
        }


        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mparentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
            if (mScroller.isFinished()) {
                if (onSildingFinishListener != null && isFinish) {
                    onSildingFinishListener.onSildingFinish();
                }
            }
        }
    }

    public interface OnSildingFinishListener {
        public void onSildingFinish();
    }
}
