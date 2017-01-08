package com.nordicsemi.nrfUARTv2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;
/**
 * ���һ���demo
 * @author xzw
 *
 */
public class flightdcset extends Activity implements OnGestureListener {
	
	
	private ViewFlipper viewFlipperd;
	private GestureDetector detectord; //���Ƽ��
	
	Animation leftInAnimationd;
	Animation leftOutAnimationd;
	Animation rightInAnimationd;
	Animation rightOutAnimationd;
	Button buttonviewbackded;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.viewflippervol);
         
        buttonviewbackded= (Button) findViewById(R.id.buttonviewbackvol);
        viewFlipperd = (ViewFlipper)findViewById(R.id.viewFlippervol);
        detectord = new GestureDetector(this);
        //���ƾ��α߿�
        GradientDrawable drawabled = new GradientDrawable();  
        drawabled.setShape(GradientDrawable.RECTANGLE); // ����  RECTANGLE 
        drawabled.setStroke(3, Color.rgb(105, 105, 105)); // �߿��ϸ����ɫ  
        drawabled.setCornerRadius(20.0f); // �߿�Բ�ǰ뾶
        drawabled.setColor(0x00000000); // �߿��ڲ���ɫ  
        buttonviewbackded.setBackgroundDrawable(drawabled);  
        //��viewFlipper���View
        viewFlipperd.addView(getImageView(R.drawable.new_feature_1));
        viewFlipperd.addView(getImageView(R.drawable.new_feature_2));
        viewFlipperd.addView(getImageView(R.drawable.new_feature_3));
        viewFlipperd.addView(getImageView(R.drawable.new_feature_4));
        viewFlipperd.addView(getImageView(R.drawable.new_feature_5));
        viewFlipperd.addView(getImageView(R.drawable.new_feature_6));
      
        //����Ч��
    	leftInAnimationd = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
		leftOutAnimationd = AnimationUtils.loadAnimation(this, R.anim.push_left_out);
		rightInAnimationd = AnimationUtils.loadAnimation(this, R.anim.push_right_in);
		rightOutAnimationd = AnimationUtils.loadAnimation(this, R.anim.push_right_out);
		
		buttonviewbackded.setOnClickListener(new View.OnClickListener() {
	          
	          @Override
	          public void onClick(View v) {
	        	  
	        	  modeltype.Index_BatteryType = (short) viewFlipperd.getDisplayedChild();
	        	  switch(modeltype.Index_BatteryType)
	        	  {
	        	  
	        	  case 0:
	        		  modeltype.textViewvolseted.setText("   2S   ");
	          		  break;
	          	  case 1:
	          		modeltype.textViewvolseted.setText("   3S   ");
	          		  break;
	          	  case 2:
	          		modeltype.textViewvolseted.setText("   4S   ");
	          		  break;
	          	  case 3:
	          		modeltype.textViewvolseted.setText("   6S   ");
	          		  break;
	          	  
	        	  }
	        	  
	        	  Intent intent = getIntent();
	        	  flightdcset.this.setResult(0, intent);
	        	  flightdcset.this.finish();
	        	  overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);	
	          }
	      });
		
    }

    private ImageView getImageView(int id){
    	ImageView imageView = new ImageView(this);
    	imageView.setImageResource(id);
    	return imageView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
     
    	return this.detectord.onTouchEvent(event); //touch�¼��������ƴ���
    }
    
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
//		Log.i(TAG, "e1="+e1.getX()+" e2="+e2.getX()+" e1-e2="+(e1.getX()-e2.getX()));

		if((e1.getX()-e2.getX()>50) && (Math.abs(velocityX) > 20) ){
			viewFlipperd.setInAnimation(leftInAnimationd);
			viewFlipperd.setOutAnimation(leftOutAnimationd);
		    viewFlipperd.showNext();//���һ���
		    switch(viewFlipperd.getDisplayedChild())
      	  {
      	  case 0:
      		buttonviewbackded.setText("   2S   ");
      		  break;
      	  case 1:
      		buttonviewbackded.setText("   3S   ");
      		  break;
      	  case 2:
      		buttonviewbackded.setText("   4S   ");
      		  break;
      	  case 3:
      		buttonviewbackded.setText("   6S  ");
      		  break;
      	  
      	  }
		    return true;
		}else if((e2.getX()-e1.getX()>50) && ( Math.abs(velocityX) > 20)){
			viewFlipperd.setInAnimation(rightInAnimationd);
			viewFlipperd.setOutAnimation(rightOutAnimationd);
			viewFlipperd.showPrevious();//���󻬶�
			switch(viewFlipperd.getDisplayedChild())
      	  {
      	  case 0:
      		buttonviewbackded.setText("   2S   ");
      		  break;
      	  case 1:
      		buttonviewbackded.setText("   3S  ");
      		  break;
      	  case 2:
      		buttonviewbackded.setText("   4S ");
      		  break;
      	  case 3:
      		buttonviewbackded.setText("   6S   ");
      		  break;
      
      	  }
			return true;
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

		
	}
	public void onDoubleClick(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
    
    
}
