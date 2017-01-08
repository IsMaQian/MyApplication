package com.nordicsemi.data;

import com.nordicsemi.buff.GetIntData;
import com.nordicsemi.buff.GetDoubleData;

/**GPS����*/
public class GPSData {
	//��Ϊ��γ����Ҫ8���ֽڣ����Բ�����int���棬��Ҫ��long��
	double m_nLatitude;      //γ��
	double m_nLongitude;     //����
	int m_nStarCount;     //��������
	int m_nHight;         //�߶�
	
	
	public GPSData(byte[] byBuff, int nOffset){
		m_nLatitude = GetDoubleData.byteArrayToDouble(byBuff, nOffset);
		m_nLongitude = GetDoubleData.byteArrayToDouble(byBuff, nOffset+8);
		m_nStarCount = GetIntData.getInt(byBuff, nOffset + 16, 2);
		m_nHight = GetIntData.getInt(byBuff, nOffset + 18, 2);
	}
	
	public Double getLatitude(){
		return m_nLatitude;
	}
	
	public Double getLongitude(){
		return m_nLongitude;
	}
	
	public int getStarCount(){
		return m_nStarCount;
	}
	
	public double getHight(){
		return m_nHight / 100.0;
	}
}
