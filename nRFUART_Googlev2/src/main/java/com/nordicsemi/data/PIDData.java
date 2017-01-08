package com.nordicsemi.data;

import com.nordicsemi.buff.GetIntData;
public class PIDData {
	
    float Gyro_RP_KP,Gyro_RP_KI,Gyro_RP_KD;
	float Gyro_Y_KP, Gyro_Y_KI, Gyro_Y_KD;
	float Prop_RP_KP,Prop_RP_KI,Prop_Y_KP,Prop_Y_KI;
	int   Change_Pid_Send_Flag;
	 public PIDData(byte[] byBuff, int nOffset){
		 Prop_RP_KI = GetIntData.getInt(byBuff, nOffset + 0, 2);
		 Prop_RP_KP = GetIntData.getInt(byBuff, nOffset + 2, 2);
		 Prop_Y_KI = GetIntData.getInt(byBuff, nOffset + 4, 2);
		 Prop_Y_KP = GetIntData.getInt(byBuff, nOffset + 6, 2);
		 Gyro_RP_KP = GetIntData.getInt(byBuff, nOffset + 8, 2);
		 Gyro_RP_KI = GetIntData.getInt(byBuff, nOffset + 10, 2);
		 Gyro_RP_KD = GetIntData.getInt(byBuff, nOffset + 12, 2);
		 Gyro_Y_KP = GetIntData.getInt(byBuff, nOffset + 14, 2);
		 Gyro_Y_KI = GetIntData.getInt(byBuff, nOffset + 16, 2);
		 Gyro_Y_KD = GetIntData.getInt(byBuff, nOffset + 18, 2);
		 Change_Pid_Send_Flag = GetIntData.getInt(byBuff, nOffset + 20, 2);
		 
	 }	
		public double getIProp_RP_KI(){
	        return Prop_RP_KI/10.0;
	    }
		public double getIProp_RP_KP(){
	        return Prop_RP_KP/10.0;
	    }
		public double getIProp_Y_KI(){
	        return Prop_Y_KI/10.0;
	    }
		public double getIProp_Y_KP(){
	        return Prop_Y_KP/10.0;
	    }
		
		public double getIGyro_RP_KP(){
	        return Gyro_RP_KP/10.0;
	    }
		public double getIGyro_RP_KI(){
	        return Gyro_RP_KI/10.0;
	    }
		public double getIGyro_RP_KD(){
	        return Gyro_RP_KD/10.0;
	    }
		
		public double getIGyro_Y_KP(){
	        return Gyro_Y_KP/10.0;
	    }
		public double getIGyro_Y_KI(){
	        return Gyro_Y_KI/10.0;
	    }
		public double getIGyro_Y_KD(){
	        return Gyro_Y_KD/10.0;
	    }
		public double getIChange_Pid_Send_Flag(){
			return Change_Pid_Send_Flag;
		}
		
}