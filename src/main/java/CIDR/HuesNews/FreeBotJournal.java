package CIDR.HuesNews;

import java.util.concurrent.TimeUnit;

import twitter.Tweeter;
import bookSwap.BookReddit;

public class FreeBotJournal {
	public static void main(String[] args) {
		BookReddit news = new BookReddit("ULYSSES.txt");
		Tweeter tweet = new Tweeter("twitter_config.txt");
		while(true) {
			String line = news.get();
			while (line.length() > 139) line = news.get();
			tweet.post(line);
			try {
				TimeUnit.MINUTES.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
