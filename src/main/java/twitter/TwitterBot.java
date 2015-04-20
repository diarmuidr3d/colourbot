package twitter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterBot {
	protected String CONSUMER_KEY;
	protected String CONSUMER_KEY_SECRET;
	protected String ACCESS_TOKEN;
	protected String ACCESS_TOKEN_SECRET;
	protected AccessToken oathAccessToken;
	protected Twitter twitter;
	private final String resource = "resource"+File.separatorChar;
	
	public TwitterBot(String configFilename) {
		CONSUMER_KEY = null;
		CONSUMER_KEY_SECRET = null;
		ACCESS_TOKEN = null;
		ACCESS_TOKEN_SECRET = null;
		twitter = new TwitterFactory().getInstance();
		File configFile = new File(resource+configFilename);
		if (configFile.canRead()) {
			configFromFile(configFile);
			writeKeys(configFile);
		} else {
			setupKeys();
		}
	}
	
	private void writeKeys(File configFile) {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFile)));
			bw.write("CONSUMER_KEY = "+CONSUMER_KEY+"\n"
					+ "CONSUMER_KEY_SECRET = "+CONSUMER_KEY_SECRET+"\n"
					+ "ACCESS_TOKEN = "+ACCESS_TOKEN+"\n"
					+ "ACCESS_TOKEN_SECRET = "+ACCESS_TOKEN_SECRET);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setupKeys() {
		if ((CONSUMER_KEY != null) && (CONSUMER_KEY_SECRET != null)) twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
		else {
			System.out.println("Error: Incorrect config file, Consumer keys not specified");
			getConsumerKeys();
		}
		if ((ACCESS_TOKEN == null) || (ACCESS_TOKEN_SECRET == null)) authorise(twitter);
		oathAccessToken = new AccessToken(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
		twitter.setOAuthAccessToken(oathAccessToken);
	}
	
	private void getConsumerKeys() {
		try {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (null == CONSUMER_KEY) {
			System.out.print("Input Consumer Key here: ");
			CONSUMER_KEY = br.readLine();
			CONSUMER_KEY = CONSUMER_KEY.trim();
		}
		while (null == CONSUMER_KEY_SECRET) {
			System.out.print("Input Consumer Secret Key here: ");
			CONSUMER_KEY_SECRET = br.readLine();
			CONSUMER_KEY_SECRET = CONSUMER_KEY_SECRET.trim();
		}
		} catch (IOException e) {
			System.err.println("Error getting input");
			e.printStackTrace();
		}
	}
	
	private void configFromFile(File configFile) {
		try {
			BufferedReader configReader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
			String next, word;
			while (((next = configReader.readLine()) != null)) {
				word = next.substring(0, next.indexOf('='));
				word = word.trim();
				if(word.equals("CONSUMER_KEY")) {
					word = next.substring(next.indexOf('=')+1);
					word = word.trim();
					CONSUMER_KEY = word;
				} else if(word.equals("CONSUMER_KEY_SECRET")) {
					word = next.substring(next.indexOf('=')+1);
					word = word.trim();
					CONSUMER_KEY_SECRET = word;
				} else if(word.equals("ACCESS_TOKEN")) {
					word = next.substring(next.indexOf('=')+1);
					word = word.trim();
					ACCESS_TOKEN = word;
				} else if(word.equals("ACCESS_TOKEN_SECRET")) {
					word = next.substring(next.indexOf('=')+1);
					word = word.trim();
					ACCESS_TOKEN_SECRET = word;
				} 
			}
			setupKeys();
			configReader.close();
		} catch (IOException e) {
			System.err.println("Error reading from config file in Tweeter construct");
			e.printStackTrace();
		}
	}
	
	private void authorise(Twitter twitter)  {
		try {
		RequestToken requestToken = twitter.getOAuthRequestToken();
		System.out.println("Authorization URL: \n" + requestToken.getAuthorizationURL());
		AccessToken accessToken = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (null == accessToken) {
			System.out.print("Input PIN here: ");
			String pin = br.readLine();
			accessToken = twitter.getOAuthAccessToken(requestToken, pin);
			
		}
		System.out.println("Access Token: " + accessToken.getToken());
		System.out.println("Access Token Secret: " + accessToken.getTokenSecret());
		twitter.updateStatus("Authorised");
		} catch (TwitterException e) {
			System.out.println("Failed to get access token, caused by: " + e.getMessage());
			System.out.println("Retry input PIN");
			e.printStackTrace();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
}
