package CIDR.HuesNews;

import java.util.concurrent.TimeUnit;

import languageGenerator.CreateSentence;
import languageGenerator.TextSub;
import stanfordParser.TFIDF;
import twitter.Tweeter;
import twitter4j.TwitterException;
import bookSwap.BookReddit;

public class FreeBotJournal {

	public static void main(String[] args) {
		final int TWITTERMAX = 116;
		BookReddit news = new BookReddit("ULYSSES.txt", new TextSub(), new TFIDF());
		Tweeter tweet = new Tweeter("twitter_config.txt");
		while(true) {
			String line = news.get().trim();
			while (line.length() > TWITTERMAX) {
				System.out.println();
				line = news.get().trim();
			}
			line += " "+news.getLastRedditLink().trim();
			System.out.println("Tweeting: "+line+"\n");
			try {
				tweet.post(line);
				TimeUnit.MINUTES.sleep(15);
			} catch (InterruptedException e) {
				System.out.print("----------------- INTERRUPTED ---------------------");
				e.printStackTrace();
			} 
		}
	}
}