package com.nordicsemi.nrfUARTv2;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.nordicsemi.buff.GetIntData;
import com.nordicsemi.buff.PackageType.packageType;
import com.nordicsemi.data.AcceleratedSpeed;
import com.nordicsemi.data.GPSData;
import com.nordicsemi.data.GeomagnetismData;
import com.nordicsemi.data.IMUData;
import com.nordicsemi.data.MotorData;
import com.nordicsemi.data.RemoteControlData;
import com.nordicsemi.data.SpeedData;
import com.nordicsemi.data.StartCloseData;
import com.nordicsemi.data.StatusData;
import com.nordicsemi.data.WayPointData;
import com.nordicsemi.data.XBEEData;



public class ClientThread implements Runnable
{
	int cc = MainActivity.gloalV;
	static Socket s;
	// 定义向UI线程发送消息的Handler对象
	private Handler handler;
	// 定义接收UI线程的消息的Handler对象
	public Handler revHandler;
	// 该线程所处理的Socket所对应的输入流
	InputStream br=null;
	OutputStream os = null;
	private String Tag = "MainActivity";
	byte[] Date = new byte[50];
	byte[] cIndexHeader = new byte[3];
	byte[] buffer = new byte[2048];
	byte[] ss = new byte[50];
	
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
	static String strSensorStatus;
	static String strCommStatus;
	static String strPhotoFlag;
	
