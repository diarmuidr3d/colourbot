package languageGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import simplenlg.framework.LexicalCategory;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.english.Realiser;
import stanfordParser.FrequencyStack;
import stanfordParser.Token;

public class CreateSentence {

	Lexicon lexicon = Lexicon.getDefaultLexicon();
	NLGFactory nlgFactory = new NLGFactory(lexicon);
	Realiser realiser = new Realiser(lexicon);

	private enum Tags {
		NOUN, VERB, MODIFIER, PREPOSITION, DETERMINER, ADVERB, ADJECTIVE, PRONOUN, CONJUNCTION, MODAL, SYMBOL,
	}

	public static void main(String[] args) {

		ArrayList<Token> list = new ArrayList<Token>();

		FrequencyStack f = new FrequencyStack();
		ArrayList<Token> a = new ArrayList<Token>();
		a.add(new Token("President", "NNP"));
		a.add(new Token("Obama", "NNP"));
		a.add(new Token("Castro", "NNP"));
		a.add(new Token("Cuba", "NNP"));
		a.add(new Token("phone", "NN"));
		a.add(new Token("conversation", "NN"));
		a.add(new Token("leaders", "NNS"));
		a.add(new Token("countries", "NNS"));
		a.add(new Token("years", "NNS"));

		HashMap<String, Stack<Token>> x = f.sortList(a);

		list.add(new Token("Shouts", "VBZ"));
		list.add(new Token("from", "IN"));
		list.add(new Token("the", "DT"));
		list.add(new Token("window", "NN"));
		list.add(new Token("startling", "JJ"));
		list.add(new Token("evening", "NN"));
		list.add(new Token("in", "IN"));
		list.add(new Token("the", "DT"));
		list.add(new Token("quadrangle", "NN"));
		list.add(new Token("a", "DT"));
		list.add(new Token("deaf", "JJ"));
		list.add(new Token("gardener", "NN"));
		list.add(new Token("aproned", "JJ"));
		list.add(new Token("masked", "VBN"));
		list.add(new Token("with", "IN"));
		list.add(new Token("Matthew", "NNP"));
		list.add(new Token("Arnold", "NNP"));
		list.add(new Token("s", "POS"));
		list.add(new Token("face", "NN"));
		list.add(new Token("pushes", "VBZ"));
		list.add(new Token("his", "PRP$"));
		list.add(new Token("Mower", "NN"));
		list.add(new Token("on the", "NN"));
		list.add(new Token("sombre", "JJ"));
		list.add(new Token("lawn", "NN"));
		list.add(new Token("watching", "VBG"));
		list.add(new Token("narrowly", "RB"));
		list.add(new Token("the", "DT"));
		list.add(new Token("dancing", "NN"));
		list.add(new Token("notes", "NNS"));
		list.add(new Token("of", "IN"));
		list.add(new Token("grasshalms", "NNS"));

		new CreateSentence().process(list, x);

	}

	public String process(ArrayList<Token> list,
			HashMap<String, Stack<Token>> stack) {

		SPhraseSpec p = nlgFactory.createClause();

		for (Token pair : list) {
			String type = pair.getPosTag();
			Stack<Token> readStack = stack.get(type);
			if (readStack != null) {
				if (!readStack.isEmpty()) {
					Token token = readStack.pop();
					pair = token;
				}
			}

			parsePair(pair, p);

		}

		String output = realiser.realiseSentence(p);

//		System.out.println(output);
		return output;

	}

	private void parsePair(Token pair, SPhraseSpec p) {

		try {
			String value = pair.getWord();
			Tags t = getTag(pair.getPosTag());
			// System.out.println(value + " " + t);

			switch (t) {

			case MODIFIER:

				addModifier(value, p);
				break;

			case NOUN:

				addNoun(value, p);
				break;

			case VERB:

				addVerb(value, p);
				break;

			case DETERMINER:

				addDeterminer(value, p);
				break;

			case PREPOSITION:

				addPreposition(value, p);
				break;

			case ADVERB:

				addAdverb(value, p);
				break;

			case ADJECTIVE:

				addAdjective(value, p);
				break;

			case CONJUNCTION:

				addConjuncton(value, p);
				break;

			case PRONOUN:

				addProNoun(value, p);
				break;

			case MODAL:

				addModal(value, p);
				break;

			case SYMBOL:

				addSymbol(value, p);
				break;

			}

		} catch (RuntimeException t) {
			t.printStackTrace();
		}
	}

