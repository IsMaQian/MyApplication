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
import java.text.DateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nordicsemi.buff.GetIntData;
import com.nordicsemi.buff.PackageType.packageType;
import com.nordicsemi.data.AcceleratedSpeed;
import com.nordicsemi.data.GPSData;
import com.nordicsemi.data.GeomagnetismData;
import com.nordicsemi.data.IMUData;
import com.nordicsemi.data.MotorData;
import com.nordicsemi.data.PIDData;
import com.nordicsemi.data.RemoteControlData;
import com.nordicsemi.data.SpeedData;
import com.nordicsemi.data.StartCloseData;
import com.nordicsemi.data.StatusData;
import com.nordicsemi.data.WayPointData;
import com.nordicsemi.data.XBEEData;

public class MainActivity extends Activity implements RadioGroup.OnCheckedChangeListener {
    private static final int REQUEST_SELECT_DEVICE = 1;
    private static final int REQUEST_SELECT_PID = 3;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int UART_PROFILE_READY = 10;
    public static  final String TAG = "nRFUART";
    private static final int UART_PROFILE_CONNECTED = 20;
    private static final int UART_PROFILE_DISCONNECTED = 21;
    private static final int STATE_OFF = 10;
    TextView mRemoteRssiVal;
    
    RadioGroup mRg;
    private int mState = UART_PROFILE_DISCONNECTED;
    static UartService mService = null;
    private BluetoothDevice mDevice = null;
    private BluetoothAdapter mBtAdapter = null;//蓝牙适配器对象
    private ListView messageListView;
    private ArrayAdapter<String> listAdapter;
    private Button btnConnectDisconnect,btnSend;
    private EditText edtMessage;
    
    ///新添加的变量
	Handler handler;
	static boolean isConnecting = false;
	ClientThread clientThread;
	int datalength;
	static int gloalV; // 全局性的
	static byte[] value;
	static byte[] changefrequence;
	static byte[] WorkindexData;
	static byte[] flightmodel;
	static byte[] flightnulldata;
	public static boolean Pidflag=false;
	public static boolean Pidflagsuccess=false; 
	public static boolean frequencyflag = false;
	public static boolean frequencyflagback = false;
	public static boolean workindexsend = false;
	public static boolean flightmodelflag = false;
	public boolean changefrencecyshow = false;
	public boolean workindexshow = false;
	public boolean pidsetshow = false;
	public boolean modelsetshow = false;
	public static boolean sendnulldata = true;
	boolean RxDataflag=false;//解码标志位
	public static  int modify_pid_flag = 0;
	int stop_frequency_flag = 0;
	private Button PIDSet;
	private Button FlightTypeSet;
	private Button WorkIndexSet;
	private Button Imu_State;
//	static TextView Gyro_state;
//	static TextView Acc_state;
//	static TextView Mag_state;
//	static TextView Zitai_state;
//	static TextView Voltage_state;
	static int Imu_Check_Attitude ;
	static int Imu_Check_Acc;
	static int Imu_Check_Gyro;
	static int Imu_Check_Magcalib;
	byte cRxDate;
	int nRxDateCount = 0;
	int nPacketSize = 0;
	boolean setScreenflag =false;//设置屏幕亮度调节模式
	public static boolean Screenflagchange = false;
	
	private String Tag = "MainActivity";
	byte[] Date = new byte[200];
	byte[] cIndexHeader = new byte[3];
	byte[] buffer = new byte[2048];
	byte[] ss = new byte[200];
	
	public static  int tmpInt;
	public static short ledcontrolflag;
	
	/**IMU数据*/
	static String strRoll;//滚转角
	static String strPitch;//俯仰角
	static String strYaw;//航向角
	static String strRollRate;//陀螺仪Rollrate
	static String strPichRate;//陀螺仪Pitchrate
	static String strYawRate;//陀螺仪Yawrate
	 
	/**加速度数据*/
	static String strAccX;//x方向加速度
	static String strAccY;//y方向加速度
	static String strAccZ;//z方向加速度
	
	/**地磁数据*/
	static String strGeoX;
	static String strGeoY;
	static String strGeoZ;
	
	/**遥控器数据*/
	static String strReRoll;
	static String strRePitch;
	static String strReYaw;	
	static String strReThrottle;
	
	/**开关数据*/
	static String strSW1;
	static String strSW2;
	static String strSW3;
	static String strSW4;
	
	/**路点数据*/
	static String strwayID;
	static String strwayCount;
	static String strwayIndex;
	static String strwaypenshaispeed = "0";
	static String strwaypenshaijuli = "0";
	static String strwaypenshaibanjing = "0";
	static String strwaypenshaigaodu = "0";
	
	/**电机数据*/
	static String strMotor_Front;
	static String strMotor_Back;
	static String strMotor_Left;
	static String strMotor_Right;
	static String strMotor_X;
	static String strMotor_Y;
	
	/**速度数据*/
	static String strLateral;
	static String strLongitudinal;
	static String strAbout;
	
	/**GPS数据*/
	static String strLatitude;
	static String strLongitude;
	static String strStarCount;
	static String strHight;
	
	/**状态数据*/
	static String strFlyStatus;
	static String strVoltage;
	static String strVoltage1;
	static String strSendFlag;
	static String strSensorStatus ;
	static String strCommStatus;
	static String strPhotoFlag;
	
	/**XBEE通断检测数据*/
	static String strXBEE;
	
