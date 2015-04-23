package CIDR.HuesNews;

//public class TwitterBot {
//
//}
//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStreamReader;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;




public class TwitterBot
{
    private final static String CONSUMER_KEY = "hGZi2WBflbse1abdnUT0xjEIM";
    private final static String CONSUMER_KEY_SECRET = "T1WJoGm754hDNymdrx5SS8DXj4F5J9A08SEdnNsyyKqk32RrlQ";




    public void tweet(String tweet) throws TwitterException, IOException {

        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
        String accessT = getSavedAccessToken();
        String accessTSecret = getSavedAccessTokenSecret();
        AccessToken oathAccessToken = new AccessToken(accessT,accessTSecret);
        twitter.setOAuthAccessToken(oathAccessToken);
        twitter.updateStatus(tweet );

    }
    private String getSavedAccessTokenSecret(){

        return "";
    }

    private String getSavedAccessToken(){
        return "";

    }


}
