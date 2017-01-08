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
 * ×óÓÒ»¬¶¯demo
 * @author xzw
 *
 */
public class flightmodel extends Activity implements OnGestureListener {
	
	
	private ViewFlipper viewFlipper;
	private GestureDetector detector; //ÊÖÊÆ¼ì²â
	
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
        //»æÖÆ¾ØĞÎ±ß¿ò
        GradientDrawable drawable = new GradientDrawable();  
        drawable.setShape(GradientDrawable.RECTANGLE); // »­¿ò  RECTANGLE 
        drawable.setStroke(3, Color.rgb(105, 105, 105)); // ±ß¿ò´ÖÏ¸¼°ÑÕÉ«  
        drawable.setCornerRadius(20.0f); // ±ß¿òÔ²½Ç°ë¾¶
        drawable.setColor(0x00000000); // ±ß¿òÄÚ²¿ÑÕÉ«  
        buttonviewbacked.setBackgroundDrawable(drawable);  
        //ÍùviewFlipperÌí¼ÓView
        viewFlipper.addView(getImageView(R.drawable.new_feature_1));
        viewFlipper.addView(getImageView(R.drawable.new_feature_2));
        viewFlipper.addView(getImageView(R.drawable.new_feature_3));
        viewFlipper.addView(getImageView(R.drawable.new_feature_4));
        viewFlipper.addView(getImageView(R.drawable.new_feature_5));
        viewFlipper.addView(getImageView(R.drawable.new_feature_6));
        viewFlipper.getDisplayedChild();
        //¶¯»­Ğ§¹û
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
	        		  modeltype.textViewmodelseted.setText("ËÄÖá²æ×ÖÄæÊ±Õë");
	        		  break;
	        	  case 1:
	        		  modeltype.textViewmodelseted.setText("ËÄÖá²æ×ÖË³Ê±Õë");
	        		  break;
	        	  case 2:
	        		  modeltype.textViewmodelseted.setText("ËÄÖáÊ®×ÖÄæÊ±Õë");
	        		  break;
	        	  case 3:
	        		  modeltype.textViewmodelseted.setText("ËÄÖáÊ®×ÖË³Ê±Õë");
	        		  break;
	        	  case 4:
	        		  modeltype.textViewmodelseted.setText("ÁùÖáV×ÖÄæÊ±Õë");
	        		  break;
	        	  case 5:
	        		  modeltype.textViewmodelseted.setText("ÁùÖáV×ÖË³Ê±Õë");
	        		  break;
	        	  case 6:
	        		  modeltype.textViewmodelseted.setText("ÁùÖáÒ»×ÖÄæÊ±Õë");
	        		  break;
	        	  case 7:
	        		  modeltype.textViewmodelseted.setText("ÁùÖáÒ»×ÖË³Ê±Õë");
	        		  break;
	        	  case 8:
	        		  modeltype.textViewmodelseted.setText("°ËÖá²æ×ÖÄæÊ±Õë");
	        		  break;
	        	  case 9:
	        		  modeltype.textViewmodelseted.setText("°ËÖá²æ×ÖË³Ê±Õë");
	        		  break;
	        	  case 10:
	        		  modeltype.textViewmodelseted.setText("°ËÖáÊ®×ÖÄæÊ±Õë");
	        		  break;
	        	  case 11:
	        		  modeltype.textViewmodelseted.setText("°ËÖáÊ®×ÖË³Ê±Õë");
	        		  break;
	        	  case 12:
	        		  modeltype.textViewmodelseted.setText("¹²Öá²æ×ÖÄæÊ±Õë");
	        		  break;
	        	  case 13:
	        		  modeltype.textViewmodelseted.setText("¹²Öá²æ×ÖË³Ê±Õë");
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
     
