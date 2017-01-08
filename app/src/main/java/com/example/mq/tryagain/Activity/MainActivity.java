package com.example.mq.tryagain.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mq.tryagain.R;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText editText;
    GestureOverlayView gestureOverlayView;
    File SDPATH = Environment.getExternalStorageDirectory();
    String path = SDPATH.toString() + File.separator + "mygesture";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.desture_name);
        gestureOverlayView = (GestureOverlayView) findViewById(R.id.gesture);
        gestureOverlayView.setGestureColor(Color.CYAN);
        gestureOverlayView.setGestureStrokeWidth(4);
        gestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, final Gesture gesture) {
                View saveDialog = getLayoutInflater().inflate(R.layout.save, null);
                ImageView imageView = (ImageView) saveDialog.findViewById(R.id.show);
                final EditText gestureName = (EditText) saveDialog.findViewById(R.id.desture_name);
                Bitmap bitmap = gesture.toBitmap(128, 128, 10, 0xffff0000);
                imageView.setImageBitmap(bitmap);
                new AlertDialog.Builder(MainActivity.this)
                        .setView(saveDialog)
                        .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                GestureLibrary gestureLibrary = null;
                                gestureLibrary = GestureLibraries.fromFile(path);
                                gestureLibrary.addGesture(gestureName.getText().toString(), gesture);
                                gestureLibrary.save();
                                Toast.makeText(MainActivity.this, "saved"+path, Toast.LENGTH_LONG).show();
                            }
                        }).setNegativeButton("取消", null).show();
            }
        });
       button = (Button) findViewById(R.id.button);
       button.setOnClickListener(new ButtonListener());
    }

   class ButtonListener implements View.OnClickListener {
       @Override
       public void onClick(View v) {
           startActivity(new Intent(MainActivity.this, SecondActivity.class));
           overridePendingTransition(R.anim.right_in,R.anim.left_out);
       }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
