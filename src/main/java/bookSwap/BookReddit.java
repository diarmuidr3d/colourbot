package bookSwap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import com.github.jreddit.entity.Comment;
import com.github.jreddit.entity.Submission;

import languageGenerator.LanguageGen;
import reddit.Reddit;
import stanfordParser.Parser;
import stanfordParser.StackBuilder;
import stanfordParser.Token;
import fileReader.TextBot;

/**
 * Class that takes top reddit posts, runs TFIDF on their comments and uses the LanguageGen specified to place them into sentences of a book
 * @author Diarmuid
 *
 */
public class BookReddit {
	
	private TextBot Ulysses;
	private Reddit reddit;
	private Parser stanParse;
	//private FrequencyStack freq;
	private StackBuilder freq;
	private LanguageGen swap;
	private int submissionNum;
	private final int MAXSUBMISSION = 10;
	private final int MINSENTENCELENGTH = 100;
	private String lastlink;
	
	/**
	 * Sets up BookReddit
	 * @param bookFileName the filename in which the plaintext book is stored in /resource/
	 * @param languageGen the implementation of languageGen{@link languageGenerator.LanguageGen} to be used to generate sentences
	 * @param hashStack the implementation of StackBuilder{@link stanfordParser.StackBuilder} to be used for scoring
	 */
	
	public BookReddit(String bookFileName, LanguageGen languageGen, StackBuilder hashStack) {
		Ulysses = new TextBot(bookFileName);
		reddit = new Reddit("reddit_config.txt");
		stanParse = new Parser();
		freq = hashStack;
		swap = languageGen;
		submissionNum = 0;
	}
	
	/**
	 * Get a new constructed sentence
	 * @return A sentence that has been processed
	 */
	public String get() {
		return swap.process(getBookSentence(), getRedditStack());
	}
	
	/**
	 * Get the URL of the last reddit submission accessed
	 * @return The article link from the last reddit post accessed.
	 */
	public String getLastRedditLink() {
		return lastlink;
	}
	
	private ArrayList<Token> getBookSentence() {
		String line = Ulysses.getRandom(MINSENTENCELENGTH);
		ArrayList<Token> tokenUly = stanParse.parse(line);
		while (!containsNoun(tokenUly)) {
			line = Ulysses.getRandom(MINSENTENCELENGTH);
			tokenUly = stanParse.parse(line);
		}
		System.out.println("Sentence: "+tokenUly);
		return tokenUly;
	}
	
	private HashMap<String, Stack<Token>> getRedditStack() {
		List<Submission> submissions =  reddit.getSubmission(MAXSUBMISSION);
		ArrayList<String> stringComments = new ArrayList<String>();
		String redditSub = submissions.get(submissionNum).getTitle();
		String id = submissions.get(submissionNum).getIdentifier();
		lastlink = submissions.get(submissionNum).getUrl();
		List<Comment> comments = reddit.getCommentsForSubmission(id);
		while (comments == null) {
			submissions =  reddit.getSubmission(MAXSUBMISSION);
			redditSub = submissions.get(submissionNum).getTitle();
			id = submissions.get(submissionNum).getIdentifier();
			comments = reddit.getCommentsForSubmission(id);
		}
		System.out.println("Headline: "+redditSub);
		submissionNum++;
		if (submissionNum == MAXSUBMISSION) submissionNum = 0;
		stringComments.add(redditSub);
		for (Comment c : comments){
			//redditSub += "\n"+c.getBody();
			stringComments.add(c.getBody());
		}
		//ArrayList<Token> tokenRedditSub = stanParse.parse(redditSub);
		ArrayList<ArrayList<Token>> tokenRedditSub2 = new ArrayList<ArrayList<Token>>();
		for (String each : stringComments) tokenRedditSub2.add(stanParse.parse(each));
		//return freq.sortList(tokenRedditSub);
		HashMap<String, Stack<Token>> a = freq.sortList(tokenRedditSub2);
		System.out.println("Stack: "+a);
		return a;
	}
	
	private boolean containsNoun(ArrayList<Token> in) {
		for (Token each : in) {
			if (each.getPosTag().contains("NN")) return true;
		}
		return false;
	}
}
