/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nordicsemi.nrfUARTv2;

import java.util.Timer;
import java.util.TimerTask;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
public class modeltype extends Activity{
	    static short Index_FlightQuad;//几旋翼
		static short Index_YKQType;//遥控器类型
		static short Index_BatteryType;//电池类型
		static short Index_huizhong;//遥控器回中
		static short Index_daisu;//怠速设置
		static TextView textViewmodelseted;//机型选择显示
		static TextView textViewykqseted; //遥控器选择显示
		static TextView textViewvolseted; //电池选择显示
		int DataSelect_Quade;
		private NumberSeekBar pb;
		protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.modetype);
		  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		  
		  ActionBar mActionBar=getActionBar();
		  mActionBar.setHomeButtonEnabled(true);
		  mActionBar.setDisplayHomeAsUpEnabled(true);
		  mActionBar.setDisplayShowHomeEnabled(false);  
		  mActionBar.setDisplayShowCustomEnabled(true);
		  mActionBar.setDisplayShowTitleEnabled(true); 
		  mActionBar.setTitle("机型设置");
		  
          Button  buttonback;
          Button  buttonindexset;
          Button  buttonmodelselected;
          Button  buttondianchi;
          Button  buttonykqselected;
          
		  //声明spinner对象  
          textViewykqseted = (TextView) findViewById(R.id.textViewykqset);
          textViewvolseted = (TextView) findViewById(R.id.textViewdianchiset); 
          textViewmodelseted = (TextView) findViewById(R.id.textViewmodelset);
		  Spinner spinnerhuizhong;
		  buttonback= (Button) findViewById(R.id.buttonback);
		  buttonindexset= (Button) findViewById(R.id.buttonindexset);
		  buttonmodelselected = (Button) findViewById(R.id.buttonmodelselect);
		  buttonykqselected = (Button) findViewById(R.id.buttonykqselect);
		  buttondianchi = (Button) findViewById(R.id.buttondianchi);
		  spinnerhuizhong=(Spinner) findViewById(R.id.spinnerhuizhong);
		  
		  pb = (NumberSeekBar)findViewById(R.id.bar0);
		  init();
	      start();
		  final String arrjixing[]=new String[]{  
	                "四旋翼叉字顺时针",  
	                "四旋翼叉字逆时针",  
	                "四旋翼十字顺时针",  
	                "四旋翼十字逆时针",
	                
	                "六旋翼V字顺时针",  
	                "六旋翼V字逆时针",  
	                "六旋翼一字顺时针",  
	                "六旋翼一字逆时针", 
	                
	                "八旋翼叉字顺时针",  
	                "八旋翼叉字逆时针",  
	                "八旋翼十字顺时针",  
	                "八旋翼十字逆时针",
	                
	                "共轴叉字顺时针",  
	                "共轴叉字逆时针"  
	        };  
		  final String arrdianchi[]=new String[]{  
				  "2S",
				  "3S",
	              "4S",  
	              "6S" 
	        };  
		  final String arryaokongqi[]=new String[]{  
	                "14SG", 
	                "其他" 
	              
	        }; 
		  final String arrhuizhong[]=new String[]{  
	                "                                 是", 
	                "                                 否" 
	              
	        }; 
		  //==============================机型=====================================//
