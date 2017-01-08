package com.nordicsemi.data;

import com.nordicsemi.buff.GetIntData;

/**路点数据*/
public class WayPointData {
	int m_wayID;
	int m_wayCount;
	int m_wayIndex;
	int m_penshai_speed;
	int m_penshai_juli;
	int m_penshai_banjing;
	int m_penshai_gaodu;
	
	public WayPointData(byte[] byBuff, int nOffset){
		m_wayID = GetIntData.getInt(byBuff, nOffset + 0, 2);
		m_wayCount = GetIntData.getInt(byBuff, nOffset + 2, 2);
		m_wayIndex = GetIntData.getInt(byBuff, nOffset + 4, 2);
		m_penshai_speed = GetIntData.getInt(byBuff, nOffset + 6, 2);
		m_penshai_juli = GetIntData.getInt(byBuff, nOffset + 8, 2);
		m_penshai_banjing = GetIntData.getInt(byBuff, nOffset + 10, 2);
		m_penshai_gaodu = GetIntData.getInt(byBuff, nOffset + 12, 2);
	}
	
	public double getMotor_Front(){
        return m_wayID ;
    }
	
	public double getWayCount(){
        return m_wayCount;
    }
	
	public double getWayIndex(){
        return m_wayIndex;
    }//
	public double getWaypenshai_speed(){
        return m_penshai_speed/100;
    }
	public double getWaypenshai_juli(){
        return m_penshai_juli/100;
    }
	public double getWaypenshai_banjing(){
        return m_penshai_banjing/100;
    }
	public double getWaypenshai_gaodu(){
        return m_penshai_gaodu/100;
    }
}
