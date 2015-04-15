package fileReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class TextBot {

	private ArrayList<String> sentences;
	
	public TextBot(String filename) {
		BufferedReader br;
		String line = null, target = "";
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
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
	        sentences.add(sentence);
	        index = bi.current();
	    }
	}
	
	public String getRandom() {
		if (sentences.size() > 0) {
			Random rand = new Random();
			int n = rand.nextInt(sentences.size());
			return sentences.get(n);
		} else return null;
	}
	
	public static void main(String[] args) {
	    String target="ULYSSES.txt";
		TextBot uly = new TextBot(target);
		System.out.println(uly.getRandom());
	}
}
