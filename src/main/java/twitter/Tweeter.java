// Modified code by Diarmuid Ryan (11363776), taken from Twitter4J Tutorial at JavaCodeGeeks.com
package twitter;

import twitter4j.TwitterException;
import twitter4j.ResponseList;
import twitter4j.Status;

public class Tweeter extends TwitterBot {
	
	public Tweeter(String configFilename) {
		super(configFilename);
	}

	public String readTimeline() {
		String retVal = "\nMy Timeline:\n";
		try {
			//System.out.println("\nMy Timeline:");
			ResponseList<Status> list = twitter.getHomeTimeline();
			for (Status each : list) {
				retVal += "Sent by: @" + each.getUser().getScreenName()
						+ " - " + each.getUser().getName() + "\n" + each.getText()
						+ "\n\n";
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	
	public void post(String p) {
		try {
			twitter.updateStatus(p);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		Tweeter t = new Tweeter("twitter_config.txt");
		//t.post("I'd really rather not be here so late");
		System.out.println(t.readTimeline());
	}
}