//		  ArrayAdapter<String> arrayAdaptermodel = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrjixing);  
//		  //设置样式
//		  arrayAdaptermodel.setDropDownViewResource(android.R.layout.simple_list_item_single_choice );
//	      //加载适配器
//		  spinnermodel.setAdapter(arrayAdaptermodel);
//		  //==============================电池=====================================//
//		  ArrayAdapter<String> arrayAdapterdianchi = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrdianchi);  
//		  //设置样式
//		  arrayAdapterdianchi.setDropDownViewResource(android.R.layout.simple_list_item_single_choice );
//	      //加载适配器
//		  spinnerdianchi.setAdapter(arrayAdapterdianchi);
//		 //==============================遥控器=====================================//
//		  ArrayAdapter<String> arrayAdapteryaokongqi = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arryaokongqi);  
//		  //设置样式
//		  arrayAdapteryaokongqi.setDropDownViewResource(android.R.layout.simple_list_item_single_choice );
//	      //加载适配器
//		  spinneryaokongqi.setAdapter(arrayAdapteryaokongqi);
		 
		  //==============================遥控回中=====================================//
		  ArrayAdapter<String> arrayAdapterhuizhong = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrhuizhong);  
		  //设置样式
		  arrayAdapterhuizhong.setDropDownViewResource(android.R.layout.simple_list_item_single_choice );
	      //加载适配器
		  spinnerhuizhong.setAdapter(arrayAdapterhuizhong);
		  
		 //spinner设置监听器  
		 // spinnermodel.setOnItemSelectedListener((OnItemSelectedListener) this);  
		  buttonback.setOnClickListener(new View.OnClickListener() {
	          
	          @Override
	          public void onClick(View v) {
//	              Intent intent = new Intent();
//	              intent.setClass(PIDIndex_Set.this, MainActivity.class);
	              //startActivityForResult(intent, 0);
	        	  
	                Intent intent = getIntent();
	                modeltype.this.setResult(0, intent);
	                modeltype.this.finish();
	                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);	
	          }
	      });
		  //============================机型设置==============================//
		  buttonmodelselected.setOnClickListener(new View.OnClickListener() {
	          
	          @Override
	          public void onClick(View v) {

	        		Intent wneIntent = new Intent(modeltype.this, flightmodel.class);	
					startActivity(wneIntent);
					overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);		
	          }
	      });
		  //============================遥控器设置==============================//
		  buttonykqselected.setOnClickListener(new View.OnClickListener() {
	          
	          @Override
	          public void onClick(View v) {

	        		Intent yneIntent = new Intent(modeltype.this, flightykqset.class);	
					startActivity(yneIntent);
					overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);		
	          }
	      });
		  //============================电池设置==============================//
		  buttondianchi.setOnClickListener(new View.OnClickListener() {
	          
	          @Override
	          public void onClick(View v) {

	        		Intent wneIntent = new Intent(modeltype.this, flightdcset.class);	
					startActivityForResult(wneIntent, 3);
					overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);		
	          }
	      });
