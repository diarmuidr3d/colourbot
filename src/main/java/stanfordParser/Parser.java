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
		      for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
		        String word = token.get(TextAnnotation.class);
		        String pos = token.get(PartOfSpeechAnnotation.class);
		        if(word.contains("http://"))  continue;
		        if(word.equals("-LRB-")) word = "(";
		        if(word.equals("-RRB-")) word = ")";
		        if((retVal.size() > 0) && pos.equals("NNP") && (retVal.get(retVal.size()-1).getPosTag().equals("NNP"))) {
		        	Token last = retVal.remove(retVal.size()-1);
		        	word = last.getWord() +" "+word;
		        }
		        retVal.add(new Token(word,pos));
		      }
		    }
		return retVal;
	}
	
	public static void main(String[] args) {
		Parser p = new Parser();
		System.out.println(p.parse("At least 15 dead and 60 wounded as Al-Shabab gunmen attack university in Kenya targeting Christians."));
	}
}
