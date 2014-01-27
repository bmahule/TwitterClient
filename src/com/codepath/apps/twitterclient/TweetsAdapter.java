package com.codepath.apps.twitterclient;

import java.util.List; 

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;  

import com.nostra13.universalimageloader.core.ImageLoader;
import com.codepath.apps.twitterclient.models.*;

public class TweetsAdapter extends ArrayAdapter<Tweet> {

	public TweetsAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) { 
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.tweet_item, null);
		}
		Tweet t = getItem(position);
		
		ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);
		ImageLoader.getInstance().displayImage(t.getUser().getProfileImageUrl() , ivProfile);
		
		TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
		String formattedName = "<b>" + t.getUser().getName() + "</b> <small><font color='#777777'>@" + 
		    t.getUser().getScreenName() + "</font></small>";
		tvName.setText(Html.fromHtml(formattedName));
		
		TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
		tvBody.setText(Html.fromHtml(t.getBody()));
		 
		return convertView;
	}
	
}
