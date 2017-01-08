package com.nordicsemi.data;

import com.nordicsemi.buff.GetIntData;

/**�ٶ�����*/
public class SpeedData {
	int m_nLateral;         //�����ٶ�
	int m_nLongitudinal;    //�����ٶ�
	int m_nAbout;           //�����ٶ�
	
	public SpeedData(byte[] byBuff, int nOffset){
		m_nLateral = GetIntData.getInt(byBuff, nOffset + 0, 2);
		m_nLongitudinal = GetIntData.getInt(byBuff, nOffset + 2, 2);
		m_nAbout = GetIntData.getInt(byBuff, nOffset + 4, 2);
	}
	
	public double getLateral(){
		return m_nLateral / 100.0;
	}
	
	public double getLongitudinal(){
		return m_nLongitudinal / 100.0;
	}
	
	public double getAbout(){
		return m_nAbout / 100.0;
	}
}