    	return this.detector.onTouchEvent(event); //touchÊÂ¼ş½»¸øÊÖÊÆ´¦Àí¡£
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
		    viewFlipper.showNext();//ÏòÓÒ»¬¶¯
		    switch(viewFlipper.getDisplayedChild())
      	  {
      	  case 0:
      		  buttonviewbacked.setText("   ËÄÖá²æ×ÖÄæÊ±Õë   ");
      		  break;
      	  case 1:
      		  buttonviewbacked.setText("   ËÄÖá²æ×ÖË³Ê±Õë   ");
      		  break;
      	  case 2:
      		  buttonviewbacked.setText("   ËÄÖáÊ®×ÖÄæÊ±Õë   ");
      		  break;
      	  case 3:
      		  buttonviewbacked.setText("   ËÄÖáÊ®×ÖË³Ê±Õë   ");
      		  break;
      	  case 4:
      		  buttonviewbacked.setText("   ÁùÖáV×ÖÄæÊ±Õë   ");
      		  break;
      	  case 5:
      		  buttonviewbacked.setText("   ÁùÖáV×ÖË³Ê±Õë   ");
      		  break;
      	  case 6:
      		  buttonviewbacked.setText("   ÁùÖáÒ»×ÖÄæÊ±Õë   ");
      		  break;
      	  case 7:
      		  buttonviewbacked.setText("   ÁùÖáÒ»×ÖË³Ê±Õë   ");
      		  break;
      	  case 8:
      		  buttonviewbacked.setText("   °ËĞıÒí²æ×ÖÄæÊ±Õë   ");
      		  break;
      	  case 9:
      		  buttonviewbacked.setText("   °ËĞıÒí²æ×ÖË³Ê±Õë   ");
      		  break;
      	  case 10:
      		  buttonviewbacked.setText("   °ËĞıÒíÊ®×ÖÄæÊ±Õë   ");
      		  break;
      	  case 11:
      		  buttonviewbacked.setText("   °ËĞıÒíÊ®×ÖË³Ê±Õë   ");
      		  break;
      	  case 12:
      		  buttonviewbacked.setText("   ¹²Öá²æ×ÖÄæÊ±Õë   ");
      		  break;
      	  case 13:
      		  buttonviewbacked.setText("   ¹²Öá²æ×ÖË³Ê±Õë   ");
      		  break;
      
      		  
      	  }
		    return true;
		}else if((e2.getX()-e1.getX()>50) && ( Math.abs(velocityX) > 20)){
			viewFlipper.setInAnimation(rightInAnimation);
			viewFlipper.setOutAnimation(rightOutAnimation);
			viewFlipper.showPrevious();//Ïò×ó»¬¶¯
			switch(viewFlipper.getDisplayedChild())
      	  {
      	  case 0:
      		  buttonviewbacked.setText("   ËÄÖá²æ×ÖÄæÊ±Õë   ");
      		  break;
      	  case 1:
      		  buttonviewbacked.setText("   ËÄÖá²æ×ÖË³Ê±Õë   ");
      		  break;
      	  case 2:
      		  buttonviewbacked.setText("   ËÄÖáÊ®×ÖÄæÊ±Õë   ");
      		  break;
      	  case 3:
      		  buttonviewbacked.setText("   ËÄÖáÊ®×ÖË³Ê±Õë   ");
      		  break;
      	  case 4:
      		  buttonviewbacked.setText("   ÁùÖáV×ÖÄæÊ±Õë   ");
      		  break;
      	  case 5:
      		  buttonviewbacked.setText("   ÁùÖáV×ÖË³Ê±Õë   ");
      		  break;
      	  case 6:
      		  buttonviewbacked.setText("   ÁùÖáÒ»×ÖÄæÊ±Õë   ");
      		  break;
      	  case 7:
      		  buttonviewbacked.setText("   ÁùÖáÒ»×ÖË³Ê±Õë   ");
      		  break;
      	  case 8:
      		  buttonviewbacked.setText("   °ËĞıÒí²æ×ÖÄæÊ±Õë   ");
      		  break;
      	  case 9:
      		  buttonviewbacked.setText("   °ËĞıÒí²æ×ÖË³Ê±Õë   ");
      		  break;
      	  case 10:
      		  buttonviewbacked.setText("   °ËĞıÒíÊ®×ÖÄæÊ±Õë   ");
      		  break;
      	  case 11:
      		  buttonviewbacked.setText("   °ËĞıÒíÊ®×ÖË³Ê±Õë   ");
      		  break;
      	  case 12:
      		  buttonviewbacked.setText("   ¹²Öá²æ×ÖÄæÊ±Õë   ");
      		  break;
      	  case 13:
      		  buttonviewbacked.setText("   ¹²Öá²æ×ÖË³Ê±Õë   ");
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
