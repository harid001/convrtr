package org.tinovation.convrtr;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.*;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

public class TitleActivity extends ActionBarActivity {
	
	protected TextView mMotto,mTouchToStart;
	protected ViewGroup mContainer;
	protected ImageView mLogo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		
		
		
		
		mMotto = (TextView)findViewById(R.id.motto_text_view);
		setFont(mMotto, "BebasNeue.otf");
		mTouchToStart = (TextView)findViewById(R.id.swipe_to_start_text_view);
		setFont(mTouchToStart,"BebasNeue.otf");
		mLogo = (ImageView)findViewById(R.id.title_image);
		mContainer = (ViewGroup)findViewById(R.id.container);
		mMotto.setTextColor(getResources().getColor(R.color.motto_text_color));
		mContainer.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				//init fade out effect
				final Animation out = new AlphaAnimation(1.0f,0.0f);
				out.setDuration(1000);

				out.setAnimationListener(new Animation.AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {

					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						mMotto.setVisibility(View.INVISIBLE);
						mTouchToStart.setVisibility(View.INVISIBLE);
						mLogo.setVisibility(View.INVISIBLE);
						Intent intent = new Intent(TitleActivity.this,SwipeActivity.class);
						startActivity(intent);
						
					}
				});
				
				if(mMotto.getVisibility() != View.INVISIBLE && mTouchToStart.getVisibility() != View.INVISIBLE && mLogo.getVisibility() != View.INVISIBLE){
					mMotto.startAnimation(out);
					mTouchToStart.startAnimation(out);
					mLogo.startAnimation(out);
					
				}
				
				return false;
			}
		});

	}
	
	private void setFont(TextView v, String fontname){
		Typeface type = Typeface.createFromAsset(getAssets(),"fonts/" + fontname); 
		v.setTypeface(type);
	}


	
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.swipe, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	 
}
