package com.codepath.apps.twitterclient.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Tweets")
public class Tweet extends Model {
	
	BaseModel baseModel;
	
	@Column(name = "User")
    private User user;

	@Column(name = "Body")
	private String body;
	
	public Tweet() {
		super();
		baseModel = new BaseModel();
	}
	
    public User getUser() {
        return user;
    }
 
    public String getBody() {
    	if (body == null) {
            body = baseModel.getString("text");
    	}
        return body;
    }

    public boolean isFavorited() {
        return baseModel.getBoolean("favorited");
    }

    public boolean isRetweeted() {
        return baseModel.getBoolean("retweeted");
    }

    public String getCreatedAt() {
        return baseModel.getString("created_at");	
    }
  
    
    //@Override
    public void save1() {
    	if (user != null) {
    	    user.save();
    	}
    	super.save();
    }
   
    
    public String toString() {
    	return "body: " + body + " user: " + (user == null? "null" : user);
    }
    
    public static Tweet fromJson(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        try {
            tweet.baseModel.jsonObject = jsonObject;
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
            tweet.getBody();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return tweet;
    }

    public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Tweet tweet = Tweet.fromJson(tweetJson);
            if (tweet != null) {
                tweets.add(tweet);
            }
        }

        return tweets;
    }

	
}
