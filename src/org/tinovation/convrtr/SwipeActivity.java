package org.tinovation.convrtr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fathzer.soft.javaluator.DoubleEvaluator;


public class SwipeActivity extends Activity {
//	
//	String massUnits[] = {"kilograms", "pounds", "grams", "tons", "ounces"};
	
//	ArrayAdapter<String> massArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, massUnits);
//	massArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
//	leftSpinner.setAdapter(massArrayAdapter);
	
	protected Spinner mLeft,mTop,mBottom,mRight;
	protected EditText mCenterText;
	protected JSONObject jObject = null;
	protected String[] distance=null,mass=null,volume=null,time=null;
	protected LinearLayout mContainer;
	protected Double value = null;
	protected String unit = null;
	protected int precision = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_swipe);
		
		mContainer = (LinearLayout)findViewById(R.id.container);
		
//		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//		RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//		intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 100);
//		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
//		startActivityForResult(intent,1);
		 
		
//		 Log.d("helo","" + SpeechRecognizer.isRecognitionAvailable(this));
		//leftSpinner = (Spinner)findViewById(R.id.spinner1);
		
//		HashMap<String,String> shit = new HashMap<String,String>();
//		shit.put("centimeters", "cm");
		
		
		
		
		// Array of choices
		mLeft = (Spinner) findViewById(R.id.spinner_left);
		mRight = (Spinner)findViewById(R.id.spinner_right);
		mTop = (Spinner)findViewById(R.id.spinner_top);
		mBottom = (Spinner)findViewById(R.id.spinner_bottom);
		

		mCenterText = (EditText)findViewById(R.id.value_field);
		Typeface type = Typeface.createFromAsset(getAssets(),"fonts/" + "BebasNeue.otf"); 
		mCenterText.setTypeface(type);
		
		mContainer.setOnTouchListener(new OnSwipeTouchListener(this){
			public void onSwipeTop(){
				String currUnit = mTop.getSelectedItem().toString();
				String text = mCenterText.getText().toString();
        		
        		UnitVerifier uv = new UnitVerifier();
        		unit = uv.getAbbreviatedUnit(unit);
        		
        		PerformUnitCalculation fuck = new PerformUnitCalculation();
        		fuck.from_type = unit;
        		fuck.from_value = value.toString();
        		fuck.to_type = currUnit;
        		fuck.execute(); // -->this shit does everything
        		
        		Animation slide = AnimationUtils.loadAnimation(SwipeActivity.this, R.anim.slidetop);
        		mCenterText.startAnimation(slide);
			}
			public void onSwipeLeft(){
				String currUnit = mLeft.getSelectedItem().toString();
				String text = mCenterText.getText().toString();
				
        		UnitVerifier uv = new UnitVerifier();
        		unit = uv.getAbbreviatedUnit(unit);
        		
        		PerformUnitCalculation fuck = new PerformUnitCalculation();
        		fuck.from_type = unit;
        		fuck.from_value = value.toString();
        		fuck.to_type = currUnit;
        		fuck.execute();
        		
        		Animation slide = AnimationUtils.loadAnimation(SwipeActivity.this, R.anim.slideoutleft);
        		mCenterText.startAnimation(slide);

			}
			public void onSwipeRight(){
				String currUnit = mRight.getSelectedItem().toString();
				Log.d("bitch","bitch");
				String text = mCenterText.getText().toString();

        		UnitVerifier uv = new UnitVerifier();
        		unit = uv.getAbbreviatedUnit(unit);
        		
        		PerformUnitCalculation fuck = new PerformUnitCalculation();
        		fuck.from_type = unit;
        		fuck.from_value = value.toString();
        		fuck.to_type = currUnit;
        		fuck.execute();
        		
        		Animation slide = AnimationUtils.loadAnimation(SwipeActivity.this, R.anim.slideinright);
        		mCenterText.startAnimation(slide);
			}
			public void onSwipeBottom(){
				String currUnit = mBottom.getSelectedItem().toString();
				String text = mCenterText.getText().toString();
				
        		UnitVerifier uv = new UnitVerifier();
        		unit = uv.getAbbreviatedUnit(unit);
        		
        		PerformUnitCalculation fuck = new PerformUnitCalculation();
        		fuck.from_type = unit;
        		fuck.from_value = value.toString();
        		fuck.to_type = currUnit;
        		Log.d("shit",fuck.from_type + " " + fuck.from_value + " " + fuck.to_type);
        		fuck.execute();
        		
        		Animation slide = AnimationUtils.loadAnimation(SwipeActivity.this, R.anim.swipedown);
        		mCenterText.startAnimation(slide);
   
			}
		});
		
		
		mCenterText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	            	if (actionId == EditorInfo.IME_ACTION_DONE) {
	            	
	        		String text = mCenterText.getText().toString();
	        		
	        		if(text.isEmpty() || (text.split("\\s+").length != 2)){
	        			showErrorDialog();
	        		}
	        		else{
	        			
	        			
	        		
	        			value = new DoubleEvaluator().evaluate((text.split("\\s+")[0]));
	        			unit = text.split("\\s+")[1];
	        			
	        			readJSON();
	        			
	        			UnitVerifier uv = new UnitVerifier();
		        		unit = uv.getAbbreviatedUnit(unit);
		        		if(unit.equals("ERROR")){
		        			showErrorDialog();
		        		}
		        		else{
		        		
			        		mCenterText.setText(value + " " + unit);
			        		
			        		Log.d("abbrev unit: ", unit);
			        		
			        		Log.d("category", getCategory(unit));
			        		
			        		
			        		populate(getCategory(unit));
			        		
			        		
			        		
			        		final Animation out = new AlphaAnimation(0.0f,1.0f);
							out.setDuration(1000);
							
							out.setAnimationListener(new AnimationListener() {
								
								@Override
								public void onAnimationStart(Animation animation) {
									
								}
								
								@Override
								public void onAnimationRepeat(Animation animation) {
									
								}
								
								@Override
								public void onAnimationEnd(Animation animation) {
									mLeft.setVisibility(View.VISIBLE);
									mRight.setVisibility(View.VISIBLE);
									mBottom.setVisibility(View.VISIBLE);
									mTop.setVisibility(View.VISIBLE);
								}
							});
			        		
			        		if(mLeft.getVisibility() == View.INVISIBLE){
			        			mLeft.startAnimation(out);
			        			mRight.startAnimation(out);
			        			mBottom.startAnimation(out);
			        			mTop.startAnimation(out);
			        		}
			        		
			        		
			        		

		        		}
	        		}
        			
	        		
	        		
	        		
	        		
//	         		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, convertJSONArray(distance));
//	         		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
//	         		mLeft.setAdapter(spinnerArrayAdapter);
	        		
	        		
	        		
	        		//close the fucking keyboard
	        		InputMethodManager imm = (InputMethodManager)getSystemService(
	        			      Context.INPUT_METHOD_SERVICE);
	        			imm.hideSoftInputFromWindow(mCenterText.getWindowToken(), 0);
	        		
//	        		Log.d("value: ",value.toString());
//	        		Log.d("unit: ",unit);
	        		
	         
	                return true;
	            }
	            
        		
	            return false;
	        }
	    });
		
