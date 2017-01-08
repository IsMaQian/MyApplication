package com.nordicsemi.data;

import com.nordicsemi.buff.GetIntData;

/**Ò£¿ØÆ÷Êý¾Ý*/
public class RemoteControlData {
	int m_rRoll;
	int m_rPitch;
	int m_rYaw;
	int m_rThrottle;
	
	public RemoteControlData(byte[] byBuff, int nOffset){
		m_rRoll = GetIntData.getInt(byBuff, nOffset + 0, 2);
		m_rPitch = GetIntData.getInt(byBuff, nOffset + 2, 2);
		m_rYaw = GetIntData.getInt(byBuff, nOffset + 4, 2);
		m_rThrottle = GetIntData.getInt(byBuff, nOffset + 6, 2);
	}
	
	public double getRoll(){
        return m_rRoll/672;
    }
	
	public double getPitch(){
        return m_rPitch/672;
    }
	
	public double getYaw(){
        return m_rYaw/672;
    }
	
	public double getThrottle(){
        return ((m_rThrottle/672)-1);
    }
}
