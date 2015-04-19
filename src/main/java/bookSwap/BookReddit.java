package bookSwap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import com.github.jreddit.entity.Comment;
import com.github.jreddit.entity.Submission;

import languageGenerator.TextSub;
import reddit.Reddit;
import stanfordParser.FrequencyStack;
import stanfordParser.Parser;
import stanfordParser.Token;
import fileReader.TextBot;

public class BookReddit {
	
	private TextBot Ulysses;
	private Reddit reddit;
	private Parser stanParse;
	private FrequencyStack freq;
	private TextSub swap;
	
	public BookReddit(String bookFileName) {
		Ulysses = new TextBot(bookFileName);
		reddit = new Reddit("reddit_config.txt");
		stanParse = new Parser();
		freq = new FrequencyStack();
		swap = new TextSub();
	}
	
	public String get() {
		return swap.subIn(getBookSentence(), getRedditStack());
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
		List<Submission> submissions =  reddit.getSubmission(1); //returns 1 submission
		String redditSub = submissions.get(0).getTitle();
		String id = submissions.get(0).getIdentifier();
		List<Comment> comments = reddit.getCommentsForSubmission(id);
		while (comments == null) {
			submissions =  reddit.getSubmission(1); //returns 1 submission
			redditSub = submissions.get(0).getTitle();
			id = submissions.get(0).getIdentifier();
			comments = reddit.getCommentsForSubmission(id);
		}
		for (Comment c : comments){
			redditSub += "\n"+c.getBody();
		}
		ArrayList<Token> tokenRedditSub = stanParse.parse(redditSub);
		return freq.sortList(tokenRedditSub);
	}
	
	private boolean containsNoun(ArrayList<Token> in) {
		for (Token each : in) {
			if (each.getPosTag().contains("NN")) return true;
		}
		return false;
	}
}
