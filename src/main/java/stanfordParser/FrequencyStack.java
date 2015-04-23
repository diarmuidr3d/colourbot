package stanfordParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;

public class FrequencyStack implements StackBuilder{
	public FrequencyStack() {
		
	}
	
	public HashMap<String, Stack<Token>> sortList (ArrayList<ArrayList<Token>> document) {
		HashMap<String, Stack<Token>> retVal = new HashMap<String, Stack<Token>>();
		HashMap<String, Integer> location = new HashMap<String, Integer>();
		SortedMap<Integer, HashMap<String, Token>> sortedFreq = new TreeMap<Integer, HashMap<String, Token>>(java.util.Collections.reverseOrder());
		for(ArrayList<Token> parsed: document) {
			for(Token each : parsed) {
				if (isNoun(each.getPosTag())) {
					Integer freq;
					if(location.containsKey(each.getWord())) {
						freq = location.remove(each.getWord());
						sortedFreq.get(freq).remove(each.getWord());
						freq++;
					} else {
						freq = 1;
					}
					location.put(each.getWord(), freq);
					if(!sortedFreq.containsKey(freq)) {
						sortedFreq.put(freq, new HashMap<String, Token>());
					} 
					sortedFreq.get(freq).put(each.getWord(), each);
				}
			}
		}
		for(Integer i : sortedFreq.keySet()) {
			for(Token t : sortedFreq.get(i).values()) {
				if (!retVal.containsKey(t.getPosTag())) {
					retVal.put(t.getPosTag(), new Stack<Token>());
				}
				retVal.get(t.getPosTag()).push(t);
			}
		}
		return retVal;
	}
	
	private boolean isNoun(String pos) {
		return ((pos.equals("NN")) || (pos.equals("NNS")) || (pos.equals("NNP")) || (pos.equals("NNPS")));
	}
	
	/*public static void main(String[] args) {
		FrequencyStack f = new FrequencyStack();
		ArrayList<Token> a = new ArrayList<Token>();
		a.add(new Token("a","NN"));
		a.add(new Token("as","NNS"));
		a.add(new Token("b","NN"));
		a.add(new Token("c","NN"));
		a.add(new Token("d","NN"));
		a.add(new Token("city","NNP"));
		a.add(new Token("a","NN"));
		a.add(new Token("b","NN"));
		HashMap<String, Stack<Token>> x = f.sortList(a);
		for(Stack<Token> y : x.values()) {
			for (Token z : y) System.out.println(z.getWord());
			System.out.println();
		}
	}*/
}
