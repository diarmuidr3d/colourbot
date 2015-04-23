package languageGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import stanfordParser.Token;

public interface LanguageGen {
	public String process(ArrayList<Token> sentence, HashMap<String, Stack<Token>> posTagMap);
}
