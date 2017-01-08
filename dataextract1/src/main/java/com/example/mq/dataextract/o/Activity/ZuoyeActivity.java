package com.example.mq.dataextract.o.Activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mq.dataextract.R;

public class ZuoyeActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zuoyedata, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
