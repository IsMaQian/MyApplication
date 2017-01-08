package com.example.mq.dataextract.o.Activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.mq.dataextract.R;

/**
 * Created by Administrator on 2016/11/20.
 */
public class FlyStyleActivity extends Fragment implements AdapterView.OnItemSelectedListener{
    Spinner flyChose;
    Spinner controlChose;
    Spinner cellStyle;
    Spinner stopRet;
    Button dataset;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.flystyle, container, false);
        flyChose = (Spinner) view.findViewById(R.id.flyChose1);
        flyChose.setPrompt("机型选择");
        flyChose.setOnItemSelectedListener(this);
        controlChose = (Spinner) view.findViewById(R.id.controlChose1);
        controlChose.setPrompt("遥控选择");
        controlChose.setOnItemSelectedListener(this);
        cellStyle = (Spinner) view.findViewById(R.id.cellTyle1);
        cellStyle.setPrompt("电池选项");
        cellStyle.setOnItemSelectedListener(this);
        stopRet = (Spinner) view.findViewById(R.id.controlRet1);
        stopRet.setPrompt("遥控回中");
        stopRet.setOnItemSelectedListener(this);
        dataset = (Button) view.findViewById(R.id.dataSet);
        dataset.setOnClickListener(new DataSetOnListener());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.flyChose,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
        flyChose.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.contronChose,
                android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
        controlChose.setAdapter(adapter1);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.cellChose,
                android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
        cellStyle.setAdapter(adapter2);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.controlRet,
                android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
        stopRet.setAdapter(adapter3);
        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.flyChose1:
                break;
            case R.id.controlChose1:
                break;
            case R.id.cellTyle1:
                break;
            case R.id.controlRet1:
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class DataSetOnListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }



}
