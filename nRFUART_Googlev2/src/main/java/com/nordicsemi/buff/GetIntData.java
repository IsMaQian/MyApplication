package com.nordicsemi.buff;

public class GetIntData {
	///byBuff:�ֽ����飬Դ����
		///nOffset���ӵڼ����ֽڿ�ʼ��ȡInt
		///nSize:Ҫ��ȡ���ٸ��ֽڣ�һ����4���ֽ�
	public static short getInt(byte[] byBuff,int nOffset,int nSize){
	
		short nRes=0;
		 if (nOffset + nSize > byBuff.length){
		    	return 0;
		    }
		 for (int i = 0; i < nSize; i++){
		        nRes = (short) (nRes |(short)( (byBuff[i + nOffset] & 0x00FF)<< (8 * (nSize-i-1)) ));
		    }
		    return nRes;
		} 
	}


