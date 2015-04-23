package languageGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import stanfordParser.Token;

public class TextSub implements LanguageGen{
	public TextSub() {
		
	}
	
	public String process(ArrayList<Token> sentence, HashMap<String, Stack<Token>> posTagMap) {
		String retVal = "";
		for(Token word : sentence) {
			if (posTagMap.containsKey(word.getPosTag())) retVal += " "+posTagMap.get(word.getPosTag()).pop().getWord();
			else retVal += " "+word.getWord();
		}
		return retVal;
	}
}
