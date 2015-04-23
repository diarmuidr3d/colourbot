package fileReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

/**
 * Takes a filename for a file, returns random sentences on demand.
 * @author Diarmuid Ryan
 *
 */
public class TextBot {

	private ArrayList<String> sentences;
	private int longestSentence;
	private final String resource = "resource"+File.separatorChar;
	
	/**
	 * Sets up the TextBot
	 * @param filename the file to be read from, must be in the /resource/ directory
	 */
	public TextBot(String filename) {
		BufferedReader br;
		String line = null, target = "";
		longestSentence = 0;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(resource+filename)));
			while ((line = br.readLine()) != null) {
				target += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		sentences = new ArrayList<String>();
		String sentence=null;
	    int index = 0;
	    BreakIterator bi = BreakIterator.getSentenceInstance(Locale.UK);
	    bi.setText(target);
	    while (bi.next() != BreakIterator.DONE) {
	        sentence = target.substring(index, bi.current());
	        if (sentence.length() > longestSentence) longestSentence = sentence.length();
	        sentences.add(sentence);
	        index = bi.current();
	    }
	}
	
	/**
	 * Gets a random sentence from the Text file
	 * @param minLength Sets a minimum amount of characters for the returned sentence, returns null if longer than longest sentence.
	 * @return A sentence of length greater than or equal minLength
	 */
	public String getRandom (int minLength) {
		if (minLength > longestSentence) return null;
		String ret = getRandom();
		while (ret.length() < minLength) {
			ret = getRandom();
		}
		return ret;
	}
	
	/**
	 * Gets a random sentence from the Text file
	 * @return A random sentence from the text file
	 */
	public String getRandom() {
		if (sentences.size() > 0) {
			Random rand = new Random();
			int n = rand.nextInt(sentences.size());
			return sentences.get(n);
		} else return null;
	}
	
	/*public static void main(String[] args) {
	    String target="ULYSSES.txt";
		TextBot uly = new TextBot(target);
		System.out.println(uly.getRandom());
	}*/
}
