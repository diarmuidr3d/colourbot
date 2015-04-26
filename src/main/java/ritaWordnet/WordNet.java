package ritaWordnet;
import java.io.File;

import rita.*;


public class WordNet  {
	 private final String resource = "resource"+File.separatorChar+"dict";
	 private RiWordNet rwn ;

	
	public WordNet() {
		rwn =  new RiWordNet(resource); //sets up the wordNet object
	}
	
	/**
	 * 	When calling this function make sure that word1 and word2 have same pos tag , e.g n and n 
	 * @param word1 The first word to be compared
	 * @param word2 The second word to be compared
	 * @param posTag The pos tag for the two words
	 * @return closer distance to 0 , the closer the words are related to each other
	 */
	public float getDistance(String word1 , String word2 , String posTag){
		
		return rwn.getDistance(word1, word2, posTag);
	}
	
	/**
	 * 	When calling this function make sure that word1 and word2 have same pos tag , e.g n and n 
	 * @param word1 The first word to be compared
	 * @param word2 The second word to be compared
	 * @return closer distance to 0 , the closer the words are related to each other
	 */
	public float getDistance(String word1 , String word2 ){
		
		return rwn.getDistance(word1, word2, this.getpos(word1));
	}
	
	private String getpos(String word){
		return rwn.getBestPos(word);
	}
	
	/*public static void main(String[] args) {

		WordNet wn = new WordNet();
		
		float distance = wn.getDistance("dog", "dog");
		System.out.println(distance);
		 distance = wn.getDistance("dog", "car");
		System.out.println(distance);
		 distance = wn.getDistance("dog", "test");
		System.out.println(distance);
		 distance = wn.getDistance("dog", "Softwareengineering");
			System.out.println(distance);
		 
		 

	}*/

}
