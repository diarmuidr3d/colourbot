//TODO: Every term is ending up with the same score. fix?

package stanfordParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;

public class TFIDF implements StackBuilder {
	public TFIDF () {
		
	}

	public HashMap<String, Stack<Token>> sortList (ArrayList<ArrayList<Token>> document) {
		HashMap<Token, Float> tokenTFIDF = countDocFreq(countFreq(document));
		HashMap<String, Stack<Token>> retVal = new HashMap<String, Stack<Token>>();
		SortedMap<Float, ArrayList<Token>> sortedFreq = new TreeMap<Float, ArrayList<Token>>(java.util.Collections.reverseOrder());
		for(Token each : tokenTFIDF.keySet()) {
			if(!sortedFreq.containsKey(tokenTFIDF.get(each))) sortedFreq.put(tokenTFIDF.get(each), new ArrayList<Token>());
			sortedFreq.get(tokenTFIDF.get(each)).add(each);
		}
		for(Float i : sortedFreq.keySet()) {
			for(Token t : sortedFreq.get(i)) {
				if (!retVal.containsKey(t.getPosTag())) {
					retVal.put(t.getPosTag(), new Stack<Token>());
				}
				retVal.get(t.getPosTag()).push(t);
			}
		}
		return retVal;
	}
	
	private HashMap<Token, float[]> countFreq (ArrayList<ArrayList<Token>> document) {
		//System.out.println("CountFreq:\n");
		HashMap<Token, float[]> ret = new HashMap<Token, float[]>();
		int numTerms = 0;
		for (int i = 0; i < document.size(); i++) {
			//System.out.println("i: "+i);
			for(Token each: document.get(i)) {
				//System.out.print("\tToken: "+each);
				if(!ret.containsKey(each)) {
					float[] j = new float[document.size()];
					for(int k=0; k < j.length; k++) j[k] = 0.0f;
					ret.put(each, j);
				}
				float[] j = ret.remove(each);
				//System.out.print(" before: "+j);
				j[i]++;
				ret.put(each, j);
				//System.out.println(" after: "+j);
				numTerms++;
			}
		}
		for (float[] each : ret.values()) {
			for (int i =0; i < each.length; i++) {
				each[i] = each[i] / numTerms;
			}
		}

		//System.out.print(ret);
		return ret;
	}
	
	private HashMap<Token, Float> countDocFreq (HashMap<Token, float[]> freq) {
		//System.out.println("\n\nDocFreq:\n");
		HashMap<Token, Float> ret = new HashMap<Token, Float>();
		for(Token each : freq.keySet()) {
			//System.out.print("Token: "+each);
			float[] frequency = freq.get(each);
			double isThere = 0;
			for (float j : frequency) {
				if (j > 0) {
					isThere++;
				}
			}
			//System.out.print(" length: "+frequency.length+" isThere: "+isThere);
			double idf = Math.log(frequency.length / isThere);
			//System.out.print(" IDF: "+idf);
			float overall = 0;
			for(float j : frequency) {
				//System.out.print(" J: "+j);
				overall += j*idf;
			}
			overall = (float) (overall/isThere);
			//System.out.println(" Score: "+overall);
			ret.put(each, overall);
			
		}
		return ret;
	}
	
	public static void main(String[] args) {
		TFIDF t = new TFIDF();
		ArrayList<ArrayList<Token>> to = new ArrayList<ArrayList<Token>>();
		to.add(new ArrayList<Token>());
		to.get(0).add(new Token("Turkey", "NNP"));
		to.get(0).add(new Token("Greece", "NNP"));
		to.get(0).add(new Token("Austria", "NNP"));
		to.get(0).add(new Token("Greece", "NNP"));
		to.get(0).add(new Token("mills", "NNS"));
		to.add(new ArrayList<Token>());
		to.get(1).add(new Token("Turkey", "NNP"));
		to.get(1).add(new Token("Sweden", "NNP"));
		to.get(1).add(new Token("France", "NNP"));
		to.get(1).add(new Token("Greece", "NNP"));
		to.get(1).add(new Token("mills", "NNS"));
		HashMap<String, Stack<Token>> sorted = t.sortList(to);
		}
	
}
