package com.nordicsemi.buff;

public class PackageType {

	public enum packageType {
        PtNon,
        PtIMU, 
        PtAcceleratedSpeed,//���ٶ�
        PtGeomagnetism,//�ش�
        PtRemoteControl,//�b����
        PtStartClose,//����
        PtWayPoint,//·��
        PtMotor,//���
        PtSpeed,//�ٶ�
        PtGPS,//GPS
        PtStatus,//״̬
        PtXBEE   //XBEE�ϵ���
    }
}
