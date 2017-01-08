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
public class PIDSetActivity extends Fragment {
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pidset, container, false);
        textView = (TextView) view.findViewById(R.id.tet);
        textView.setText(TabMainActivity.notice1);
        return view;
    }
}