	/**PID数据*/
	public static String Gyro_RP_KP = "0";
	public static String Gyro_RP_KI = "0";
	public static String Gyro_RP_KD = "0";
	public static String Gyro_Y_KP = "0";
	public static String Gyro_Y_KI = "0";
	public static String Gyro_Y_KD = "0";
	public static String Prop_RP_KP = "0";
	public static String Prop_RP_KI = "0";
	public static String Prop_Y_KP = "0";
	public static String Prop_Y_KI = "0";
	static double Change_Pid_Send_Flag;

//	接收消息
	packageType dataType;
	GetIntData getInt;
	/**IMU数据*/
	IMUData IMU; 
	/**加速度数据*/
	AcceleratedSpeed accspeed;
	/**地磁数据*/
	GeomagnetismData geoData;
	/**遥控器数据*/
	RemoteControlData remoteData;
	/**开关数据*/
	StartCloseData closeData;    
	/**路点数据*/
	WayPointData wayPointData;   
	/**电机数据*/
	MotorData motorData;         
	/**速度数据*/
	SpeedData speedData;         
	/**GPS数据*/
	GPSData gpsData;  
	/**状态数据*/
	StatusData statusData;       
	/**XBEE通断检测数据*/
	XBEEData xbeeData; 
	
	PIDData  piddata;
    