//		String text = mCenterText.getText().toString();
//		Double value = Double.parseDouble(text.split("\\s+")[0]);
//		String unit = text.split("\\s+")[1];
//		
//		Log.d("value: ",value.toString());
//		Log.d("unit: ",unit);
		
		
		
		
		
		
		//readJSON();
	}
	
//	    spinnerArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,a);
//	    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//	    return spinnerArrayAdapter;
	
	private String[] shuffle(String[] b)
	{
		String[] a = new String[b.length];
		for(int i = 0; i < b.length; i++){
			a[i] = b[i];
		}
		
	    int n = a.length;
	    for (int i = 0; i < n; i++)
	    {
	        // between i and n-1
	        int r = i + (int) (Math.random() * (n-i));
	        String tmp = a[i];    // swap
	        a[i] = a[r];
	        a[r] = tmp;
	    }
	    return a;
	}
	
	
	private void populate(String category){
		MyArrayAdapter<String> spinnerArrayAdapter;
		
		if(category.equals("distance")){
			spinnerArrayAdapter = new MyArrayAdapter<String>(this,android.R.layout.simple_spinner_item,distance);
			spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mLeft.setAdapter(spinnerArrayAdapter);
			
			spinnerArrayAdapter = new MyArrayAdapter<String>(this,android.R.layout.simple_spinner_item,shuffle(distance));
			spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mRight.setAdapter(spinnerArrayAdapter);
			
			spinnerArrayAdapter = new MyArrayAdapter<String>(this,android.R.layout.simple_spinner_item,shuffle(distance));
			spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mTop.setAdapter(spinnerArrayAdapter);
			
			
			spinnerArrayAdapter = new MyArrayAdapter<String>(this,android.R.layout.simple_spinner_item,shuffle(distance));
			spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mBottom.setAdapter(spinnerArrayAdapter);
		}
		if(category.equals("mass")){
			
			spinnerArrayAdapter = new MyArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mass);
			spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mLeft.setAdapter(spinnerArrayAdapter);
			
			spinnerArrayAdapter = new MyArrayAdapter<String>(this,android.R.layout.simple_spinner_item,shuffle(mass));
			spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mRight.setAdapter(spinnerArrayAdapter);
			
			spinnerArrayAdapter = new MyArrayAdapter<String>(this,android.R.layout.simple_spinner_item,shuffle(mass));
			spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mTop.setAdapter(spinnerArrayAdapter);
			
			spinnerArrayAdapter = new MyArrayAdapter<String>(this,android.R.layout.simple_spinner_item,shuffle(mass));
			spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mBottom.setAdapter(spinnerArrayAdapter);
			
		}
		if(category.equals("volume")){
			
			spinnerArrayAdapter = new MyArrayAdapter<String>(this,android.R.layout.simple_spinner_item,volume);
			spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mLeft.setAdapter(spinnerArrayAdapter);
			
			spinnerArrayAdapter = new MyArrayAdapter<String>(this,android.R.layout.simple_spinner_item,shuffle(volume));
			spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mRight.setAdapter(spinnerArrayAdapter);
			
			spinnerArrayAdapter = new MyArrayAdapter<String>(this,android.R.layout.simple_spinner_item,shuffle(volume));
			spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mTop.setAdapter(spinnerArrayAdapter);
			
			spinnerArrayAdapter = new MyArrayAdapter<String>(this,android.R.layout.simple_spinner_item,shuffle(volume));
			spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mBottom.setAdapter(spinnerArrayAdapter);	
		}
		if(category.equals("time")){
			
			spinnerArrayAdapter = new MyArrayAdapter<String>(this,android.R.layout.simple_spinner_item,time);
			spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mLeft.setAdapter(spinnerArrayAdapter);
			
			spinnerArrayAdapter = new MyArrayAdapter<String>(this,android.R.layout.simple_spinner_item,shuffle(time));
			spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mRight.setAdapter(spinnerArrayAdapter);
			
			spinnerArrayAdapter = new MyArrayAdapter<String>(this,android.R.layout.simple_spinner_item,shuffle(time));
			spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mTop.setAdapter(spinnerArrayAdapter);
			
			spinnerArrayAdapter = new MyArrayAdapter<String>(this,android.R.layout.simple_spinner_item,shuffle(time));
			spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mBottom.setAdapter(spinnerArrayAdapter);
		}
	}
	
	private String getCategory(String unit){
		for(int i = 0; i < distance.length;i++){
			if(unit.equals(distance[i])){
				return "distance";
			}
		}
		for(int i = 0; i < mass.length;i++){
			if(unit.equals(mass[i])){
				return "mass";
			}
		}
		for(int i = 0; i < volume.length;i++){
			if(unit.equals(volume[i])){
				return "volume";
			}
		}
		for(int i = 0; i < time.length;i++){
			if(unit.equals(time[i])){
				return "time";
			}
		}
		
		return null;
	}
	
	private void readJSON(){
		
		InputStream is = null;
		JSONArray jDistance=null,jMass=null,jVolume=null,jTime=null;
		
		
		try {
			is = getAssets().open("units.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String line = "";
	    StringBuilder total = new StringBuilder();
	    
	    // Wrap a BufferedReader around the InputStream
	    BufferedReader rd = new BufferedReader(new InputStreamReader(is));

	    // Read response until the end
	    try {
			while ((line = rd.readLine()) != null) { 
			    total.append(line); 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    try {
			jObject = new JSONObject(total.toString());
			jDistance = jObject.getJSONArray("length");
			jMass = jObject.getJSONArray("mass");
			jVolume = jObject.getJSONArray("volume");
			jTime = jObject.getJSONArray("time");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    
	    distance = convertJSONArray(jDistance);
	    mass = convertJSONArray(jMass);
	    volume = convertJSONArray(jVolume);
	    time = convertJSONArray(jTime);
	    

 		// Application of the Array to the Spinner

	    
	    
	}
	
	private String[] convertJSONArray(JSONArray arr){
		String[] newArr = new String[arr.length()];
		for(int i = 0; i < arr.length(); i++){
			try {
				newArr[i] = arr.getString(i);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return newArr;
	}
	
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==1  && resultCode==RESULT_OK) {
//        		ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//        		((TextView)findViewById(R.id.textView1)).setText(thingsYouSaid.get(0));
//        }
//    }
	
	private class PerformUnitCalculation extends AsyncTask<URL, Integer, Long> {
		
		InputStream is = null;
		String from_value="",from_type="",to_type="";
//		
	     protected Long doInBackground(URL... urls) {
	    	 
//	    	 HttpClient httpclient = new DefaultHttpClient();
//	    	 HttpResponse response = null;
//	    	 
//	    	 HttpPost httppost = new HttpPost("http://api.wolframalpha.com/v2/query?" + "input="
//	    			 + "convert" + "+" + from_value + "+" + from_type + "+" + "to" + "+" + to_type
//	    			 + "&appid=47XUTE-UUA43YAXYW");
//	    	 
//	    	 try {
//				response = httpclient.execute(httppost);
//			} catch (ClientProtocolException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//	    	 
//	    	 try {
//				is = response.getEntity().getContent();
//			} catch (IllegalStateException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//	    	 
//	    	 return response.getEntity().getContentLength();
	    	 
	    	 
	    	 
	    	 
	    	 //47XUTE-UUA43YAXYW
	    	 
//	    	 
	    	 HttpClient httpclient = new DefaultHttpClient();
	    	 HttpResponse response = null;
	    	 
	    	 
	    	 HttpPost httppost = new HttpPost("https://neutrinoapi.com/convert?");
	    	 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	    	 nameValuePairs.add(new BasicNameValuePair("from-value", from_value));
	    	 nameValuePairs.add(new BasicNameValuePair("from-type", from_type));
	    	 nameValuePairs.add(new BasicNameValuePair("to-type", to_type));
	    	 nameValuePairs.add(new BasicNameValuePair("user-id", "sujay"));
	    	 nameValuePairs.add(new BasicNameValuePair("api-key", "CUTDTW33jg3FRXnNnskEpgeiU4RgQfNY"));  
	    	 
	    	 try {
	    		 httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	    		 // Execute HTTP Post Request
	    		 response = httpclient.execute(httppost);
	    		 is = response.getEntity().getContent();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

	    	return response.getEntity().getContentLength();
//	    	 
//	    	 HttpResponse request = Unirest.post("https://community-neutrino-currency-conversion.p.mashape.com/convert")
//	    			  .header("X-Mashape-Authorization", "OigFYm7eqoiVhFjBfR8aBfl7UHquYnaF")
//	    			  .field("from-value", "10")
//	    			  .field("from-type", "NZD")
//	    			  .field("to-type", "GBP")
//	    			  .asJson();
	    	 
//	    	 HttpClient client = new DefaultHttpClient();
//	    	 HttpPost post = new HttpPost("https://community-neutrino-currency-conversion.p.mashape.com/convert");
//	    	 post.setHeader("X-Mashape-Authorization", "OigFYm7eqoiVhFjBfR8aBfl7UHquYnaF");
//	    	
//	    	 List <NameValuePair> nvps = new ArrayList <NameValuePair>();
//	    	 nvps.add(new BasicNameValuePair("from-value", from_value));
//	    	 nvps.add(new BasicNameValuePair("from-type", from_type));
//	    	 nvps.add(new BasicNameValuePair("to-type", to_type));
//	    	 
//	    	 
//	    	 
//	    	AbstractHttpEntity ent = null;
//	    	HttpResponse response = null;
//			try {
//				ent = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
//			} catch (UnsupportedEncodingException e2) {
//				e2.printStackTrace();
//			}
//	    	 
//	    	 ent.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
//	    	 ent.setContentEncoding("UTF-8");
//	    	 post.setEntity(ent);
//	    	 try {
//				post.setURI(new URI("http://myhost.com/test.php"));
//			} catch (URISyntaxException e1) {
//				e1.printStackTrace();
//			}
//	    	 try {
//				response = client.execute(post);
//			} catch (ClientProtocolException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//	    	
//	    	try {
//				is = response.getEntity().getContent();
//			} catch (IllegalStateException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//	    	
//	    	return response.getEntity().getContentLength();
//	    	 
	    	 
	     }

	     protected void onProgressUpdate(Integer... progress) {
	         
	     }

	     protected void onPostExecute(Long result) {
	    	 
	    	String line = "";
		    StringBuilder total = new StringBuilder();
		    
		    // Wrap a BufferedReader around the InputStream
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	
		    // Read response until the end
		    try {
				while ((line = rd.readLine()) != null) { 
				    total.append(line); 
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		    		    
		    try {
				JSONObject jObject = new JSONObject(total.toString());
				
				Double answer = jObject.getDouble("result");
				Log.d("answer",answer.toString());
				String format = "#.";
				for(int i = 0; i < precision; i++) {
					format += "#";
				}
				DecimalFormat df = new DecimalFormat(format);
				mCenterText.setText(df.format(answer) + " " + to_type);
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		    
//		    String answer = "";
//		    
//		    XmlPullParserFactory factory = null;
//	        XmlPullParser xpp= null;
//			try {
//				factory =  XmlPullParserFactory.newInstance();
//				xpp = factory.newPullParser();
//				xpp.setInput( new StringReader ( total.toString() ) );
//				int eventType = xpp.getEventType();
//				
//		         while (eventType != XmlPullParser.END_DOCUMENT) {
//			        	 
//			         if(eventType == XmlPullParser.START_TAG) {
//			        	 if(xpp.getName().equals("pod")){
//			        		//Log.d("shit",xpp.getAttributeValue(0));
//			        		 if(xpp.getAttributeValue(0).equals("Result")){
//			        			 while(!xpp.getName().equals("plaintext")){
//			        				 eventType = xpp.next();
//			        			 }
//			        			 answer = xpp.getText();
//			        			 break;
//			        		 }
//			        	 }
//			         }
//		          eventType = xpp.next();
//		         }
//			} catch (XmlPullParserException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//	          Log.d("bullshit",answer);
	         

	     }
	 }
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.swipe, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	private void showErrorDialog(){
		//AlertDialog shit
		AlertDialog.Builder builder = new AlertDialog.Builder(SwipeActivity.this);
		builder.setMessage(R.string.screw_up_message);
		builder.setIcon(R.drawable.caution);
		builder.setTitle(R.string.error);
		builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mCenterText.setText("");
				dialog.cancel();
			}
		});
		builder.show();
	}
	
	private class MyArrayAdapter<T> extends ArrayAdapter<T> {
		
		Typeface type = Typeface.createFromAsset(getAssets(),"fonts/" + "BebasNeue.otf");
		
		public MyArrayAdapter(Context context, int textViewResourceId,T[] objects) {
			super(context, textViewResourceId,objects);
		}

		public TextView getView(int position, View convertView, ViewGroup parent) {
			TextView v = (TextView) super.getView(position, convertView, parent);
			v.setTextSize(25);
			v.setTypeface(type);
			return v;
		}

		public TextView getDropDownView(int position, View convertView, ViewGroup parent) {
			TextView v = (TextView) super.getView(position, convertView, parent);
			v.setTypeface(type);
			return v;
		}

	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		    case R.id.action_settings:
		    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    	LayoutInflater factory = LayoutInflater.from(this);
		    	final View textEntryView = factory.inflate(R.layout.alert_dialog_text_entry, null);
		    	final Spinner shit = (Spinner)textEntryView.findViewById(R.id.sig_fig_spinner);
		    	String[] things = {"1","2","3","4","5","6","7"};
		    	MyArrayAdapter<String> adapter = new MyArrayAdapter<String>(this,android.R.layout.simple_spinner_item,things);
		    	shit.setAdapter(adapter);
		    	builder.setTitle(R.string.num_sig_figs_message);
		    	builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						precision = Integer.parseInt((String)shit.getSelectedItem());
					}
				});
		    	builder.setView(textEntryView);
		    	builder.show();
		      break;
		    default:
		      break;
		    }
		
		return false;
	}
	

}
