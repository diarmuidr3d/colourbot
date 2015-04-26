// Modified code by Diarmuid Ryan (11363776), taken from Twitter4J Tutorial at JavaCodeGeeks.com
package twitter;

import twitter4j.TwitterException;
import twitter4j.ResponseList;
import twitter4j.Status;

/**
 * An easier to use Class for TwitterBot, hides some of the (probably) unneeded methods
 * @author Diarmuid Ryan
 *
 */
public class Tweeter extends TwitterBot {
	
	/**
	 * Sets up the TwitterBot
	 * @param configFilename Address of a file in the resource directory containing the keys for this bot, if it doesn't exist it will be created
	 */
	public Tweeter(String configFilename) {
		super(configFilename);
	}

	/**
	 * Gets a representation of the users Twitter timeline
	 * @return A string representation of the users timeline
	 */
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
	
	/**
	 * Posts a string to the user's Twitter Account
	 * @param p
	 */
	public void post(String p) {
		try {
			twitter.updateStatus(p);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	/*public static void main(String[] args) throws Exception {
		Tweeter t = new Tweeter("twitter_config.txt");
		//t.post("I'd really rather not be here so late");
		System.out.println(t.readTimeline());
	}*/
}
