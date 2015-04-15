package CIDR.HuesNews;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import languageGenerator.CreateSentence;
import com.github.jreddit.entity.Submission;
import reddit.Reddit;
import stanfordParser.*;
import twitter4j.TwitterException;

public class App 
{
	public static void main( String[] args )
	{
		final Timer tickTock = new Timer();  // Create a Timer object
		
		Reddit reddit = new Reddit("izzy19959","u7f-ozz-nkv-Gkq");        
		Parser stanParse = new Parser();
		CreateSentence simpNlG = new CreateSentence();
		FrequencyStack fs = new FrequencyStack();
		TwitterBot CIDRBOT = new TwitterBot();
		CreateSentence sentenceGenerator = new CreateSentence();

		
		List<Submission> submissions =  reddit.getSubmission(1); //returns 1 submission
		String headline = submissions.get(0).getTitle();
		ArrayList<Token> tokens = stanParse.parse(headline);
		HashMap<String, Stack<Token>> sortedList = fs.sortList(tokens);
		System.out.println(sortedList);
		System.out.println(headline);
		String generatedSentence = sentenceGenerator.process(tokens, sortedList);
		System.out.println(generatedSentence);
		
		
//		try {
//			CIDRBOT.tweet(generatedSentence);
//		} catch (TwitterException | IOException e) {
//			e.printStackTrace();
//		} 
		



//		final TimerTask tickTockTask = new TimerTask(){
//
//
//			public void run(){
//
//
//			}
//		};
//		tickTock.schedule(tickTockTask, 1000, 200);


	}
}
