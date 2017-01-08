package com.example.mq.dataextract.o.Activity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mq.dataextract.R;

public class ZuoyeActivity extends Fragment {
    EditText workSpeed;
    float text_workspeed;
    EditText sendField;
    float text_sendfield;
    EditText workHigh;
    float text_workhigh;
    EditText workDis;
    float text_workdis;
    Button dataGet;
    Button dataSet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zuoyedata, container, false);
        workSpeed = (EditText) view.findViewById(R.id.workSpeed);
        sendField = (EditText) view.findViewById(R.id.sendField);
        workHigh = (EditText) view.findViewById(R.id.workHigh);
        workDis = (EditText) view.findViewById(R.id.workDis);
        dataGet = (Button) view.findViewById(R.id.dataGet);
        dataSet = (Button) view.findViewById(R.id.dataSet);
        dataSet.setOnClickListener(new dataSetOnListener());
        return view;
    }

    class dataSetOnListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if ((workSpeed.length() == 0) || (sendField.length() == 0) ||
                    (workHigh.length() == 0) || (workDis.length() == 0)) {
                new AlertDialog.Builder(ZuoyeActivity.this.getActivity())
                        .setTitle("警告信息")
                        .setMessage("请确保输入完整")
                        .setPositiveButton("确定", null)
                        .show();
                return;
            }
            text_workspeed = Float.parseFloat(workSpeed.getText().toString());
            text_sendfield = Float.parseFloat(sendField.getText().toString());
            text_workhigh = Float.parseFloat(workHigh.getText().toString());
            text_workdis = Float.parseFloat(workDis.getText().toString());
            SetWorkIndex(text_workspeed, text_sendfield, text_workhigh, text_workdis);
        }
    }
    public void SetWorkIndex(float Set_work_speed,float Set_work_radius,float Set_pensa_radius,float Set_work_height)
    {
        short sTemp;
        byte[] TxData = new byte[14];

        TxData[0] = (byte)0x1E;
        TxData[1] = (byte)0xE1;
        TxData[2] = (byte)0x0F;
        sTemp = (short)((Set_work_speed+0.005) * 100.0);
        TxData[3] = (byte)((sTemp&0xFF00)>>8);
        TxData[4]=(byte)(sTemp&0x00FF);
        sTemp = (short)((Set_work_radius+0.005) * 100.0);
        TxData[5] = (byte)((sTemp&0xFF00)>>8);
        TxData[6]=(byte)(sTemp&0x00FF);
        sTemp = (short)((Set_pensa_radius+0.005) * 100.0);
        TxData[7] = (byte)((sTemp&0xFF00)>>8);
        TxData[8]=(byte)(sTemp&0x00FF);
        sTemp = (short)((Set_work_height+0.005) * 100.0);
        TxData[9] = (byte)((sTemp&0xFF00)>>8);
        TxData[10]=(byte)(sTemp&0x00FF);
        CalcBcc(TxData,10);


    }
    public static void CalcBcc(byte[] TxData, int nByte)
    { // 计算校验和

        short sBcc = 0;
        for(int i=2;i<=nByte;i++)
        {
            if(TxData[i] < 0)
            {
                TxData[i] = (byte)(TxData[i] + 256);
                sBcc += (TxData[i] & 0x00FF);
            }
            else
                sBcc += (TxData[i] & 0x00FF);
        }

        TxData[nByte+1] = (byte)((sBcc >> 8) & 0x00FF);
        TxData[nByte+2] = (byte)(sBcc & 0x00FF);

        return;
    }

}
