package com.codepath.apps.twitterclient.models;

import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Users")
public class User extends Model {
	
	BaseModel baseModel;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="ScreenName")
	private String screenName;
	
	@Column(name="ProfileImageUrl")
	private String profileImageUrl;
	
	public User() {
		super();
		baseModel = new BaseModel();
	}
	 
    public String getName() {
    	if (name == null) {
    	    name = baseModel.getString("name");
    	}
        return name;
    } 

    public String getScreenName() {
    	if (screenName == null) {
    	    screenName = baseModel.getString("screen_name");
    	}
    	return screenName;
    }

    public String getProfileBackgroundImageUrl() {
        return baseModel.getString("profile_background_image_url");
    }

    public String getProfileImageUrl() {
    	if (profileImageUrl ==  null) {
    	    profileImageUrl = baseModel.getString("profile_image_url");
    	}
    	return profileImageUrl;
    }
    
    public int getNumTweets() {
        return baseModel.getInt("statuses_count");
    }

    public int getFollowersCount() {
        return baseModel.getInt("followers_count");
    }

    public int getFriendsCount() {
        return baseModel.getInt("friends_count");
    }

    public String toString() {
    	return "name: " + name + " screen name " + screenName + "profile url: " + profileImageUrl;
    }
    
    public static User fromJson(JSONObject json) {
        User u = new User();
        try {
        	u.baseModel.jsonObject = json;
            // This is a hacky was to populate the values before persisting the User Object
            u.getProfileImageUrl();
            u.getScreenName();
            u.getName();
        } catch (Exception e) { 
            e.printStackTrace();
        }

        return u;
    }


}
