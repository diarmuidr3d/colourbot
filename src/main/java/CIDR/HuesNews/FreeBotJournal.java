package CIDR.HuesNews;

import java.util.concurrent.TimeUnit;

import languageGenerator.CreateSentence;
import languageGenerator.TextSub;
import stanfordParser.TFIDF;
import twitter.Tweeter;
import bookSwap.BookReddit;

public class FreeBotJournal {
	public static void main(String[] args) {
		BookReddit news = new BookReddit("ULYSSES.txt", new CreateSentence(), new TFIDF());
		Tweeter tweet = new Tweeter("twitter_config.txt");
		while(true) {
			String line = news.get().trim();
			while (line.length() > 139) line = news.get().trim();
			line += " "+news.getLastRedditLink().trim();
			System.out.println("\n"+"Tweeting: "+line);
			tweet.post(line);
			try {
				TimeUnit.MINUTES.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
