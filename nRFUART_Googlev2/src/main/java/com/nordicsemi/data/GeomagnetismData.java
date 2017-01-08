package com.nordicsemi.data;

import com.nordicsemi.buff.GetIntData;

/**µØ´ÅÊý¾Ý*/
public class GeomagnetismData {
	int m_gX;
	int m_gY;
	int m_gZ;
	
	public GeomagnetismData(byte[] byBuff, int nOffset){
		m_gX = GetIntData.getInt(byBuff, nOffset + 0, 2);
		m_gY = GetIntData.getInt(byBuff, nOffset + 2, 2);
		m_gZ = GetIntData.getInt(byBuff, nOffset + 4, 2);
	}
	
	public double getX(){
        return m_gX/10000.0;
    }
	
	public double getY(){
        return m_gY/10000.0;
    }
	
	public double getZ(){
        return m_gZ/10000.0;
    }
}
