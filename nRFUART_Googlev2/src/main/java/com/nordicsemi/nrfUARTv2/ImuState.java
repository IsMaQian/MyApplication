package com.nordicsemi.nrfUARTv2;

import com.nordicsemi.nrfUARTv2.MainActivity.ThreadShow;
import com.nordicsemi.nrfUARTv2.R.string;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ImuState extends Activity  {
    static  TextView Acc_stateringt ;
    static TextView Mag_stateringt;
    static TextView Zitai_stateringt;
    static TextView Voltage_stateringt;
    static  TextView Gyro_stateringt;
    
    static  TextView strnum_value;
    static TextView jixing_value;
    static TextView yaokongqi_value;
    static TextView fudu_value;
    static  TextView gaodu_value;
    static TextView sudu_value;
    static  TextView juli_value;
//	static int Imu_Check_Attitude ;
//	static int Imu_Check_Acc;
//	static int Imu_Check_Gyro;
//	static int Imu_Check_Magcalib;
	static UartService mService1 = null;
	static String  strSensorStatusshow;
	MainActivity mainactivity;
	public Button  buttonback;//����
	static SeekBar SeekBar = null;
	private Handler hangler=new Handler();
    
	  public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.imu_state); 
	  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	  
	  ActionBar mActionBar=getActionBar();
	  mActionBar.setHomeButtonEnabled(true);
	  mActionBar.setDisplayHomeAsUpEnabled(true);
	  mActionBar.setDisplayShowHomeEnabled(false);  
	  mActionBar.setDisplayShowCustomEnabled(true);
	  mActionBar.setDisplayShowTitleEnabled(true); 
	  mActionBar.setTitle("������״̬");
	  
      Acc_stateringt =(TextView)findViewById(R.id.textViewAcc_State);
	  Mag_stateringt =(TextView)findViewById(R.id.textViewMag_State);
	  Zitai_stateringt =(TextView)findViewById(R.id.textViewZitai_State);
	  Voltage_stateringt =(TextView)findViewById(R.id.textViewVol_State);
	  Gyro_stateringt=(TextView)findViewById(R.id.textViewGyro_State);
	  buttonback= (Button) findViewById(R.id.buttonback);
      SeekBar = (SeekBar) findViewById(R.id.seekbarscr);
      
      
     strnum_value =(TextView)findViewById(R.id.textViewstrnumvalue);
     jixing_value =(TextView)findViewById(R.id.textViewjixingvalue);
     yaokongqi_value =(TextView)findViewById(R.id.textViewykqvalue);
     fudu_value =(TextView)findViewById(R.id.textViewfuduwvalue);
     gaodu_value =(TextView)findViewById(R.id.textViewgaoduwvalue);
     sudu_value=(TextView)findViewById(R.id.textViewworkspeedwvvalue);
     juli_value=(TextView)findViewById(R.id.textViewjuiwvalue);
      
      SeekBar.setMax(255);
      try {
		SeekBar.setProgress(Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS));
	  } catch (SettingNotFoundException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	  }
      SeekBar.setOnSeekBarChangeListener(onSeekbar);
	  //=========================���ص�������==========================//
	  buttonback.setOnClickListener(new View.OnClickListener() {
          
          @Override
          public void onClick(View v) {

                Intent intent = getIntent();
                ImuState.this.setResult(0, intent);
                ImuState.this.finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);	
          }
      });
	
      
  }
	  public boolean onOptionsItemSelected(MenuItem item)
	  {
	      // TODO Auto-generated method stub
	      if(item.getItemId() == android.R.id.home)
	      {
	    	
	          finish();
	          overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	          return true;
	      }
	      return super.onOptionsItemSelected(item);
	  }
  OnSeekBarChangeListener  onSeekbar=new OnSeekBarChangeListener() {
		
		@Override
		//���α��ƶ�ֹͣ��ʱ����ø÷���
		public void onStopTrackingTouch(SeekBar seekBar) {
		//���ñ��Ϊ��Ҫˢ��
			//flag=true;
	   //����ʱ�Ϳ�ʼ�Զ����¸��϶���
			//refresh();
		}
		@Override
		//���α꿪ʼ�ƶ�ʱ���ø÷���
		public void onStartTrackingTouch(SeekBar seekBar) {
		//ֹͣˢ��
		//	flag=false;
			
		}
		
		@Override
		//���������α걻�ı���߽������ı�ʱ���ø÷���
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			MainActivity.Screenflagchange = true;
			MainActivity.tmpInt = SeekBar.getProgress() ;
			if(MainActivity.Screenflagchange)
			{
				
	 			//������С��40ʱ�����ó�40����ֹ̫�ڿ������ĺ����
			    if (MainActivity.tmpInt < 40) {
			    	MainActivity.tmpInt = 40;
	 			}
	 			if ((0 <= MainActivity.tmpInt) && (MainActivity.tmpInt < 85)) {
	 				MainActivity.ledcontrolflag = 0;
	 			}
	 			if ((85 <= MainActivity.tmpInt) && (MainActivity.tmpInt < 170)) {
	 				MainActivity.ledcontrolflag = 1;
	 			}
	 			if ((170 <= MainActivity.tmpInt) && (MainActivity.tmpInt <= 255)) {
	 				MainActivity.ledcontrolflag = 2;
	 			}
			}
	 			//���ݵ�ǰ���ȸı�����
//	 			Settings.System.putInt(getContentResolver(),
//	 					Settings.System.SCREEN_BRIGHTNESS, mainactivity.tmpInt);
//	 			mainactivity.tmpInt = Settings.System.getInt(getContentResolver(),
//	 					Settings.System.SCREEN_BRIGHTNESS, -1);
//	 			WindowManager.LayoutParams wl = getWindow()
//	 					.getAttributes();

//	 			float tmpFloat = (float) tmpInt / 255;
//	 			if (tmpFloat > 0 && tmpFloat <= 1) {
//	 				wl.screenBrightness = tmpFloat;
//	 			
//	 				}
//	 				getWindow().setAttributes(wl);
		
		}
		
	};
	private void refresh() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//�����Ȳ���1000���͸���status
				while( SeekBar.getProgress()<100)
				{
//					try {
//						//��ͣ1��
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						// TODO: handle exception
//						e.printStackTrace();
//					}
					//��һ��Runnable������ӵ���Ϣ���е��У�
					//���ҵ�ִ�е��ö���ʱִ��run()����
					hangler.post(new Runnable() {
						
						@Override
						public void run() {
							// �������ý�������ǰ��ֵ
							//SeekBar.setProgress(SeekBar.getProgress()+1);
							
						}
					});
				}
			}
		}).start();
     }


}