	private Tags getTag(String t) {

		switch (t.toUpperCase()) {

		case "WP":
			return Tags.PRONOUN;

		case "WP$":
			return Tags.PRONOUN;

		case "PRP":
			return Tags.PRONOUN;

		case "PRP$":
			return Tags.PRONOUN;

		case "NN":
			return Tags.NOUN;

		case "NNS":
			return Tags.NOUN;

		case "NNP":
			return Tags.NOUN;

		case "NNPS":
			return Tags.NOUN;

		case "VB":
			return Tags.VERB;

		case "VBD":
			return Tags.VERB;

		case "VBG":
			return Tags.VERB;

		case "VBN":
			return Tags.VERB;

		case "VBP":
			return Tags.VERB;

		case "VBZ":
			return Tags.VERB;

		case "JJ":
			return Tags.ADJECTIVE;

		case "JJR":
			return Tags.ADJECTIVE;

		case "JJS":
			return Tags.ADJECTIVE;

		case "RB":
			return Tags.ADVERB;

		case "WRB":
			return Tags.ADVERB;

		case "RBR":
			return Tags.ADVERB;

		case "RBS":
			return Tags.ADVERB;

		case "IN":
			return Tags.PREPOSITION;

		case "DT":
			return Tags.DETERMINER;

		case "PDT":
			return Tags.DETERMINER;

		case "WDT":
			return Tags.DETERMINER;

		case "CC":
			return Tags.CONJUNCTION;

		case "SYM":
			return Tags.SYMBOL;

		case "MD":
			return Tags.MODAL;

		case "CD":
			return Tags.MODIFIER;

		case "EX":
			return Tags.MODIFIER;

		case "FW":
			return Tags.MODIFIER;

		case "LS":
			return Tags.MODIFIER;

		case "POS":
			return Tags.MODIFIER;

		case "RP":
			return Tags.MODIFIER;

		case "TO":
			return Tags.MODIFIER;

		case "UH":
			return Tags.MODIFIER;

		default:
			// throw new RuntimeException("Type " + t + " not defined");
			return Tags.MODIFIER;
		}

	}

	private void addSymbol(String value, SPhraseSpec p) {
		NLGElement symbol = nlgFactory
				.createWord(value, LexicalCategory.SYMBOL);
		p.addModifier(symbol);

	}

	private void addModal(String value, SPhraseSpec p) {
		NLGElement modal = nlgFactory.createWord(value, LexicalCategory.MODAL);
		p.addModifier(modal);

	}

	private void addConjuncton(String value, SPhraseSpec p) {

		NLGElement conjunction = nlgFactory.createWord(value,
				LexicalCategory.CONJUNCTION);
		p.addModifier(conjunction);

	}

	private void addAdjective(String value, SPhraseSpec p) {

		NLGElement adjective = nlgFactory.createWord(value,
				LexicalCategory.ADJECTIVE);
		p.addModifier(adjective);

	}

	private void addPreposition(String value, SPhraseSpec p) {

		NLGElement preposition = nlgFactory.createWord(value,
				LexicalCategory.PREPOSITION);
		p.addModifier(preposition);

	}

	private void addDeterminer(String value, SPhraseSpec p) {

		NLGElement determiner = nlgFactory.createWord(value,
				LexicalCategory.DETERMINER);
		p.addModifier(determiner);

	}

	private void addModifier(String value, SPhraseSpec p) {
		p.addModifier(value);

	}

	private void addVerb(String value, SPhraseSpec p) {

		p.addPostModifier(value);

	}

	private void addAdverb(String value, SPhraseSpec p) {

		p.addPostModifier(value);

	}

	private void addProNoun(String value, SPhraseSpec p) {

		p.addPostModifier(value);

	}

	private void addNoun(String value, SPhraseSpec p) {
		NLGElement noun = nlgFactory.createWord(value, LexicalCategory.NOUN);
		p.addModifier(noun);

	}

}
