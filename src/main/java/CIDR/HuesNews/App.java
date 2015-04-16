package CIDR.HuesNews;

import com.github.jreddit.entity.Submission;
import fileReader.TextBot;
import languageGenerator.CreateSentence;
import reddit.Reddit;
import stanfordParser.FrequencyStack;
import stanfordParser.Parser;
import stanfordParser.Token;

import java.util.*;

public class App 
{
	public static void main( String[] args )
	{
		final Timer tickTock = new Timer();  // Create a Timer object
		final  String TARGET = "ULYSSES.txt";

		Reddit reddit = new Reddit("izzy19959","u7f-ozz-nkv-Gkq");        
		Parser stanParse = new Parser();
		FrequencyStack fs = new FrequencyStack();
		TwitterBot CIDRBOT = new TwitterBot();
		CreateSentence sentenceGenerator = new CreateSentence();
		TextBot textbot = new TextBot(TARGET);
		
		
		List<Submission> submissions =  reddit.getSubmission(1); //returns 1 submission
		String headline = submissions.get(0).getTitle();


		ArrayList<Token> tokenHeadline = stanParse.parse(headline);
		ArrayList<Token> tokenUly = stanParse.parse(textbot.getRandom());

		HashMap<String, Stack<Token>> sortedListHeadline = fs.sortList(tokenHeadline);

		HashMap<String, Stack<Token>> sortedListUly = fs.sortList(tokenUly);


		String generatedSentenceFromHeadline = sentenceGenerator.process(tokenHeadline, sortedListHeadline);
		String generatedSentenceFromUly = sentenceGenerator.process(tokenHeadline, sortedListHeadline);

		System.out.println(sortedListHeadline);
		System.out.println(sortedListUly);

		System.out.println("generated Headline = " + generatedSentenceFromHeadline);
		System.out.println("generated From Uly = " + generatedSentenceFromUly);


		
		//need keys in TwitterBot class to generate Tweets
//		try {
//			CIDRBOT.tweet();
//		} catch (TwitterException | IOException e) {
//			e.printStackTrace();
//		}
		



		//Will be used later.
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
