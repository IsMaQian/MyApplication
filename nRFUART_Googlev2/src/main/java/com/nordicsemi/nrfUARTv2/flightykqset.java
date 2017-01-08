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
public class flightykqset extends Activity implements OnGestureListener {
	
	
	private ViewFlipper viewFlipperykq;
	private GestureDetector detectorykq; //���Ƽ��
	
	Animation leftInAnimationy;
	Animation leftOutAnimationy;
	Animation rightInAnimationy;
	Animation rightOutAnimationy;
	Button buttonviewbackykqed;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.viewflipperykq);
         
        buttonviewbackykqed= (Button) findViewById(R.id.buttonviewbackykq);
        viewFlipperykq = (ViewFlipper)findViewById(R.id.viewFlippery);
        detectorykq = new GestureDetector(this);
        //���ƾ��α߿�
        GradientDrawable drawabley = new GradientDrawable();  
        drawabley.setShape(GradientDrawable.RECTANGLE); // ����  RECTANGLE 
        drawabley.setStroke(3, Color.rgb(105, 105, 105)); // �߿��ϸ����ɫ  
        drawabley.setCornerRadius(20.0f); // �߿�Բ�ǰ뾶
        drawabley.setColor(0x00000000); // �߿��ڲ���ɫ  
        buttonviewbackykqed.setBackgroundDrawable(drawabley);  
        //��viewFlipper���View
        viewFlipperykq.addView(getImageView(R.drawable.new_feature_1));
        viewFlipperykq.addView(getImageView(R.drawable.new_feature_2));
        viewFlipperykq.addView(getImageView(R.drawable.new_feature_3));
        viewFlipperykq.addView(getImageView(R.drawable.new_feature_4));
        viewFlipperykq.addView(getImageView(R.drawable.new_feature_5));
        viewFlipperykq.addView(getImageView(R.drawable.new_feature_6));
      
        //����Ч��
    	leftInAnimationy = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
		leftOutAnimationy = AnimationUtils.loadAnimation(this, R.anim.push_left_out);
		rightInAnimationy = AnimationUtils.loadAnimation(this, R.anim.push_right_in);
		rightOutAnimationy = AnimationUtils.loadAnimation(this, R.anim.push_right_out);
		
		buttonviewbackykqed.setOnClickListener(new View.OnClickListener() {
	          
	          @Override
	          public void onClick(View v) {
	        	  
	        	  modeltype.Index_YKQType = (short) viewFlipperykq.getDisplayedChild();
	        	  switch(modeltype.Index_YKQType)
	        	  {
	        	  
	        	  case 0:
	        		  modeltype.textViewykqseted.setText("   14SG   ");
	          		  break;
	          	  case 1:
	          		modeltype.textViewykqseted.setText("   ��ط�   ");
	          		  break;
	          	  case 2:
	          		modeltype.textViewykqseted.setText("   ����   ");
	          		  break;
	          	  case 3:
	          		modeltype.textViewykqseted.setText("   ����   ");
	          		  break;
	          	  
	        	  }
	        	  
	        	  Intent intent = getIntent();
	        	  flightykqset.this.setResult(0, intent);
	        	  flightykqset.this.finish();
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
     
    	return this.detectorykq.onTouchEvent(event); //touch�¼��������ƴ���
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
			viewFlipperykq.setInAnimation(leftInAnimationy);
			viewFlipperykq.setOutAnimation(leftOutAnimationy);
		    viewFlipperykq.showNext();//���һ���
		    switch(viewFlipperykq.getDisplayedChild())
      	  {
      	  case 0:
      		  buttonviewbackykqed.setText("   14SG   ");
      		  break;
      	  case 1:
      		buttonviewbackykqed.setText("   ��ط�   ");
      		  break;
      	  case 2:
      		buttonviewbackykqed.setText("   ����   ");
      		  break;
      	  case 3:
      		buttonviewbackykqed.setText("   ����   ");
      		  break;
      	  
      	  }
		    return true;
		}else if((e2.getX()-e1.getX()>50) && ( Math.abs(velocityX) > 20)){
			viewFlipperykq.setInAnimation(rightInAnimationy);
			viewFlipperykq.setOutAnimation(rightOutAnimationy);
			viewFlipperykq.showPrevious();//���󻬶�
			switch(viewFlipperykq.getDisplayedChild())
      	  {
      	  case 0:
      		  buttonviewbackykqed.setText("   14SG   ");
      		  break;
      	  case 1:
      		buttonviewbackykqed.setText("   ��ط�   ");
      		  break;
      	  case 2:
      		buttonviewbackykqed.setText("   ����   ");
      		  break;
      	  case 3:
      		buttonviewbackykqed.setText("   ����   ");
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
