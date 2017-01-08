package com.example.mq.testimage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2016/12/1.
 */

public class MainActivityPaint extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TextView(this));
    }

    class TextView extends View {
        final String DRAW_STR = "我叫马茜，哈哈哈哈哈哈";
        Path[] paths = new Path[3];
        Paint paint;
        public TextView(Context context) {
            super(context);
            paths[0] = new Path();
            paths[0].moveTo(0, 0);
            for (int i=1;i<=20;i++) {
                paths[0].lineTo(i*30,(float) Math.random()*30);
            }
            paths[1] = new Path();
            //矩形的绘制
            RectF rectF = new RectF(0, 0, 600, 360);
            paths[1].addOval(rectF, Path.Direction.CCW);
            paths[2] = new Path();
            paths[2].addArc(rectF, 60, 180);
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.CYAN);
            paint.setStrokeWidth(1);
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(Color.WHITE);
            canvas.translate(40, 40);
            paint.setTextAlign(Paint.Align.LEFT);
//            paint.setTextSize(5);
            //绘制路径
            paint.setStyle(Paint.Style.STROKE);
//            canvas.drawPath(paths[0], paint);
            paint.setTextSize(50);
//            paint.setStyle(Paint.Style.FILL);
            canvas.drawTextOnPath(DRAW_STR, paths[0], -8, 20, paint);
            canvas.translate(0, 60);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(paths[1], paint);
//            paint.setStyle(Paint.Style.FILL);
            canvas.drawTextOnPath(DRAW_STR, paths[1], -8, 20, paint);
            canvas.translate(0, 360);
//            paint.setStyle(Paint.Style.FILL);
            canvas.drawTextOnPath(DRAW_STR,paths[2],-2,20,paint);
        }
    }
}
