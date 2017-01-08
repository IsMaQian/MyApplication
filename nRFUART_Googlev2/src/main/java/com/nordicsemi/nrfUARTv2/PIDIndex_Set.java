package com.nordicsemi.nrfUARTv2;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class PIDIndex_Set extends Activity  {
	 static UartService mService1 = null;
	 MainActivity mainactivity;

  public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.set_index);
	  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	
	//  requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);  
//	  setContentView(R.layout.set_index);  
//	  getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title_bar); 
	  
	  Button buttonback;
	  Button buttonsetindex;
	  Button buttongetindex;
	  
	  ActionBar mActionBar=getActionBar();
	  mActionBar.setHomeButtonEnabled(true);
	  mActionBar.setDisplayHomeAsUpEnabled(true);
	  mActionBar.setDisplayShowHomeEnabled(false);  
	  mActionBar.setDisplayShowCustomEnabled(true);
	  mActionBar.setDisplayShowTitleEnabled(true); 
	  mActionBar.setTitle("PID设置");
	
	  final EditText nameTextGypro_KP_RP = (EditText) findViewById(R.id.nameTextGypro_KP_RP);
	  final EditText nameTextGypro_KD_RP = (EditText) findViewById(R.id.nameTextGypro_KD_RP);
	  final EditText nameTextGypro_KI_RP = (EditText) findViewById(R.id.nameTextGypro_KI_RP);
	  
	  final EditText nameTextGypro_KP_Y = (EditText) findViewById(R.id.nameTextGypro_KP_Y);
	  final EditText nameTextGypro_KI_Y = (EditText) findViewById(R.id.nameTextGypro_KI_Y);
	  final EditText nameTextGypro_KD_Y = (EditText) findViewById(R.id.nameTextGypro_KD_Y);
	  
	  final EditText nameTextProp_KP_RP = (EditText) findViewById(R.id.nameTextProp_KP_RP);
	  final EditText nameTextProp_KI_RP = (EditText) findViewById(R.id.nameTextProp_KI_RP);
	
	  final EditText nameTextProp_KP_Y = (EditText) findViewById(R.id.nameTextProp_KP_Y);
	  final EditText nameTextProp_KI_Y = (EditText) findViewById(R.id.nameTextProp_KI_Y);
	 
	  buttonback = (Button) findViewById(R.id.cancel_btn);//返回
	  buttonsetindex = (Button) findViewById(R.id.ok_btn);//参数设置
	  buttongetindex = (Button) findViewById(R.id.buttonget_btn);//参数设置
	  
      buttonback.setOnClickListener(new View.OnClickListener() {
          
          @Override
          public void onClick(View v) {   
        	  
        	    MainActivity.frequencyflagback = true;
        		MainActivity.modify_pid_flag = 0;//切换到5hz
				int i= 0;
				boolean timer = true;
				while(timer){
				i++;
			    if(i>10){
				timer = false;

                Intent intent = getIntent();
				PIDIndex_Set.this.setResult(0, intent);
				PIDIndex_Set.this.finish();	
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
					    }
				}
          }
      });
      
      buttongetindex.setOnClickListener(new View.OnClickListener() {  
          public void onClick(View v) { 
        	  nameTextGypro_KP_RP.setText(MainActivity.Gyro_RP_KP); 
        	  nameTextGypro_KI_RP.setText(MainActivity.Gyro_RP_KI);
        	  nameTextGypro_KD_RP.setText(MainActivity.Gyro_RP_KD); 
        	  
        	  nameTextGypro_KP_Y.setText(MainActivity.Gyro_Y_KP); 
        	  nameTextGypro_KI_Y.setText(MainActivity.Gyro_Y_KI);  
        	  nameTextGypro_KD_Y.setText(MainActivity.Gyro_Y_KD);
        	  
        	  nameTextProp_KP_RP.setText(MainActivity.Prop_RP_KP); 
        	  nameTextProp_KI_RP.setText(MainActivity.Prop_RP_KI); 
        	
        	  nameTextProp_KP_Y.setText(MainActivity.Prop_Y_KP); 
        	  nameTextProp_KI_Y.setText(MainActivity.Prop_Y_KI);
        	  
          }
      });

      buttonsetindex.setOnClickListener(new View.OnClickListener() {
		  @SuppressWarnings("null")
		@Override
          public void onClick(View v) {
			  
			  if ( (nameTextGypro_KP_RP.length()==0)
        			  || (nameTextGypro_KI_RP.length()==0)
        			  || (nameTextGypro_KD_RP.length()==0)
        			  || (nameTextGypro_KP_Y.length()==0)
        			  || (nameTextGypro_KI_Y.length()==0)
        			  || (nameTextGypro_KD_Y.length()==0)
        			  || (nameTextProp_KP_RP.length()==0)
        			  || (nameTextProp_KI_RP.length()==0)
        			  || (nameTextProp_KP_Y.length()==0)
        			  || (nameTextProp_KI_Y.length()==0)){
        		    new  AlertDialog.Builder(PIDIndex_Set.this)    
        		
        		                   .setTitle("警告信息" )  
        		 
        		                   .setMessage("请确保参数已经输入完成！" )  
        		 
        		                   .setPositiveButton("确定" ,  null )  
        		
        		                   .show(); 
        		    return;
        		   
        	   }
			  float Gypro_KP_RP =  Float.parseFloat(nameTextGypro_KP_RP.getText().toString());
        	  float Gypro_KI_RP =  Float.parseFloat(nameTextGypro_KI_RP.getText().toString());        	 
        	  float Gypro_KD_RP =  Float.parseFloat(nameTextGypro_KD_RP.getText().toString());
        	  
        	  float Gypro_KP_Y =  Float.parseFloat(nameTextGypro_KP_Y.getText().toString());
        	  float Gypro_KI_Y =  Float.parseFloat(nameTextGypro_KI_Y.getText().toString());
        	  float Gypro_KD_Y =  Float.parseFloat(nameTextGypro_KD_Y.getText().toString());
        	  
        	  float Prop_KP_RP =  Float.parseFloat(nameTextProp_KP_RP.getText().toString());
        	  float Prop_KI_RP =  Float.parseFloat(nameTextProp_KI_RP.getText().toString());
	  
        	  float Prop_KP_Y =  Float.parseFloat(nameTextProp_KP_Y.getText().toString());
        	  float Prop_KI_Y =  Float.parseFloat(nameTextProp_KI_Y.getText().toString());
        	 
        	  SendSetPID(Gypro_KP_RP,Gypro_KI_RP,Gypro_KD_RP,Gypro_KP_Y,Gypro_KI_Y,Gypro_KD_Y,
        			  Prop_KP_RP,Prop_KI_RP,Prop_KP_Y,Prop_KI_Y);
        	  
        	  
          }
      });
      buttongetindex.performClick();
  }

  public boolean onOptionsItemSelected(MenuItem item)
  {
      // TODO Auto-generated method stub
      if(item.getItemId() == android.R.id.home)
      {
    	MainActivity.frequencyflagback = true;
   		MainActivity.modify_pid_flag = 0;//切换到5hz
          finish();
          overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
          return true;
      }
      return super.onOptionsItemSelected(item);
  }