	PIDIndex_Set pidindex_set = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
      //  circleSeekBar = (SeekBar) findViewById(R.id.seekbar);
//        circleSeekBar.setProgressMax(255);
//        circleSeekBar.setProgress(100);
//        circleSeekBar.setProgressFrontColor(Color.RED);
//        circleSeekBar.setIsShowProgressText(true);
//        circleSeekBar.setProgressTextSize(30);
//        circleSeekBar.setProgressTextColor(Color.GREEN);
//        circleSeekBar.setProgressWidth(40);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //绘制矩形边框
        GradientDrawable drawable = new GradientDrawable();  
        drawable.setShape(GradientDrawable.RECTANGLE); // 画框  RECTANGLE 
        drawable.setStroke(3, Color.rgb(105, 105, 105)); // 边框粗细及颜色  
        drawable.setCornerRadius(20.0f); // 边框圆角半径
        drawable.setColor(0x00000000); // 边框内部颜色  
                   
    
        value = new byte[19];
        changefrequence = new byte[5];
        WorkindexData = new byte[14];
        flightmodel = new byte[9]; 
        flightnulldata = new byte[6];
        new Thread(new ThreadShow()).start();
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBtAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
       // messageListView = (ListView) findViewById(R.id.listMessage);//接收去显示框对象
        listAdapter = new ArrayAdapter<String>(this, R.layout.message_detail);//蓝牙设备显示列表对象
       // messageListView.setAdapter(listAdapter);
        //messageListView.setDivider(null);button_IMUState
        btnConnectDisconnect=(Button) findViewById(R.id.btn_select);//蓝牙设备连接按钮对象
        btnConnectDisconnect.setBackgroundDrawable(drawable);//setBackgroundDrawable(drawable); // 设置背景（效果就是有边框及底色）
        service_init();
        Imu_State=(Button)findViewById(R.id.button_IMUState);//
        PIDSet=(Button)findViewById(R.id.button_PID);
        WorkIndexSet=(Button)findViewById(R.id.button_WorkIndex);
        FlightTypeSet=(Button)findViewById(R.id.button_DroneType);

       
//        Gyro_state =(TextView)findViewById(R.id.textViewGYRO_State);  
//	    Acc_state =(TextView)findViewById(R.id.textViewACC_State);
//	    Mag_state =(TextView)findViewById(R.id.textViewMAG_State);
//	    Zitai_state =(TextView)findViewById(R.id.textViewzitai_State);
//	    Voltage_state =(TextView)findViewById(R.id.textViewV_State);
        // Handler Disconnect & Connect button扫描并连接蓝牙设备
        btnConnectDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBtAdapter.isEnabled()) {
                    Log.i(TAG, "onClick - BT not enabled yet");
                    Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
                }
                else {
                	if (btnConnectDisconnect.getText().equals("连接")){
            			Intent newIntent = new Intent(MainActivity.this, DeviceListActivity.class);
            			startActivityForResult(newIntent, REQUEST_SELECT_DEVICE);
            			setScreenflag = true;
        			} else {
        				//Disconnect button pressed
        				if (mDevice!=null)
        				{
        					mService.disconnect();		
        					//setScreenMode(0);
        				}
        			}
                }
            }
        });
     // Handler Disconnect & Connect button扫描并连接蓝牙设备
        //=========================启动传感器状态显示===========================//
        Imu_State.setOnClickListener(new View.OnClickListener(){ 
            @Override 
            public void onClick(View v){ 
  			Intent wIntent = new Intent(MainActivity.this, ImuState.class);
			startActivityForResult(wIntent, REQUEST_SELECT_PID);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);	
            } 
        });
        //=========================启动pid设置===========================//
        PIDSet.setOnClickListener(new View.OnClickListener(){ 
            @Override 
            public void onClick(View v){ 
			  if(modify_pid_flag==0)
				{
					modify_pid_flag=1;	
					frequencyflag = true;
					Intent neIntent = new Intent(MainActivity.this, PIDIndex_Set.class);	
					startActivityForResult(neIntent, REQUEST_SELECT_PID);
					overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);							
				}
            } 
        });
        //=========================启动作业参数设置===========================//
        WorkIndexSet.setOnClickListener(new View.OnClickListener(){ 
            @Override 
            public void onClick(View v){ 	
  			Intent neIntent = new Intent(MainActivity.this, workindex_set.class);
			startActivityForResult(neIntent, REQUEST_SELECT_PID);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);	
            } 
        });
      //=========================启动机型设置===========================//
        FlightTypeSet.setOnClickListener(new View.OnClickListener(){ 
            @Override 
            public void onClick(View v){ 
  			Intent nIntent = new Intent(MainActivity.this, modeltype.class);
			startActivityForResult(nIntent, REQUEST_SELECT_PID);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);	
            } 
        });
 
    }
  
    class ThreadShow implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				try {
					
					//+++++++++++++++++++++++++自检信息++++++++++++++++++++++++++++++++++++//
					if(strSensorStatus != null){
						Imu_Check_Attitude = (Integer.parseInt(strSensorStatus)>>4) & 0x0003;
						Imu_Check_Acc = (Integer.parseInt(strSensorStatus)>>6) & 0x0003;
						Imu_Check_Gyro =(Integer.parseInt(strSensorStatus)>>8) & 0x0003;
						Imu_Check_Magcalib =(Integer.parseInt(strSensorStatus)>>15) & 0x0001;
					}
					else {
						Imu_Check_Attitude = 5;
						Imu_Check_Acc = 5;
						Imu_Check_Gyro = 5;
						Imu_Check_Magcalib = 5;
						}
						//+++++++++++++++++++++++++自检信息++++++++++++++++++++++++++++++++++++//
					//============================切换到5Hz================================//
					if(frequencyflagback &&  modify_pid_flag==0){//&&  modify_pid_flag==1
						//Thread.sleep(1000);
						PIDIndex_Set.Changefrequency(2);
						mService.writeRXCharacteristic(changefrequence);
						if (frequencyflagback && modify_pid_flag==0){
							//切换成功
							changefrencecyshow = true;
							frequencyflagback = false;
							for(int i=0;i<10;i++)
							{
								SendNullData();
								mService.writeRXCharacteristic(flightnulldata);
							}
						}
					}
					//============================切换到50Hz================================//
					if(frequencyflag && modify_pid_flag==1)
					{
						Thread.sleep(1000);
						PIDIndex_Set.Changefrequency(1);
						mService.writeRXCharacteristic(changefrequence);
						if (frequencyflag && modify_pid_flag==1 )
						{
							//切换成功
							changefrencecyshow = true;
							frequencyflag = false;
							for(int i=0;i<10;i++)
							{
								SendNullData();
								mService.writeRXCharacteristic(flightnulldata);
							}
						}
					}
					
					//============================PID参数发送================================//
					if(Pidflag && ((int)Change_Pid_Send_Flag !=22)){//
						Thread.sleep(20);						
                       	mService.writeRXCharacteristic(value); 
                       	if (Pidflag && ((int)Change_Pid_Send_Flag ==22) ){//
                        	//发送成功
                       		pidsetshow = true;
    						Pidflag = false;
    						sendnulldata = true;
    						for(int i=0;i<10;i++)
							{
								SendNullData();
								mService.writeRXCharacteristic(flightnulldata);
							}
    					} 
                    }
					//============================作业参数发送================================//
					if(workindexsend && ((int)statusData.getSendFlag() !=15)){					
						Thread.sleep(20);	
						mService.writeRXCharacteristic(WorkindexData);
						if (workindexsend && ((int)statusData.getSendFlag() ==15) ){  //
							//设置成功
							workindexshow = true;
							workindexsend = false;
							sendnulldata = true;
							for(int i=0;i<10;i++)
							{
								SendNullData();
								mService.writeRXCharacteristic(flightnulldata);
							}
						}
					}
					//============================机型参数发送================================//
					if(flightmodelflag  && ((int)statusData.getSendFlag() !=20)){// 
						Thread.sleep(20);
						mService.writeRXCharacteristic(flightmodel); 
						if (flightmodelflag && ((int)statusData.getSendFlag() ==20)){  //  
							flightmodelflag = false;
							modelsetshow = true;
							sendnulldata = true;
							for(int i=0;i<10;i++)
							{
								Thread.sleep(20);
								SendNullData();
								mService.writeRXCharacteristic(flightnulldata);
							}
						}
					}
				} catch (Exception e) {	
					System.out.println("thread error...");
				}
			}
		}
	}
    ///++++++++++++++++++++++++++++++++++++++++++++++++++++++++++///
    //  UART service connected/disconnected  客户端绑定到一个service 
    //  系统调用onServiceConnected来传送在service的onBind()中返回的IBinder．
    //  调用bindService()，传给它ServiceConnection的实现
    //  当系统调用你的onServiceConnected()方法时，你就可以使用接口定义的方法们开始调用service了．
    //  要与service断开连接，调用unbindService()．
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
        		mService = ((UartService.LocalBinder) rawBinder).getService();
        		Log.d(TAG, "onServiceConnected mService= " + mService);
        		if(!mService.initialize()) {
                    Log.e(TAG, "Unable to initialize Bluetooth");
                    finish();
                }
        }
        public void onServiceDisconnected(ComponentName classname) {
       //     mService.disconnect(mDevice);
        		mService = null;
        }
    };
    private Handler mHandler = new Handler() {
        @Override
        //Handler events that received from UART service 
        public void handleMessage(Message msg) {
        }
    };
    ///在Android系统中，广播体现在方方面面，例如当开机完成后系统会产生一条广播，接收到这条广播就能实现开机启动服务的功能；
    ///要创建自己的BroadcastReceiver对象，我们需要继承android.content.BroadcastReceiver，并实现其onReceive方法。
    ///在onReceive方法内，我们可以获取随广播而来的Intent中的数据，这非常重要，就像无线电一样，包含很多有用的信息。
    ///在创建完我们的BroadcastReceiver之后，还不能够使它进入工作状态，我们需要为它注册一个指定的广播地址。没有注册广播地址的BroadcastReceiver就像一个缺少选台按钮的收音机，虽然功能俱备，但也无法收到电台的信号。
    ///静态注册是在AndroidManifest.xml文件中配置的，我们就来为MyReceiver注册一个广播地址 配置了以上信息之后，只要是android.intent.action.MY_BROADCAST这个地址的广播，MyReceiver都能够接收的到。
    //注意，这种方式的注册是常驻型的，也就是说当应用关闭后，如果有广播信息传来，MyReceiver也会被系统调用而自动运行。

    //动态注册需要在代码中动态的指定广播地址并注册，通常我们是在Activity或Service注册一个广播
    private final BroadcastReceiver UARTStatusChangeReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            final Intent mIntent = intent;
           //*********************//
            if (action.equals(UartService.ACTION_GATT_CONNECTED)) {
            	 runOnUiThread(new Runnable() {
                     public void run() {
                         	String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                             Log.d(TAG, "UART_CONNECT_MSG");
                             btnConnectDisconnect.setText("断开");
                             ((TextView) findViewById(R.id.deviceName)).setText(mDevice.getName()+ " - 设备已连接");
                            if(setScreenflag)
                             {
                            	 setScreenMode(0);
                            	 setScreenflag = false;
           
                             }
                            // listAdapter.add("["+currentDateTimeString+"] 已连接到设备: "+ mDevice.getName());
                        	// messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);
                             mState = UART_PROFILE_CONNECTED;
                             isConnecting = true;
                     }
            	 });
            }
          //*********************//
            if (action.equals(UartService.ACTION_GATT_DISCONNECTED)) {
            	 runOnUiThread(new Runnable() {
                     public void run() {
                    	 	 String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                             Log.d(TAG, "UART_DISCONNECT_MSG");
                             btnConnectDisconnect.setText("连接");
                             ((TextView) findViewById(R.id.deviceName)).setText("设备未连接");
                             listAdapter.add("["+currentDateTimeString+"] 已经断开设备: "+ mDevice.getName());
                             mState = UART_PROFILE_DISCONNECTED;
                             Pidflag = false;
                             flightmodelflag = false;
                             workindexsend = false;
                             frequencyflagback = false;
                             frequencyflag = false;
                             mService.close();
                     }
                 });
            }
          //*********************//
            if (action.equals(UartService.ACTION_GATT_SERVICES_DISCOVERED)) {
             	 mService.enableTXNotification();
            }
          //*********************//
