package com.nordicsemi.nrfUARTv2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
public class flightmodel extends Activity implements OnGestureListener {
	
	
	private ViewFlipper viewFlipper;
	private GestureDetector detector; //���Ƽ��
	
	Animation leftInAnimation;
	Animation leftOutAnimation;
	Animation rightInAnimation;
	Animation rightOutAnimation;
	Button buttonviewbacked;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.viewfilerflight);
         
        buttonviewbacked= (Button) findViewById(R.id.buttonviewback);
        viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);
        detector = new GestureDetector(this);
        //���ƾ��α߿�
        GradientDrawable drawable = new GradientDrawable();  
        drawable.setShape(GradientDrawable.RECTANGLE); // ����  RECTANGLE 
        drawable.setStroke(3, Color.rgb(105, 105, 105)); // �߿��ϸ����ɫ  
        drawable.setCornerRadius(20.0f); // �߿�Բ�ǰ뾶
        drawable.setColor(0x00000000); // �߿��ڲ���ɫ  
        buttonviewbacked.setBackgroundDrawable(drawable);  
        //��viewFlipper���View
        viewFlipper.addView(getImageView(R.drawable.new_feature_1));
        viewFlipper.addView(getImageView(R.drawable.new_feature_2));
        viewFlipper.addView(getImageView(R.drawable.new_feature_3));
        viewFlipper.addView(getImageView(R.drawable.new_feature_4));
        viewFlipper.addView(getImageView(R.drawable.new_feature_5));
        viewFlipper.addView(getImageView(R.drawable.new_feature_6));
        viewFlipper.getDisplayedChild();
        //����Ч��
    	leftInAnimation = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
		leftOutAnimation = AnimationUtils.loadAnimation(this, R.anim.push_left_out);
		rightInAnimation = AnimationUtils.loadAnimation(this, R.anim.push_right_in);
		rightOutAnimation = AnimationUtils.loadAnimation(this, R.anim.push_right_out);
		
		buttonviewbacked.setOnClickListener(new View.OnClickListener() {
	          
	          @Override
	          public void onClick(View v) {
	        	  
	        	  modeltype.Index_FlightQuad = (short) viewFlipper.getDisplayedChild();
	        	  switch(modeltype.Index_FlightQuad)
	        	  {
	        	  case 0:
	        		  modeltype.textViewmodelseted.setText("���������ʱ��");
	        		  break;
	        	  case 1:
	        		  modeltype.textViewmodelseted.setText("�������˳ʱ��");
	        		  break;
	        	  case 2:
	        		  modeltype.textViewmodelseted.setText("����ʮ����ʱ��");
	        		  break;
	        	  case 3:
	        		  modeltype.textViewmodelseted.setText("����ʮ��˳ʱ��");
	        		  break;
	        	  case 4:
	        		  modeltype.textViewmodelseted.setText("����V����ʱ��");
	        		  break;
	        	  case 5:
	        		  modeltype.textViewmodelseted.setText("����V��˳ʱ��");
	        		  break;
	        	  case 6:
	        		  modeltype.textViewmodelseted.setText("����һ����ʱ��");
	        		  break;
	        	  case 7:
	        		  modeltype.textViewmodelseted.setText("����һ��˳ʱ��");
	        		  break;
	        	  case 8:
	        		  modeltype.textViewmodelseted.setText("���������ʱ��");
	        		  break;
	        	  case 9:
	        		  modeltype.textViewmodelseted.setText("�������˳ʱ��");
	        		  break;
	        	  case 10:
	        		  modeltype.textViewmodelseted.setText("����ʮ����ʱ��");
	        		  break;
	        	  case 11:
	        		  modeltype.textViewmodelseted.setText("����ʮ��˳ʱ��");
	        		  break;
	        	  case 12:
	        		  modeltype.textViewmodelseted.setText("���������ʱ��");
	        		  break;
	        	  case 13:
	        		  modeltype.textViewmodelseted.setText("�������˳ʱ��");
	        		  break;
	        	  
	        	  }
	        	  
	        	  Intent intent = getIntent();
	        	  flightmodel.this.setResult(0, intent);
	        	  flightmodel.this.finish();
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
     
    	return this.detector.onTouchEvent(event); //touch�¼��������ƴ���
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
			viewFlipper.setInAnimation(leftInAnimation);
			viewFlipper.setOutAnimation(leftOutAnimation);
		    viewFlipper.showNext();//���һ���
		    switch(viewFlipper.getDisplayedChild())
      	  {
      	  case 0:
      		  buttonviewbacked.setText("   ���������ʱ��   ");
      		  break;
      	  case 1:
      		  buttonviewbacked.setText("   �������˳ʱ��   ");
      		  break;
      	  case 2:
      		  buttonviewbacked.setText("   ����ʮ����ʱ��   ");
      		  break;
      	  case 3:
      		  buttonviewbacked.setText("   ����ʮ��˳ʱ��   ");
      		  break;
      	  case 4:
      		  buttonviewbacked.setText("   ����V����ʱ��   ");
      		  break;
      	  case 5:
      		  buttonviewbacked.setText("   ����V��˳ʱ��   ");
      		  break;
      	  case 6:
      		  buttonviewbacked.setText("   ����һ����ʱ��   ");
      		  break;
      	  case 7:
      		  buttonviewbacked.setText("   ����һ��˳ʱ��   ");
      		  break;
      	  case 8:
      		  buttonviewbacked.setText("   �����������ʱ��   ");
      		  break;
      	  case 9:
      		  buttonviewbacked.setText("   ���������˳ʱ��   ");
      		  break;
      	  case 10:
      		  buttonviewbacked.setText("   ������ʮ����ʱ��   ");
      		  break;
      	  case 11:
      		  buttonviewbacked.setText("   ������ʮ��˳ʱ��   ");
      		  break;
      	  case 12:
      		  buttonviewbacked.setText("   ���������ʱ��   ");
      		  break;
      	  case 13:
      		  buttonviewbacked.setText("   �������˳ʱ��   ");
      		  break;
      
      		  
      	  }
		    return true;
		}else if((e2.getX()-e1.getX()>50) && ( Math.abs(velocityX) > 20)){
			viewFlipper.setInAnimation(rightInAnimation);
			viewFlipper.setOutAnimation(rightOutAnimation);
			viewFlipper.showPrevious();//���󻬶�
			switch(viewFlipper.getDisplayedChild())
      	  {
      	  case 0:
      		  buttonviewbacked.setText("   ���������ʱ��   ");
      		  break;
      	  case 1:
      		  buttonviewbacked.setText("   �������˳ʱ��   ");
      		  break;
      	  case 2:
      		  buttonviewbacked.setText("   ����ʮ����ʱ��   ");
      		  break;
      	  case 3:
      		  buttonviewbacked.setText("   ����ʮ��˳ʱ��   ");
      		  break;
      	  case 4:
      		  buttonviewbacked.setText("   ����V����ʱ��   ");
      		  break;
      	  case 5:
      		  buttonviewbacked.setText("   ����V��˳ʱ��   ");
      		  break;
      	  case 6:
      		  buttonviewbacked.setText("   ����һ����ʱ��   ");
      		  break;
      	  case 7:
      		  buttonviewbacked.setText("   ����һ��˳ʱ��   ");
      		  break;
      	  case 8:
      		  buttonviewbacked.setText("   �����������ʱ��   ");
      		  break;
      	  case 9:
      		  buttonviewbacked.setText("   ���������˳ʱ��   ");
      		  break;
      	  case 10:
      		  buttonviewbacked.setText("   ������ʮ����ʱ��   ");
      		  break;
      	  case 11:
      		  buttonviewbacked.setText("   ������ʮ��˳ʱ��   ");
      		  break;
      	  case 12:
      		  buttonviewbacked.setText("   ���������ʱ��   ");
      		  break;
      	  case 13:
      		  buttonviewbacked.setText("   �������˳ʱ��   ");
      		  break;
      	  }
			return true;
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		int i;
		
		i = viewFlipper.getDisplayedChild();
		
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