public void SendSetPID(
		float gyro_kp_rp,float gyro_ki_rp,float gyro_kd_rp,
		float gyro_kp_y, float gyro_ki_y, float gyro_kd_y,
		float prop_kp_rp,float prop_ki_rp,
		float prop_kp_y, float prop_ki_y)
  {
	byte[] TxData = new byte[25];
  	short sTemp;   
    TxData[0] = (byte)0x8E;
  	TxData[1] = (byte)0xE8;
  	TxData[2] = (byte)0x16;
     	sTemp = (short)((gyro_kp_rp+0.005));
  	TxData[3] = (byte)((sTemp&0xFF00)>>8);
  	TxData[4] =(byte)(sTemp&0x00FF);
    	sTemp = (short)((gyro_ki_rp+0.005));
  	TxData[5] = (byte)((sTemp&0xFF00)>>8);
  	TxData[6] =(byte)(sTemp&0x00FF);
    	sTemp = (short)((gyro_kd_rp+0.005));
  	TxData[7] = (byte)((sTemp&0xFF00)>>8);
  	TxData[8] =(byte)(sTemp&0x00FF);

    	sTemp = (short)(gyro_kp_y );
  	TxData[9] = (byte)((sTemp&0xFF00)>>8);
  	TxData[10]=(byte)(sTemp&0x00FF);
    	sTemp = (short)(gyro_ki_y);
  	TxData[11]= (byte)((sTemp&0xFF00)>>8);
  	TxData[12]=(byte)(sTemp&0x00FF);
    	sTemp = (short)(gyro_kd_y);
  	TxData[13]= (byte)((sTemp&0xFF00)>>8);
  	TxData[14]=(byte)(sTemp&0x00FF);
	
    	sTemp = (short)(prop_kp_rp * 10.0);
  	TxData[15]=(byte)((sTemp&0xFF00)>>8);
  	TxData[16]=(byte)(sTemp&0x00FF);
    	sTemp = (short)(prop_ki_rp * 10.0);
  	TxData[17]= (byte)((sTemp&0xFF00)>>8);
  	TxData[18]=(byte)(sTemp&0x00FF);
    	sTemp =(short)((prop_kp_y+0.005) * 10.0);
  	TxData[19]=(byte)((sTemp&0xFF00)>>8);
  	TxData[20]=(byte)(sTemp&0x00FF);
    	sTemp =(short)(prop_ki_y * 10.0);
  	TxData[21]=(byte)((sTemp&0xFF00)>>8);
  	TxData[22]=(byte)(sTemp&0x00FF);

  	CalcBcc(TxData,22);
  	for(int i = 23; i<25;i++)
  	{
  		if(TxData[i] < 0)
  		{
  			TxData[i] = (byte) (TxData[i] + 256);
  		}else
  		{	
  			TxData[i] = TxData[i];
  		}
  	}
  	getpidindex(TxData);
  	MainActivity.sendnulldata =false;
  	MainActivity.Pidflag = true;
  }
 public static void Changefrequency(int frequency)
 {
 	byte TxData[]=new byte[6];
 	
 	TxData[0] = (byte) 0x5A;
 	TxData[1] = (byte) 0xA5;
 	TxData[2] = (byte) 0x05;
 	TxData[3] = (byte)(frequency);
 	CalcBcc(TxData,3);
 	  //MainActivity.mService.writeRXCharacteristic(TxData);
 	MainActivity.changefrequence = TxData.clone();
 }
  public void getpidindex(byte[] TxData)
  {
	  MainActivity.value[0]  = TxData[0];
	  MainActivity.value[1]  = TxData[1];
	  MainActivity.value[2]  = TxData[2];
	  MainActivity.value[3]  = TxData[3];
	  MainActivity.value[4]  = TxData[4];
	  MainActivity.value[5]  = TxData[5];
	  MainActivity.value[6]  = TxData[6];
	  MainActivity.value[7]  = TxData[8];
	  MainActivity.value[8]  = TxData[9];
	  MainActivity.value[9]  = TxData[10];
	  MainActivity.value[10] = TxData[11];
	  MainActivity.value[11] = TxData[12];
	  MainActivity.value[12] = TxData[14];
	  MainActivity.value[13] = TxData[16];
	  MainActivity.value[14] = TxData[18];
	  MainActivity.value[15] = TxData[20];
	  MainActivity.value[16] = TxData[22];
	  MainActivity.value[17] = TxData[23];
	  MainActivity.value[18] = TxData[24];
	
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
