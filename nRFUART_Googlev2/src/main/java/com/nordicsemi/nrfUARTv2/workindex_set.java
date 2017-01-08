package com.nordicsemi.nrfUARTv2;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class workindex_set extends Activity{
  protected void onCreate(Bundle savedInstanceState) {
	  
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.workindex_set);
	  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	  
	  ActionBar mActionBar=getActionBar();
	  mActionBar.setHomeButtonEnabled(true);
	  mActionBar.setDisplayHomeAsUpEnabled(true);
	  mActionBar.setDisplayShowHomeEnabled(false);  
	  mActionBar.setDisplayShowCustomEnabled(true);
	  mActionBar.setDisplayShowTitleEnabled(true); 
	  mActionBar.setTitle("作业参数");
	  
	  
	  Button displayset;
	  Button displayget;
	  Button displayback;

	  displayset = (Button)findViewById(R.id.buttonsetindex);
	  displayget = (Button)findViewById(R.id.buttongetindex);
	  displayback = (Button)findViewById(R.id.buttonback);
	  
	  final EditText text_work_speed = (EditText) findViewById(R.id.editText1);
	  final EditText text_work_radius = (EditText) findViewById(R.id.editText3);
	  final EditText text_pensa_radius = (EditText) findViewById(R.id.editText2);
	  final EditText text_work_height = (EditText) findViewById(R.id.editText4);
	  
	  displayback.setOnClickListener(new View.OnClickListener() {
          
          @Override
          public void onClick(View v) {
        	    Intent intent = getIntent();
        	    workindex_set.this.setResult(0, intent);
        	    workindex_set.this.finish();
        	    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);	
          }
      });
	  displayset.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
        	  if ( (text_work_speed.length()==0)
        			  || (text_work_radius.length()==0)
        			  || (text_pensa_radius.length()==0)
        			  || (text_work_height.length()==0)){
        		    new  AlertDialog.Builder(workindex_set.this)    
        		
        		                   .setTitle("警告信息" )  
        		 
        		                   .setMessage("请确保参数已经输入完成！" )  
        		 
        		                   .setPositiveButton("确定" ,  null )  
        		
        		                   .show(); 
        		    return;
        		   
        	   }
			
        	  float edit_work_speed=Float.parseFloat(text_work_speed.getText().toString()); 
        	  float edit_work_radius=Float.parseFloat(text_work_radius.getText().toString());
        	  float edit_pensa_radius=Float.parseFloat(text_pensa_radius.getText().toString());
        	  float edit_work_height=Float.parseFloat(text_work_height.getText().toString());
        	  SetWorkIndex(edit_work_speed, edit_work_radius,edit_pensa_radius,edit_work_height);	
          }
      		
	  });
	   displayget.setOnClickListener(new View.OnClickListener() {  
	          public void onClick(View v) { 
	        	  text_work_speed.setText(MainActivity.strwaypenshaispeed); 
	        	  text_work_radius.setText(MainActivity.strwaypenshaijuli);
	        	  text_pensa_radius.setText(MainActivity.strwaypenshaibanjing); 
	        	  text_work_height.setText(MainActivity.strwaypenshaigaodu); 
	        	 
	          }
	      });
	   displayget.performClick();
  }
  public boolean onOptionsItemSelected(MenuItem item)
  {
      // TODO Auto-generated method stub
      if(item.getItemId() == android.R.id.home)
      {
    
          finish();
          overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
          return true;
      }
      return super.onOptionsItemSelected(item);
  }
  public void SetWorkIndex(float Set_work_speed,float Set_work_radius,float Set_pensa_radius,float Set_work_height)
  {
		short sTemp;
		byte[] TxData = new byte[14];
	  	  
		TxData[0] = (byte)0x1E;
		TxData[1] = (byte)0xE1;
		TxData[2] = (byte)0x0F;
		sTemp = (short)((Set_work_speed+0.005) * 100.0);
		TxData[3] = (byte)((sTemp&0xFF00)>>8);
		TxData[4]=(byte)(sTemp&0x00FF);
		sTemp = (short)((Set_work_radius+0.005) * 100.0);
		TxData[5] = (byte)((sTemp&0xFF00)>>8);
		TxData[6]=(byte)(sTemp&0x00FF);
		sTemp = (short)((Set_pensa_radius+0.005) * 100.0);
		TxData[7] = (byte)((sTemp&0xFF00)>>8);
		TxData[8]=(byte)(sTemp&0x00FF);
		sTemp = (short)((Set_work_height+0.005) * 100.0);
		TxData[9] = (byte)((sTemp&0xFF00)>>8);
		TxData[10]=(byte)(sTemp&0x00FF);
		CalcBcc(TxData,10);		
		MainActivity.WorkindexData = TxData.clone();
		MainActivity.workindexsend = true;
		MainActivity.sendnulldata = false;
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
  
}
