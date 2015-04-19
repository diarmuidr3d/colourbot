package stanfordParser;

public class Token {
	private String word;
	private String posTag;
	
	public Token (String word, String posTag) {
		this.word = word;
		this.posTag = posTag;
	}
	
	public String getWord() {
		return word;
	}

	public String getPosTag() {
		return posTag;
	}
	
	public String toString() {
		return word+"\\"+posTag;
	}
	
	public int hashCode(){
		return word.hashCode();
	}
	
	public boolean equals(Object o) {
		if (o.getClass() == this.getClass()) {
			Token t = (Token) o;
			return (this.getWord() == t.getWord() && this.getPosTag() == t.getPosTag());
		} else return false;
	}
}
