package com.codepath.apps.twitterclient;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.codepath.apps.twitterclient.models.User;

public class PostTweetActivity extends Activity { 
 
	private volatile boolean userInfoFound = false;
	private ImageView imgProfile;
	private TextView tvProfile;
	private EditText etBody; 
	private Button btSubmit;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_tweet);		
		btSubmit = (Button) findViewById(R.id.btSubmit);
        btSubmit.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFF66B2FF));
		imgProfile = (ImageView) findViewById(R.id.imgProfile);
		tvProfile = (TextView) findViewById(R.id.tvProfile);		
		etBody = (EditText) findViewById(R.id.etBody);
		
		if (!userInfoFound) {
			TwitterClientApp.getRestClient().getUserInfo(
				    new JsonHttpResponseHandler() {
				        @Override
						public void onFailure(Throwable arg0, JSONArray arg1) { 
							Log.d("DEBUG", "failed1!: " + arg0 + " " + arg1);
						}
	
						@Override
						public void onFailure(Throwable arg0, JSONObject arg1) {
							Log.d("DEBUG", "failed2!" + arg0 + " " + arg1);    
						}
		 
						@Override  
				        public void onSuccess(JSONObject userInfo) {
							userInfoFound = true; 
				        	Log.d("DEBUG object", userInfo.toString());
				        	User user = User.fromJson(userInfo);
				        	tvProfile.setText(" @" + user.getScreenName());
				        	ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), imgProfile);
				        }  
										
				    });
		}
	}
 
	@Override 
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post_tweet, menu);
		return true;
	}

	public void onCancel(View v) {
	    finish();	
	}
	 
	public void onSubmit(View v) {
		String tweet = etBody.getText().toString();
		if (tweet.isEmpty()) { 
			return;
		}
		TwitterClientApp.getRestClient().postTweet(etBody.getText().toString(),
	        new JsonHttpResponseHandler() {
			    @Override 
	            public void onSuccess(JSONObject status) {
                    Log.d("DEBUG", "Tweet uploaded successfully " + status);
                    Intent i = new Intent();
                    setResult(RESULT_OK, i);
                    finish();
	            }  
			    @Override 
	            public void onFailure(Throwable error, JSONObject status) {
                    Toast.makeText(getBaseContext(), "Could not upload tweet " + status, Toast.LENGTH_SHORT).show(); 
                    finish();
	            }
		    });
	}
	
}
