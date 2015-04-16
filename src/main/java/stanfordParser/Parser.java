package stanfordParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class Parser {
	private StanfordCoreNLP pipeline;
	
	public Parser() {
		// creates a StanfordCoreNLP object, with POS tagging
		Properties props = new Properties();
	    props.setProperty("annotators", "tokenize, ssplit, pos");
	    pipeline = new StanfordCoreNLP(props);
	}
	
	public ArrayList<Token> parse (String text) {
		ArrayList<Token> retVal = new ArrayList<Token>();
		Annotation document = new Annotation(text);
		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for(CoreMap sentence: sentences) {
		      // traversing the words in the current sentence
		      // a CoreLabel is a CoreMap with additional token-specific methods
		      for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
		        // this is the text of the token
		        String word = token.get(TextAnnotation.class);
		        // this is the POS tag of the token
		        String pos = token.get(PartOfSpeechAnnotation.class);
		        // this is the NER label of the token
		        //String ne = token.get(NamedEntityTagAnnotation.class);
		        if(!word.contains("http://")) retVal.add(new Token(word,pos)); 
		        if(word.equals("-LRB-")) word = "(";
		        if(word.equals("-RRB-")) word = ")";
		      }
		    }
		return retVal;
	}
	
	public static void main(String[] args) {
		Parser p = new Parser();
		System.out.println(p.parse("At least 15 dead and 60 wounded as Al-Shabab gunmen attack university in Kenya targeting Christians."));
	}
}
