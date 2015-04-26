package stanfordParser;

/**
 * A simple Token class for storing a String,String pair assumed to be a word and it's POS tag
 * @author Diarmuid Ryan
 *
 */
public class Token {
	private String word;
	private String posTag;
	
	/**
	 * Set's up the Token class
	 * @param word The word to be set
	 * @param posTag The pos tag belonging to the word
	 */
	public Token (String word, String posTag) {
		this.word = word;
		this.posTag = posTag;
	}
	
	/**
	 * Gets the word
	 * @return A string of the word
	 */
	public String getWord() {
		return word;
	}
	
	/**
	 * Gets the pos tag belonging to the word
	 * @return A string of the pos tag
	 */
	public String getPosTag() {
		return posTag;
	}
	
	/**
	 * Gives a string representation of the Token
	 * @return A string representation of the Token
	 */
	public String toString() {
		return word+"\\"+posTag;
	}
	
	/**
	 * A hashCode method for HashMaps
	 */
	public int hashCode(){
		return word.hashCode();
	}
	
	/**
	 * A method to determine if it is the same as another object in a HashMap in case of HashCode conflict
	 */
	public boolean equals(Object o) {
		if (o.getClass() == this.getClass()) {
			Token t = (Token) o;
			return (this.getWord().equals(t.getWord()) && this.getPosTag().equals(t.getPosTag()));
		} else return false;
	}
}