//		  spinnermodel.setOnItemSelectedListener(new OnItemSelectedListener() {
//			    @Override
//			    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//			    	@SuppressWarnings("unused")
//					String cardNumber = arrjixing[arg2];
//			    	switch(arg2)
//			    	{
//			    	case 0:
//			    		Index_FlightQuad = 1;
//			    		break;
//			    	case 1:
//			    		Index_FlightQuad = 2;
//			    		break;
//			    	case 2:
//			    		Index_FlightQuad = 3;
//			    		break;
//			    	case 3:
//			    		Index_FlightQuad = 4;
//			    		break;
//			    	case 4:
//			    		Index_FlightQuad = 5;
//			    		break;
//			    	case 5:
//			    		Index_FlightQuad = 6;
//			    		break;
//			    	case 6:
//			    		Index_FlightQuad = 7;
//			    		break;
//			    	case 7:
//			    		Index_FlightQuad = 8;
//			    		break;
//			    	case 8:
//			    		Index_FlightQuad = 9;
//			    		break;
//			    	case 9:
//			    		Index_FlightQuad = 10;
//			    		break;
//			    	case 10:
//			    		Index_FlightQuad = 11;
//			    		break;
//			    	case 11:
//			    		Index_FlightQuad = 12;
//			    		break;
//			    	case 12:
//			    		Index_FlightQuad = 13;
//			    		break;
//			    	case 13:
//			    		Index_FlightQuad = 14;
//			    		break;
//			    	}
//			    }
//				@Override
//				public void onNothingSelected(AdapterView<?> arg0) {
//					// TODO 自动生成的方法存根
//				}
//			});
//		  
//		  spinnerdianchi.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
//			    @Override
//			    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//			    	@SuppressWarnings("unused")
//					String cardNumber = arrdianchi[arg2];
//			    	switch(arg2)
//			    	{
//			    	case 0:
//			    		Index_BatteryType = 1;//2S
//			    		break;
//			    	case 1:
//			    		Index_BatteryType = 3;//3S
//			    		break;
//			    	case 2:
//			    		Index_BatteryType = 2;//4S
//			    		break;
//			    	case 3:
//			    		Index_BatteryType = 0;//6S
//			    		break;
//			    	}
//			    }
//
//				@Override
//				public void onNothingSelected(AdapterView<?> arg0) {
//					// TODO 自动生成的方法存根
//					
//				}
//			});
//		  
//		  spinneryaokongqi.setOnItemSelectedListener(new OnItemSelectedListener() {
//			    @Override
//			    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//			    	@SuppressWarnings("unused")
//					String cardNumber = arryaokongqi[arg2];
//			    	switch(arg2)
//			    	{
//			    	case 0:
//			    		Index_YKQType = 0;
//			    		break;
//			    	case 1:
//			    		Index_YKQType = 1;
//			    		break;
//			    	}
//			    }
//				@Override
//				public void onNothingSelected(AdapterView<?> arg0) {
//					// TODO 自动生成的方法存根
//				}
//			});
		  spinnerhuizhong.setOnItemSelectedListener(new OnItemSelectedListener() {
			    @Override
			    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			    	@SuppressWarnings("unused")
					String cardNumber = arrhuizhong[arg2];
			    	switch(arg2)
			    	{
			    	case 0:
			    		Index_huizhong = 0;//遥控器回中
			    		break;
			    	case 1:
			    		Index_huizhong = 1;//遥控器不回中
			    		break;
			    	}
			    }
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO 自动生成的方法存根
				}
			});
		  buttonindexset.setOnClickListener(new View.OnClickListener() {     
	          @Override
	          public void onClick(View v) {
	        	  DataSelect_Quade =  (((short)Index_FlightQuad<<8) & 0xff00) 
	        			  |(((short)Index_huizhong<<7) & 0x0080) 
	        			  | (((short)Index_BatteryType<<1) & 0x0006) 
	        			  | (((short)Index_YKQType) & 0x0001);//数组要进行打包
	        	  
	        	  SendQudeSet(DataSelect_Quade,Index_daisu);
	        	  MainActivity.flightmodelflag = true;
	        	  MainActivity.sendnulldata = false;

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
	  void SendQudeSet(int DataSelect_QUADE,int Indexdaisu)//添加一个怠速值   以参数的形式5--25   遥控器是否回中 回中：0 ，不会中 ：1
		{
			int sTemp;
			sTemp=DataSelect_QUADE;
			byte[] TxData = new byte[9];
			TxData[0] = (byte)0x5E;
			TxData[1] = (byte)0xE5;
			TxData[2] = (byte)0x14;
			TxData[3] = (byte)((sTemp&0xFF00)>>8);
			TxData[4]=  (byte)(sTemp&0x00FF);
			sTemp = Indexdaisu;
			TxData[5] = (byte)(sTemp&0x00FF);
			TxData[6] = (byte)0x02;
			CalcBcc(TxData,6);
			MainActivity.flightmodel = TxData.clone();
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
	  private void init() {
	        pb.setTextSize(20);// 璁剧疆瀛椾綋澶у皬
	        pb.setTextColor(Color.WHITE);// 棰滆壊
	        pb.setMyPadding(10, 10, 10, 10);// 璁剧疆padding 璋冪敤setpadding浼氭棤鏁�
	        pb.setImagePadding(0, 1);// 鍙互涓嶈缃�
	        pb.setTextPadding(0, 0);// 鍙互涓嶈缃�
	
	    }
	    
	    private void start() {
	        TimerTask tt = new TimerTask() {
	            public void run() {
	             
	            }
	        };
	        Timer timer = new Timer();
	        timer.schedule(tt, 1000, 2000);
	    }
	    
	}