//+++++++++++++++++++++++++发送默认值++++++++++++++++++++++++++++++++++++//
//			if(sendnulldata && action.equals(UartService.ACTION_DATA_AVAILABLE))
//			{
//				SendNullData();
//				mService.writeRXCharacteristic(flightnulldata);
//			}
            ///++++++++++++++++++++++数据接收显示+++++++++++++++++++++++++++++++++++++//
            if (action.equals(UartService.ACTION_DATA_AVAILABLE)) {
            	
            	final byte[] txValue = intent.getByteArrayExtra(UartService.EXTRA_DATA);

            ///++++++++++++++++++++++数据解码+++++++++++++++++++++++++++++++++++++//
     					
     						datalength = txValue.length;
     						System.out.println(datalength);
     						 //content = br.read(buffer);
     						for(int i=0;i<txValue.length;i++)
     						{
     							cRxDate = txValue[i];
     							if(RxDataflag)
     							{			
     								Date[nRxDateCount++]=cRxDate;
     								if(nRxDateCount>=nPacketSize){
     									RxDataflag=false;
     									//将获得的负数数据转成正数并赋给ss数组
     									for (int x = 0; x <nPacketSize; x++) 
     									{
     										if (Date[x] < 0) 
     										{
     											ss[x] = (byte) (Date[x] + 256);
     										} else 
     										{
     											ss[x] = (byte) Date[x];
     										}
     									}
     									//计算校验和并判断服务器传输的数据是否正确
     									int Bcc2 = 0;
     									int Bcc = 0;
     									for(int k=2;k<nPacketSize-2;k++)
     									{
     										Bcc+=((short)ss[k]&0x00ff);
     									}
     										
     									Bcc2=(short)(((ss[nPacketSize-2]<<8)&0xFF00)|(ss[nPacketSize-1])&0x00FF);
     									if(Bcc==Bcc2)
     									{
     										Message msg=new Message();
     										msg.what=0x123;
//     										根据date【2】的不同输出各数据
     									if(modify_pid_flag == 0){
     									switch (Date[2]) {
     									case 1:
     										dataType = packageType.PtIMU;
     										IMU = new IMUData(ss, 3);//3-14
     										strRoll = String.valueOf(IMU.getIRoll());
     									    strPitch = String.valueOf(IMU.getIPitch());
     										strYaw = String.valueOf(IMU.getIYaw());
     										strRollRate = String.valueOf(IMU.getIRollRate());
     										strPichRate = String.valueOf(IMU.getIPitchRate());
     										strYawRate = String.valueOf(IMU.getIYawRate());
     										
     										dataType = packageType.PtAcceleratedSpeed;
     										accspeed = new AcceleratedSpeed(ss, 15);//15-20
     										strAccX = String.valueOf(accspeed.getX());
     										strAccY = String.valueOf(accspeed.getY());
     										strAccZ = String.valueOf(accspeed.getZ());
     										
     										dataType = packageType.PtGeomagnetism;
     										geoData = new GeomagnetismData(ss, 21);//21-26
     										strGeoX = String.valueOf(geoData.getX());
     										strGeoY = String.valueOf(geoData.getY());
     										strGeoZ = String.valueOf(geoData.getZ());
     										
     										dataType = packageType.PtRemoteControl;//27-34
     										remoteData = new RemoteControlData(ss, 27);
     										strReRoll = String.valueOf(remoteData.getRoll());
     										strRePitch=String.valueOf(remoteData.getPitch());
     										strReYaw=String.valueOf(remoteData.getYaw());	
     										strReThrottle=String.valueOf(remoteData.getThrottle());
     										
     										dataType = packageType.PtStartClose;//35-42
     										closeData = new StartCloseData(ss, 35);
     										strSW1=String.valueOf(closeData.getSW1());
     										strSW2=String.valueOf(closeData.getSW2());
     										strSW3=String.valueOf(closeData.getSW3());
     										strSW4=String.valueOf(closeData.getSW4());
     										
     										dataType = packageType.PtWayPoint;//43-56
     										wayPointData = new WayPointData(ss, 43);
     										strwayID=String.valueOf(wayPointData.getMotor_Front());
     										strwayCount=String.valueOf(wayPointData.getWayCount());
     										strwayIndex=String.valueOf(wayPointData.getWayIndex());
     										
     										strwaypenshaispeed=String.valueOf(wayPointData.getWaypenshai_speed());
     										strwaypenshaijuli=String.valueOf(wayPointData.getWaypenshai_juli());
     										strwaypenshaibanjing=String.valueOf(wayPointData.getWaypenshai_banjing());
     										strwaypenshaigaodu=String.valueOf(wayPointData.getWaypenshai_gaodu());
     										
     										dataType = packageType.PtMotor;
     										motorData = new MotorData(ss, 57);//57-68
     										strMotor_Front=String.valueOf(motorData.getMotor_Front());
     										strMotor_Back=String.valueOf(motorData.getMotor_Back());
     										strMotor_Left=String.valueOf(motorData.getMotor_Left());
     										strMotor_Right=String.valueOf(motorData.getMotor_Right());
     										strMotor_X=String.valueOf(motorData.getMotor_X());
     										strMotor_Y=String.valueOf(motorData.getMotor_Y());
     										
     										
     										dataType = packageType.PtSpeed;
     										speedData = new SpeedData(ss, 69);//69-74
     										strLateral=String.valueOf(speedData.getLateral());
     										strLongitudinal=String.valueOf(speedData.getLongitudinal());
     										strAbout=String.valueOf(speedData.getAbout());
     										
     										dataType = packageType.PtGPS;
     										gpsData = new GPSData(ss, 77);//77-96
     										strLatitude=String.valueOf(gpsData.getLatitude());
     										strLongitude=String.valueOf(gpsData.getLongitude());;
     										strStarCount=String.valueOf(gpsData.getStarCount());
     										strHight=String.valueOf(gpsData.getHight());     										
     										break;     										
     									case 2:
     										dataType = packageType.PtStatus;
     										statusData = new StatusData(ss, 3);
     										strFlyStatus=String.valueOf(statusData.getFlyStatus());
     										strVoltage=String.valueOf(statusData.getVoltage());
     										strSendFlag=String.valueOf(statusData.getSendFlag());
     										strSensorStatus=String.valueOf(statusData.getSensorStatus());
     										strCommStatus=String.valueOf(statusData.getCommStatus());
     										strPhotoFlag=String.valueOf(statusData.getPhotoFlag());
     										break;
//     									case 3:
//     										dataType = packageType.PtGeomagnetism;
//     										geoData = new GeomagnetismData(ss, 3);
//     										strGeoX = String.valueOf(geoData.getX());
//     										strGeoY = String.valueOf(geoData.getY());
//     										strGeoZ = String.valueOf(geoData.getZ());
//     										break;
//     									case 4:
//     										dataType = packageType.PtRemoteControl;
//     										remoteData = new RemoteControlData(ss, 3);
//     										strReRoll = String.valueOf(remoteData.getRoll());
//     										strRePitch=String.valueOf(remoteData.getPitch());
//     										strReYaw=String.valueOf(remoteData.getYaw());	
//     										strReThrottle=String.valueOf(remoteData.getThrottle());
//     										break;	
//     									case 5:
//     										dataType = packageType.PtStartClose;
//     										closeData = new StartCloseData(ss, 3);
//     										strSW1=String.valueOf(closeData.getSW1());
//     										strSW2=String.valueOf(closeData.getSW2());
//     										strSW3=String.valueOf(closeData.getSW3());
//     										strSW4=String.valueOf(closeData.getSW4());
//     										break;
//     										
//     									case 6:
//     										dataType = packageType.PtWayPoint;
//     										wayPointData = new WayPointData(ss, 3);
//     										strwayID=String.valueOf(wayPointData.getMotor_Front());
//     										strwayCount=String.valueOf(wayPointData.getWayCount());
//     										strwayIndex=String.valueOf(wayPointData.getWayIndex());
//     										
//     										strwaypenshaispeed=String.valueOf(wayPointData.getWaypenshai_speed());
//     										strwaypenshaijuli=String.valueOf(wayPointData.getWaypenshai_juli());
//     										strwaypenshaibanjing=String.valueOf(wayPointData.getWaypenshai_banjing());
//     										strwaypenshaigaodu=String.valueOf(wayPointData.getWaypenshai_gaodu());
//     										break;
//     										
//     									case 7:
//     										dataType = packageType.PtMotor;
//     										motorData = new MotorData(ss, 3);
//     										strMotor_Front=String.valueOf(motorData.getMotor_Front());
//     										strMotor_Back=String.valueOf(motorData.getMotor_Back());
//     										strMotor_Left=String.valueOf(motorData.getMotor_Left());
//     										strMotor_Right=String.valueOf(motorData.getMotor_Right());
//     										strMotor_X=String.valueOf(motorData.getMotor_X());
//     										strMotor_Y=String.valueOf(motorData.getMotor_Y());
//     										break;
//     										
//     									case 8:
//     										dataType = packageType.PtSpeed;
//     										speedData = new SpeedData(ss, 3);
//     										strLateral=String.valueOf(speedData.getLateral());
//     										strLongitudinal=String.valueOf(speedData.getLongitudinal());
//     										strAbout=String.valueOf(speedData.getAbout());
//     										break;
//     										
//     									case 9:
//     										dataType = packageType.PtGPS;
//     										gpsData = new GPSData(ss, 3);
//     										strLatitude=String.valueOf(gpsData.getLatitude());
//     										strLongitude=String.valueOf(gpsData.getLongitude());;
//     										strStarCount=String.valueOf(gpsData.getStarCount());
//     										strHight=String.valueOf(gpsData.getHight());
//     										break;
//     										
//     									case 10:
//     										dataType = packageType.PtStatus;
//     										statusData = new StatusData(ss, 3);
//     										strFlyStatus=String.valueOf(statusData.getFlyStatus());
//     										strVoltage=String.valueOf(statusData.getVoltage());
//     										strSendFlag=String.valueOf(statusData.getSendFlag());
//     										strSensorStatus=String.valueOf(statusData.getSensorStatus());
//     										strCommStatus=String.valueOf(statusData.getCommStatus());
//     										strPhotoFlag=String.valueOf(statusData.getPhotoFlag());
//     										break;
//     										
//     									case 11:
//     										dataType = packageType.PtSpeed;
//     										xbeeData = new XBEEData(ss, 3);
//     										strXBEE=String.valueOf(xbeeData.getXBEE());
//     										break;	
     									}
     										}
     									
     									if(modify_pid_flag == 1){
         									switch (Date[2]) {
         									case 4:
         										piddata = new PIDData(ss, 3);
         										Gyro_RP_KP = String.valueOf(piddata.getIGyro_RP_KP());
         										Gyro_RP_KI = String.valueOf(piddata.getIGyro_RP_KI());
         										Gyro_RP_KD = String.valueOf(piddata.getIGyro_RP_KD());
         										Gyro_Y_KP = String.valueOf(piddata.getIGyro_Y_KP());
         										Gyro_Y_KI = String.valueOf(piddata.getIGyro_Y_KI());
         										Gyro_Y_KD = String.valueOf(piddata.getIGyro_Y_KD());
         										Prop_RP_KP =String.valueOf(piddata.getIProp_RP_KP());
         										Prop_RP_KI= String.valueOf(piddata.getIProp_RP_KI());
         										Prop_Y_KP= String.valueOf(piddata.getIProp_Y_KP());
         										Prop_Y_KI= String.valueOf(piddata.getIProp_Y_KI());
         									    Change_Pid_Send_Flag = (piddata.getIChange_Pid_Send_Flag());
         									    break;
         								
         									}
     									}
         									
     									}
     										
     							     }
     							}
     							else
     							{
     								/**
//     									 * 先获得前3个字节(2个包头1个ID),放入Data中,并清空cIndexHeader 
//     									 **/
//     									//数据头的3个字节
     								
     								
 										if (cRxDate < 0) 
 										{
 											cRxDate = (byte) (cRxDate + 256);
 										} else 
 										{
 											cRxDate = (byte) cRxDate;
 										}
 								
     								cIndexHeader[0] = cIndexHeader[1];
     								cIndexHeader[1] = cIndexHeader[2];
     								cIndexHeader[2] = cRxDate;
     								//判断包头是否是正确的
     								if(modify_pid_flag == 0){
     								if(cIndexHeader[0]==(byte)0xB5&&cIndexHeader[1]==(byte)0x5B)
     								{
     									stop_frequency_flag = 20;
     								    for(int i1=0;i1<3;i1++)
     								    {
     								    	Date[i1]=cIndexHeader[i1];
     								    	}
     							    	for(int i2=0;i2<3;i2++) 
     							    	{
     							    		cIndexHeader[i2]=0;
     							    		}
//     									   根据ID给出nPacketSize的大小
     							    	if (Date[2] < 1 && Date[2] > 11 ) 
     							    	{
     							    		Log.d(Tag, "ID获取异常:");
     									         break;
     								      }
     								    if (Date[2]==(byte)0x01) 
     								    {
     									  nPacketSize=99;
     								    }
     								    else if (Date[2]==(byte)0x02) 
     								    {
     									  nPacketSize=87;
     								    }
     								    else if (Date[2]==(byte)0x03) 
     								    {
     									  nPacketSize=11;
     								    }
     								    else if (Date[2]==(byte)0x04) 
     								    {
     									  nPacketSize=13;
     								    }
     								    else if (Date[2]==(byte)0x05) 
     								    {
     									  nPacketSize=13;
     								    }
     								    //需要将0x06中的11改为19
     								    else if (Date[2]==(byte)0x06) 
     								    {
     									  nPacketSize=19;
     								    }
     								    else if (Date[2]==(byte)0x07) 
     								    {
     									  nPacketSize=17;
     								    }
     								    else if (Date[2]==(byte)0x08) 
     								    {
     									  nPacketSize=11;
     								    }
     								    else if (Date[2]==(byte)0x09) 
     								    {
     									  nPacketSize=25;
     								    }
     								    else if (Date[2]==(byte)0x0A) 
     								    {
     									  nPacketSize=21;
     								    }
     								    else if (Date[2]==(byte)0x0B) 
     								    {
     									  nPacketSize=7;
     								    } 
     								    nRxDateCount=3;
     								    RxDataflag=true;
     							      }
     								}
     								
     								if(modify_pid_flag == 1){
         								if(cIndexHeader[0]==(byte)0xFF&&cIndexHeader[1]==(byte)0xFF)
         								{
         									stop_frequency_flag=21;
         								    for(int i1=0;i1<3;i1++)
         								    {
         								    	Date[i1]=cIndexHeader[i1];
         								    	}
         							    	for(int i2=0;i2<3;i2++) 
         							    	{
         							    		cIndexHeader[i2]=0;
         							    		}
//         									   根据ID给出nPacketSize的大小
         							    	if (Date[2] < 1 && Date[2] > 11 ) 
         							    	{
         							    		Log.d(Tag, "ID获取异常:");
         									         break;
         								      }
         								    if (Date[2]==(byte)0x02) 
         								    {
         									  nPacketSize=23;
         								    }
         								    else if (Date[2]==(byte)0x03) 
         								    {
         									  nPacketSize=21;
         								    }
         								    else if (Date[2]==(byte)0x04) 
         								    {
         									  nPacketSize=27;
         								    }
         								    else if (Date[2]==(byte)0x05) 
         								    {
         									  nPacketSize=57;
         								    }
         								    else if (Date[2]==(byte)0x06) 
         								    {
         									  nPacketSize=11;
         								    }
         								    else if (Date[2]==(byte)0x07) 
         								    {
         									  nPacketSize=11;
         								    }
         								    else if (Date[2]==(byte)0x08) 
         								    {
         									  nPacketSize=23;
         								    }
         								    else if (Date[2]==(byte)0x09) 
         								    {
         									  nPacketSize=13;
         								    }
         								    else if (Date[2]==(byte)0x0A) 
         								    {
         									  nPacketSize=13;
         								    }
         								    else if (Date[2]==(byte)0x0C) 
         								    {
         									  nPacketSize=6;
         								    }
         								    nRxDateCount=3;
         								    RxDataflag=true;
         							      }
         								}
     						   }							
     					}
     						if(ImuState.Voltage_stateringt!=null && ImuState.Gyro_stateringt!=null
     								&&ImuState.Acc_stateringt!=null && ImuState.Mag_stateringt!=null
     								&&ImuState.Zitai_stateringt!=null){
     						if(strVoltage != null){
     							ImuState.Voltage_stateringt.setText(strVoltage+"V");
     						}
     						else
     						{
     							ImuState.Voltage_stateringt.setText("0.0"+"V");
     						}
     						if(Imu_Check_Gyro == 0){
     							ImuState.Gyro_stateringt.setText ("陀螺仪正常");
     						}
     						if(Imu_Check_Gyro == 1 || Imu_Check_Gyro == 3 ||Imu_Check_Gyro == 5){
     							ImuState.Gyro_stateringt.setText ("陀螺仪异常");
     							ImuState.Gyro_stateringt.setTextColor(Color.RED);
         						}
     						if(Imu_Check_Acc == 0){
     							ImuState.Acc_stateringt.setText ("加速度正常");
         						}
         					if(Imu_Check_Acc == 1 || Imu_Check_Acc == 3 || Imu_Check_Magcalib == 5){
         						ImuState.Acc_stateringt.setText ("加速度异常");
         						ImuState.Acc_stateringt.setTextColor(Color.RED);

             					}
     						if(Imu_Check_Magcalib == 0){
     							ImuState.Mag_stateringt.setText ("地磁正常");
         						}
         					if(Imu_Check_Magcalib == 1 || Imu_Check_Magcalib == 3 || Imu_Check_Magcalib == 5){
         						ImuState.Mag_stateringt.setText ("地磁异常");
         						ImuState.Mag_stateringt.setTextColor(Color.RED);
             					}
     						if(Imu_Check_Attitude == 0){
     							ImuState.Zitai_stateringt.setText ("IMU正常");
         						}
         					if(Imu_Check_Attitude == 1 || Imu_Check_Attitude == 3 ||Imu_Check_Attitude == 5){
         						ImuState.Zitai_stateringt.setText ("IMU异常");
         						ImuState.Zitai_stateringt.setTextColor(Color.RED);
             					}
         					if(strStarCount != null)
         					{
         						ImuState.strnum_value.setText(strStarCount);
         					}else
         						ImuState.strnum_value.setText("0");
         						
         					ImuState.jixing_value.setText("无法识别");
         					ImuState.yaokongqi_value.setText("无法识别");
         				
         					if(strwaypenshaibanjing != null){
         					
         						ImuState.fudu_value.setText(strwaypenshaibanjing + "米");
         					}else
         					{
         						ImuState.fudu_value.setText("0.0米");
         					}
         					if(strwaypenshaigaodu != null){
             					
         						ImuState.gaodu_value.setText(strwaypenshaigaodu + "米");
         					}else
         					{
         						ImuState.gaodu_value.setText("0.0米");
         					}
         					if(strwaypenshaispeed != null){
             					
         						ImuState.sudu_value.setText(strwaypenshaispeed + "米/秒");
         					}else
         					{
         						ImuState.sudu_value.setText("0.0米/秒");
         					}
         					if(strwaypenshaijuli != null){
         						
         						ImuState.juli_value.setText(strwaypenshaijuli+ "米" );
         					}else
         					{
         						ImuState.juli_value.setText("0.0米");
         					}
         					
         					
         					//strnum_value  jixing_value  yaokongqi_value  fudu_value  gaodu_value  sudu_value  juli_value
         		          //  取得当前进度
         					if(Screenflagchange){	
         						Screenflagchange = false;
         			 			
         			 			
         			 			//当进度小于40时，设置成40，防止太黑看不见的后果。
         			 			if (tmpInt < 40) {
         			 				tmpInt = 40;
         			 			}
         			 			if ((0 <= tmpInt) && (tmpInt < 85)) {
         			 				//circleSeekBar.setProgressFrontColor(Color.CYAN);
         			 				ledcontrolflag = 0;
         			 				SendLEDBrighe(ledcontrolflag);
         			 			}
         			 			if ((85 <= tmpInt) && (tmpInt < 170)) {
         			 				//circleSeekBar.setProgressFrontColor(Color.GREEN);
         			 				ledcontrolflag = 1;
         			 				SendLEDBrighe(ledcontrolflag);
         			 			}
         			 			if ((170 <= tmpInt) && (tmpInt <= 255)) {
         			 				//circleSeekBar.setProgressFrontColor(Color.RED);
         			 				ledcontrolflag = 2;
         			 				SendLEDBrighe(ledcontrolflag);
         			 			}
         			 			//根据当前进度改变亮度
         			 			Settings.System.putInt(getContentResolver(),
         			 					Settings.System.SCREEN_BRIGHTNESS, tmpInt);
         			 			tmpInt = Settings.System.getInt(getContentResolver(),
         			 					Settings.System.SCREEN_BRIGHTNESS, -1);
         			 			WindowManager.LayoutParams wl = getWindow()
         			 					.getAttributes();

         			 			float tmpFloat = (float) tmpInt / 255;
         			 			if (tmpFloat > 0 && tmpFloat <= 1) {
         			 				wl.screenBrightness = tmpFloat;
         			 			
         			 				}
         			 				getWindow().setAttributes(wl);
         						}
     						}
                     }

