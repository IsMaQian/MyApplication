package com.example.mq.dataextract.o.Activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mq.dataextract.R;

/**
 * Created by Administrator on 2016/11/23.
 */
public class SensorStateActivity extends Fragment {
    TextView voltage;
    TextView start;
    TextView accelerate;
    TextView angle;
    TextView geomagnetism;
    TextView gyroscope;
    TextView flyState;
    TextView remoteControl;
    TextView range;
    TextView hight;
    TextView speed;
    TextView distance;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sensorstate, container, false);
        voltage = (TextView) view.findViewById(R.id.voltage);
        start = (TextView) view.findViewById(R.id.start);
        accelerate = (TextView) view.findViewById(R.id.accelerate);
        angle = (TextView) view.findViewById(R.id.angle);
        geomagnetism = (TextView) view.findViewById(R.id.geomagnetism);
        gyroscope = (TextView) view.findViewById(R.id.gyroscope);
        flyState = (TextView) view.findViewById(R.id.flyState);
        remoteControl = (TextView) view.findViewById(R.id.remoteControl);
        range = (TextView) view.findViewById(R.id.range);
        hight = (TextView) view.findViewById(R.id.hight);
        speed = (TextView) view.findViewById(R.id.speed);
        distance = (TextView) view.findViewById(R.id.distance);

        return view;
    }
}
