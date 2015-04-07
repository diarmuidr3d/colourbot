package languageGenerator;
import java.util.ArrayList;

import simplenlg.features.Feature;
import simplenlg.features.InterrogativeType;
import simplenlg.framework.LexicalCategory;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.english.Realiser;

public class CreateSentence {

	Lexicon lexicon = Lexicon.getDefaultLexicon();
	NLGFactory nlgFactory = new NLGFactory(lexicon);
	Realiser realiser = new Realiser(lexicon);

	private enum Tags {
		NOUN, VERB, MODIFIER, PREPOSITION, DETERMINER, ADVERB, ADJECTIVE, PRONOUN, CONJUNCTION, MODAL, SYMBOL
	}

	public static void main(String[] args) {
		ArrayList<Token> list = new ArrayList<Token>();

		Token token = new Token("At", "IN");
		Token token2 = new Token("least", "JJS");
		Token token3 = new Token("15", "CD");
		Token token4 = new Token("dead", "JJ");
		Token token5 = new Token("and", "CC");
		Token token6 = new Token("60", "CD");
		Token token7 = new Token("wounded", "JJ");
		Token token8 = new Token("as", "RB");
		Token token9 = new Token("Al-Shabab", "JJ");
		Token token10 = new Token("gunmen", "NNS");
		Token token11 = new Token("attack", "VBP");
		Token token12 = new Token("university", "NN");
		Token token13 = new Token("in", "IN");
		Token token14 = new Token("Kenya", "NNP");
		Token token15 = new Token("targeting", "VBG");
		Token token16 = new Token("Christians", "NNS");

		list.add(token);
		list.add(token2);
		list.add(token3);
		list.add(token4);
		list.add(token5);
		list.add(token6);
		list.add(token7);
		list.add(token8);
		list.add(token9);
		list.add(token10);
		list.add(token11);
		list.add(token12);
		list.add(token13);
		list.add(token14);
		list.add(token15);
		list.add(token16);

		new CreateSentence().process(list);

	}

	public String process(ArrayList<Token> list) {

		SPhraseSpec p = nlgFactory.createClause();

		for (Token pair : list) {

			parsePair(pair, p);

		}

		//p.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.HOW_MANY);

		String output = realiser.realiseSentence(p);

		System.out.println(output);
		return output;

	}

	private void parsePair(Token pair, SPhraseSpec p) {

		if (pair.getPosTag().isEmpty()) {

			throw new RuntimeException("Unable to parse the pair " + pair);

		}

		String value = pair.getWord();
		Tags t = getTag(pair.getPosTag());
		//System.out.println(value+" "+t);

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

		 NLGElement verb = nlgFactory.createWord(value, LexicalCategory.VERB);
	p.addModifier(verb);

		//p.addPostModifier(value);

	}

	private void addNoun(String value, SPhraseSpec p) {

		NLGElement noun = nlgFactory.createWord(value, LexicalCategory.NOUN);
		p.addModifier(noun);

	}

	private void addAdverb(String value, SPhraseSpec p) {

		// NLGElement adverb = nlgFactory
		// .createWord(value, LexicalCategory.ADVERB);
		// p.addModifier(adverb);

		p.addPostModifier(value);

	}

	private void addProNoun(String value, SPhraseSpec p) {

		 //NLGElement pronoun = nlgFactory.createWord(value,
		 //LexicalCategory.PRONOUN);

		//p.addModifier(pronoun);

		p.addPostModifier(value);

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
			throw new RuntimeException("Type " + t + " not defined");
		}

	}

}