//           if (action.equals(UartService.ACTION_GATT_DISCONNECTED)) {
//            	    Gyro_state.setText("数据连接异常");
//					Acc_state.setText("数据连接异常");
//					Mag_state.setText("数据连接异常");
//					Zitai_state.setText("数据连接异常");
//					Voltage_state.setText("0.0V");
//            }
           //*********************//
            if (action.equals(UartService.DEVICE_DOES_NOT_SUPPORT_UART)){
            	//showMessage("设备不支持. 连接已经断开");
            	mService.disconnect();
            }
			if(changefrencecyshow){
				showMessage("频率切换完成");
				changefrencecyshow = false;
            }
            if(workindexshow){
				showMessage("作业参数设置完成");
				workindexshow = false;
            }
            if(pidsetshow){
				showMessage("PID设置成功");
				pidsetshow = false;
            }
            if(modelsetshow){
				showMessage("机型设置完成");
				modelsetshow = false;
            } 
        }
    };
    private void service_init() {
        Intent bindIntent = new Intent(this, UartService.class);
        //使用Service的组件通过Context.bindService()建立服务链接， 
        //通过Context.unbindService()停止服务链接 
        //通过bindService()函数绑定Servcie时， onCreate()函数和onBinde()函 
        //数将先后被调用 
        bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
        //动态的注册了广播事件
        LocalBroadcastManager.getInstance(this).registerReceiver(UARTStatusChangeReceiver, makeGattUpdateIntentFilter());
    }
  //动态的注册了广播事件
    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UartService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(UartService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(UartService.DEVICE_DOES_NOT_SUPPORT_UART);
        return intentFilter;
    }
    @Override
    public void onStart() {
    	super.onStart();
    }
    @Override
    public void onDestroy() {
    	 super.onDestroy();
        Log.d(TAG, "onDestroy()");
        
        try {
        	LocalBroadcastManager.getInstance(this).unregisterReceiver(UARTStatusChangeReceiver);
        } catch (Exception ignore) {
            Log.e(TAG, ignore.toString());
        } 
        unbindService(mServiceConnection);
        mService.stopSelf();
        mService= null;
    }
  //动态的注册了广播事件
    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (!mBtAdapter.isEnabled()) {
            Log.i(TAG, "onResume - BT not enabled yet");
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);            
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case REQUEST_SELECT_DEVICE:
        	//When the DeviceListActivity return, with the selected device address
            if (resultCode == Activity.RESULT_OK && data != null) {
                String deviceAddress = data.getStringExtra(BluetoothDevice.EXTRA_DEVICE);
                mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(deviceAddress);
               
                Log.d(TAG, "... onActivityResultdevice.address==" + mDevice + "mserviceValue" + mService);
                ((TextView) findViewById(R.id.deviceName)).setText(mDevice.getName()+ " - 正在连接中......");
                mService.connect(deviceAddress);
            }
            break;
        case REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Bluetooth has turned on ", Toast.LENGTH_SHORT).show();

            } else {
                // User did not enable Bluetooth or an error occurred
                Log.d(TAG, "BT not enabled");
                Toast.makeText(this, "Problem in BT Turning ON ", Toast.LENGTH_SHORT).show();
                finish();
            }
            break;
        default:
            Log.e(TAG, "wrong request code");
            break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
       
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
  
    }
    public void SendNullData()
    {
    	byte[] TxData = new byte[6];
    	TxData[0] = (byte)0x9A;
		TxData[1] = (byte)0xA9;
		TxData[2] = (byte)0x00;
		TxData[3] = (byte)0x00;
		TxData[4]=  (byte)0x00;
		TxData[5] = (byte)0x00;
		MainActivity.flightnulldata = TxData.clone();
    }
    public void SendLEDBrighe(short temint)
    {
    	byte[] TxData = new byte[6];
    	TxData[0] = (byte)0x9A;
		TxData[1] = (byte)0xA9;
		TxData[2] = (byte)0x00;
		TxData[3] = (byte)temint;
		CalcBcc(TxData,3);	
		mService.writeRXCharacteristic(TxData);
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
   
    /**
     * 获得当前屏幕亮度的模式 SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
     * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
     */
//    private int getScreenMode() {
//        int screenMode = 0;
//        try {
//            screenMode = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
//        } catch (Exception localException) {
//
//        }
//        return screenMode;
//    }

    /**
     * 设置当前屏幕亮度的模式 SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
     * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
     */
    private void setScreenMode(int paramInt) {
        try {
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, paramInt);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }
    /**
     * 获得当前屏幕亮度值 0--255
     */
    private int getScreenBrightness() {
        int screenBrightness = 255;
        try {
            screenBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception localException) {

        }
        return screenBrightness;
    }

    /**
     * 设置当前屏幕亮度值 0--255
     */
//    private void saveScreenBrightness(int paramInt) {
//        try {
//            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, paramInt);
//        } catch (Exception localException) {
//            localException.printStackTrace();
//        }
//    }

    /**
     * 保存当前的屏幕亮度值，并使之生效
     */
//    private void setScreenBrightness(int paramInt) {
//        Window localWindow = getWindow();
//        WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
//        float f = paramInt / 255.0F;
//        localLayoutParams.screenBrightness = f;
//        localWindow.setAttributes(localLayoutParams);
//    }
    @Override
    public void onBackPressed() {
        if (mState == UART_PROFILE_CONNECTED) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            showMessage("设备在后台运行\n             断开连接将退出");
        }
        else {
            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(R.string.popup_title)
            .setMessage(R.string.popup_message)
            .setPositiveButton(R.string.popup_yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
   	                finish();
                }
            })
            .setNegativeButton(R.string.popup_no, null)
            .show();
        }
    }
}