	/**XBEE通断检测数据*/
	static String strXBEE;
	
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
	public ClientThread(Handler handler)
	{
		this.handler = handler;
	}
	public void run()
	{
		try
		{   
//			String True_IP = MainActivity.sIP;
//			int True_Port = MainActivity.port;
//			String aa=String.valueOf(cc);
//			System.out.println(aa);
//			s = new Socket(True_IP, True_Port);
//			br=new BufferedInputStream(
//					s.getInputStream());
//			os = s.getOutputStream();
			// 启动一条子线程来读取服务器响应的数据
			//br = new 
			new Thread()
			{
				@SuppressWarnings("null")
				@Override
				public void run()
				{
					final byte[] content ;
					byte cRxDate;
					int nRxDateCount = 0;
					int nPacketSize = 0;
					for (int y = 0; y < 3; y++) {
						cIndexHeader[y] = 0;
					}
					boolean RxDataflag=false;
					while(MainActivity.isConnecting);//&&(br.read(buffer)!=-1))//一直循环,查询是否有数据输入
					{
						Intent intent = null;
						content =intent.getByteArrayExtra(UartService.EXTRA_DATA);
						 //content = br.read(buffer);
						for(int i=0;i<content.length;i++)
						{
							cRxDate = content[i];
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
									if(Bcc==Bcc2){
										Message msg=new Message();
										msg.what=0x123;
//										根据date【2】的不同输出各数据
									switch (Date[2]) {
									case 1:
										dataType = packageType.PtIMU;
										IMU = new IMUData(ss, 3);
										strRoll = String.valueOf(IMU.getIRoll());
									    strPitch = String.valueOf(IMU.getIPitch());
										strYaw = String.valueOf(IMU.getIYaw());
										strRollRate = String.valueOf(IMU.getIRollRate());
										strPichRate = String.valueOf(IMU.getIPitchRate());
										strYawRate = String.valueOf(IMU.getIYawRate());
										handler.sendEmptyMessage(1);
										break;
									case 2:
										dataType = packageType.PtAcceleratedSpeed;
										accspeed = new AcceleratedSpeed(ss, 3);
										strAccX = String.valueOf(accspeed.getX());
										strAccY = String.valueOf(accspeed.getY());
										strAccZ = String.valueOf(accspeed.getZ());
										handler.sendEmptyMessage(2);
										break;
									case 3:
										dataType = packageType.PtGeomagnetism;
										geoData = new GeomagnetismData(ss, 3);
										strGeoX = String.valueOf(geoData.getX());
										strGeoY = String.valueOf(geoData.getY());
										strGeoZ = String.valueOf(geoData.getZ());
										handler.sendEmptyMessage(3);
										break;
									case 4:
										dataType = packageType.PtRemoteControl;
										remoteData = new RemoteControlData(ss, 3);
										strReRoll = String.valueOf(remoteData.getRoll());
										strRePitch=String.valueOf(remoteData.getPitch());
										strReYaw=String.valueOf(remoteData.getYaw());	
										strReThrottle=String.valueOf(remoteData.getThrottle());
										handler.sendEmptyMessage(4);
										break;	
									case 5:
										dataType = packageType.PtStartClose;
										closeData = new StartCloseData(ss, 3);
										strSW1=String.valueOf(closeData.getSW1());
										strSW2=String.valueOf(closeData.getSW2());
										strSW3=String.valueOf(closeData.getSW3());
										strSW4=String.valueOf(closeData.getSW4());
										handler.sendEmptyMessage(5);
										break;
										
									case 6:
										dataType = packageType.PtWayPoint;
										wayPointData = new WayPointData(ss, 3);
										strwayID=String.valueOf(wayPointData.getMotor_Front());
										strwayCount=String.valueOf(wayPointData.getWayCount());
										strwayIndex=String.valueOf(wayPointData.getWayIndex());
										handler.sendEmptyMessage(6);
										break;
										
									case 7:
										dataType = packageType.PtMotor;
										motorData = new MotorData(ss, 3);
										strMotor_Front=String.valueOf(motorData.getMotor_Front());
										strMotor_Back=String.valueOf(motorData.getMotor_Back());
										strMotor_Left=String.valueOf(motorData.getMotor_Left());
										strMotor_Right=String.valueOf(motorData.getMotor_Right());
										strMotor_X=String.valueOf(motorData.getMotor_X());
										strMotor_Y=String.valueOf(motorData.getMotor_Y());
										handler.sendEmptyMessage(7);
										break;
										
									case 8:
										dataType = packageType.PtSpeed;
										speedData = new SpeedData(ss, 3);
										strLateral=String.valueOf(speedData.getLateral());
										strLongitudinal=String.valueOf(speedData.getLongitudinal());
										strAbout=String.valueOf(speedData.getAbout());
										handler.sendEmptyMessage(8);
										break;
										
									case 9:
										dataType = packageType.PtGPS;
										gpsData = new GPSData(ss, 3);
										strLatitude=String.valueOf(gpsData.getLatitude());
										strLongitude=String.valueOf(gpsData.getLongitude());;
										strStarCount=String.valueOf(gpsData.getStarCount());
										strHight=String.valueOf(gpsData.getHight());
										handler.sendEmptyMessage(9);
										break;
										
									case 10:
										dataType = packageType.PtStatus;
										statusData = new StatusData(ss, 3);
										strFlyStatus=String.valueOf(statusData.getFlyStatus());
										strVoltage=String.valueOf(statusData.getVoltage());
										strSendFlag=String.valueOf(statusData.getSendFlag());
										strSensorStatus=String.valueOf(statusData.getSensorStatus());
										strCommStatus=String.valueOf(statusData.getCommStatus());
										strPhotoFlag=String.valueOf(statusData.getPhotoFlag());
										handler.sendEmptyMessage(10);
										break;
										
									case 11:
										dataType = packageType.PtSpeed;
										xbeeData = new XBEEData(ss, 3);
										strXBEE=String.valueOf(xbeeData.getXBEE());
										handler.sendEmptyMessage(11);
										break;	
									}
									}
							     }
							}
							else
							{
								/**
//									 * 先获得前3个字节(2个包头1个ID),放入Data中,并清空cIndexHeader 
//									 **/
//									//数据头的3个字节
								cIndexHeader[0] = cIndexHeader[1];
								cIndexHeader[1] = cIndexHeader[2];
								cIndexHeader[2] = cRxDate;
								//判断包头是否是正确的
								if(cIndexHeader[0]==(byte)0xB5&&cIndexHeader[1]==(byte)0x5B)
								{
								    for(int i1=0;i1<3;i1++)
								    {
								    	Date[i1]=cIndexHeader[i1];
								    	}
							    	for(int i2=0;i2<3;i2++) 
							    	{
							    		cIndexHeader[i2]=0;
							    		}
//									   根据ID给出nPacketSize的大小
							    	if (Date[2] < 1 && Date[2] > 11 ) 
							    	{
							    		Log.d(Tag, "ID获取异常:");
									         break;
								      }
								    if (Date[2]==(byte)0x01) 
								    {
									  nPacketSize=17;
								    }
								    else if (Date[2]==(byte)0x02) 
								    {
									  nPacketSize=11;
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
									  nPacketSize=11;
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
					}
						
					}
				}

				
			}.start();
			// 为当前线程初始化Looper
				Looper.prepare();
			// 创建revHandler对象
			revHandler = new Handler()
			{
				@Override
				public void handleMessage(Message msg)
				{
					// 接收到UI线程中用户输入的数据
					if (msg.what == 0x345)
					{
						// 将用户在文本框内输入的内容写入网络
						try
						{
							os.write((msg.obj.toString() + "\r\n")
									.getBytes("utf-8"));
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			};
			// 启动Looper
		Looper.loop();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally{
			try {
				br.close();
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
