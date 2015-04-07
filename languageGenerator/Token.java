package languageGenerator;
public class Token {
	private String word;
	private String posTag;

	public Token(String word, String posTag) {
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
		return word + "\\" + posTag;
	}
}