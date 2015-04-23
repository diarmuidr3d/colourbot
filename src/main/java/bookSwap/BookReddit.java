package bookSwap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import com.github.jreddit.entity.Comment;
import com.github.jreddit.entity.Submission;

import languageGenerator.LanguageGen;
import reddit.Reddit;
import stanfordParser.FrequencyStack;
import stanfordParser.Parser;
import stanfordParser.StackBuilder;
import stanfordParser.TFIDF;
import stanfordParser.Token;
import fileReader.TextBot;

public class BookReddit {
	
	private TextBot Ulysses;
	private Reddit reddit;
	private Parser stanParse;
	//private FrequencyStack freq;
	private StackBuilder freq;
	private LanguageGen swap;
	private int submissionNum;
	private final int MAXSUBMISSION = 10;
	
	public BookReddit(String bookFileName, LanguageGen languageGen, StackBuilder hashStack) {
		Ulysses = new TextBot(bookFileName);
		reddit = new Reddit("reddit_config.txt");
		stanParse = new Parser();
		//freq = new FrequencyStack();
		freq = hashStack;
		swap = languageGen;
		submissionNum = 0;
	}
	
	public String get() {
		return swap.process(getBookSentence(), getRedditStack());
	}
	
	private ArrayList<Token> getBookSentence() {
		String line = Ulysses.getRandom();
		ArrayList<Token> tokenUly = stanParse.parse(line);
		while (!containsNoun(tokenUly)) {
			line = Ulysses.getRandom();
			tokenUly = stanParse.parse(Ulysses.getRandom());
		}
		return tokenUly;
	}
	
	private HashMap<String, Stack<Token>> getRedditStack() {
		List<Submission> submissions =  reddit.getSubmission(MAXSUBMISSION); //returns 1 submission
		ArrayList<String> stringComments = new ArrayList<String>();
		String redditSub = submissions.get(submissionNum).getTitle();
		String id = submissions.get(submissionNum).getIdentifier();
		List<Comment> comments = reddit.getCommentsForSubmission(id);
		while (comments == null) {
			submissions =  reddit.getSubmission(1); //returns 1 submission
			redditSub = submissions.get(submissionNum).getTitle();
			id = submissions.get(submissionNum).getIdentifier();
			comments = reddit.getCommentsForSubmission(id);
		}
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
		return a;
	}
	
	private boolean containsNoun(ArrayList<Token> in) {
		for (Token each : in) {
			if (each.getPosTag().contains("NN")) return true;
		}
		return false;
	}
}
