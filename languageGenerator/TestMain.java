package languageGenerator;
import java.util.Arrays;

import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Lexicon lexicon = Lexicon.getDefaultLexicon();
		new NLGFactory(lexicon);
		new Realiser(lexicon);

		TestMain sentence = new TestMain();

		// subject, verb, object

		sentence.simpleSentence("Mary", "chase", "the monkey");

		// subject, verb, object,tense

		sentence.sentenceTense("Mary", "chase", "the monkey", "past");

		sentence.sentenceTense("Mary", "chase", "the monkey", "future");

		sentence.sentenceNegated("Mary", "chase", "the monkey");

		sentence.sentenceQuestion("Mary", "chase", "the monkey");

		sentence.sentenceQuestionWho("Mary", "chase", "the monkey");

		// subject, verb, object, complement, complement2

		sentence.sentenceComplement("Mary", "chase", "the monkey",
				"very quickly", "despite her exhaustion");

		// A determiner is a word, phrase or affix that occurs together with a
		// noun or noun phrase
		// and serves to express the reference of that noun or noun phrase in
		// the context
		// example The girl is a student. The and a are determiners.

		// premodifier
		// a word, especially an adjective or a noun,
		// that is placed before a noun and describes it or restricts its
		// meaning in some way
		// In ‘a loud noise’, the adjective ‘loud’ is a premodifier.

		// subject, verb, object, noun, determiner, preposition, preModifier

		sentence.sentencePreposition("Mary", "chase", "the tiger", "park",
				"the", "in", "leafy");

		// Adverb phrase, passed as a string "very quickly"
		// Prepositional phrase, string , "despite her exhaustion"

		sentence.paragraph();

		sentence.example();

	}

	public String simpleSentence(String subject, String verb, String object) {

		Lexicon lexicon = Lexicon.getDefaultLexicon();
		NLGFactory nlgFactory = new NLGFactory(lexicon);
		Realiser realiser = new Realiser(lexicon);

		SPhraseSpec p = nlgFactory.createClause();

		p.setSubject(subject);
		p.setVerb(verb);
		p.setObject(object);

		String output = realiser.realiseSentence(p); // Realiser created
		// earlier.
		System.out.println(output);
		return output;

	}

	public String sentenceTense(String subject, String verb, String object,
			String tense) {

		Lexicon lexicon = Lexicon.getDefaultLexicon();
		NLGFactory nlgFactory = new NLGFactory(lexicon);
		Realiser realiser = new Realiser(lexicon);

		SPhraseSpec p = nlgFactory.createClause();

		p.setSubject(subject);
		p.setVerb(verb);
		p.setObject(object);

		if (tense.equalsIgnoreCase("future")) {
			p.setFeature(Feature.TENSE, Tense.FUTURE);
		}

		if (tense.equalsIgnoreCase("past")) {
			p.setFeature(Feature.TENSE, Tense.PAST);
		}

		String output = realiser.realiseSentence(p); // Realiser created
		// earlier.
		System.out.println(output);
		return output;

	}

	public String sentenceNegated(String subject, String verb, String object) {

		Lexicon lexicon = Lexicon.getDefaultLexicon();
		NLGFactory nlgFactory = new NLGFactory(lexicon);
		Realiser realiser = new Realiser(lexicon);

		SPhraseSpec p = nlgFactory.createClause();

		p.setSubject(subject);
		p.setVerb(verb);
		p.setObject(object);

		p.setFeature(Feature.NEGATED, true);

		String output = realiser.realiseSentence(p); // Realiser created
		// earlier.
		System.out.println(output);
		return output;

	}

	public String sentenceQuestion(String subject, String verb, String object) {

		Lexicon lexicon = Lexicon.getDefaultLexicon();
		NLGFactory nlgFactory = new NLGFactory(lexicon);
		Realiser realiser = new Realiser(lexicon);

		SPhraseSpec p = nlgFactory.createClause();

		p.setSubject(subject);
		p.setVerb(verb);
		p.setObject(object);

		p.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.YES_NO);

		String output = realiser.realiseSentence(p); // Realiser created
		// earlier.
		System.out.println(output);
		return output;

	}

	public String sentenceQuestionWho(String subject, String verb, String object) {

		Lexicon lexicon = Lexicon.getDefaultLexicon();
		NLGFactory nlgFactory = new NLGFactory(lexicon);
		Realiser realiser = new Realiser(lexicon);

		SPhraseSpec p = nlgFactory.createClause();

		p.setSubject(subject);
		p.setVerb(verb);
		p.setObject(object);

		p.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.WHO_OBJECT);

		String output = realiser.realiseSentence(p); // Realiser created
		// earlier.
		System.out.println(output);
		return output;

	}

	public String sentenceComplement(String subject, String verb,
			String object, String complement, String complement2) {

		// As far as simplenlg is concerned, a complement is anything that comes
		// after the verb.
		// When you label something as a complement and hand it to simplenlg to
		// be realized,
		// simplenlg will place it, no matter what it is, after the verb.

		Lexicon lexicon = Lexicon.getDefaultLexicon();
		NLGFactory nlgFactory = new NLGFactory(lexicon);
		Realiser realiser = new Realiser(lexicon);

		SPhraseSpec p = nlgFactory.createClause();

		p.setSubject(subject);
		p.setVerb(verb);
		p.setObject(object);

		p.addComplement(complement); // Adverb phrase, passed as a string
		p.addComplement(complement2); // Prepositional phrase,
										// string

		String output = realiser.realiseSentence(p); // Realiser created
		// earlier.
		System.out.println(output);
		return output;

	}

	public String sentencePreposition(String subject, String verb,
			String object, String noun, String determiner, String preposition,
			String preModifier) {

		Lexicon lexicon = Lexicon.getDefaultLexicon();
		NLGFactory nlgFactory = new NLGFactory(lexicon);
		Realiser realiser = new Realiser(lexicon);

		SPhraseSpec p = nlgFactory.createClause(subject, verb, object);

		NPPhraseSpec place = nlgFactory.createNounPhrase(noun);
		place.setNoun(noun);
		place.setDeterminer(determiner);
		PPPhraseSpec pp = nlgFactory.createPrepositionPhrase();
		pp.addComplement(place);
		pp.setPreposition(preposition);
		place.addPreModifier(preModifier);

		// We then add the prepositional phrase as a complement to the sentence
		p.addComplement(pp);

		String output = realiser.realiseSentence(p); // Realiser created
		// earlier.
		System.out.println(output);
		nlgFactory.createClause("Mary", "chase", "the monkey");
		
		


		return output;

	}

	public String demo(String subject, String verb, String object, String noun,
			String determiner, String preposition, String preModifier) {

		Lexicon lexicon = Lexicon.getDefaultLexicon();
		NLGFactory nlgFactory = new NLGFactory(lexicon);
		Realiser realiser = new Realiser(lexicon);

		SPhraseSpec p = nlgFactory.createClause(subject, verb, object);

		NPPhraseSpec place = nlgFactory.createNounPhrase(noun);
		place.setDeterminer(determiner);
		PPPhraseSpec pp = nlgFactory.createPrepositionPhrase();
		pp.addComplement(place);
		pp.setPreposition(preposition);
		place.addPreModifier(preModifier);

		// We then add the prepositional phrase as a complement to the sentence
		p.addComplement(pp);

		String output = realiser.realiseSentence(p); // Realiser created
		// earlier.
		System.out.println(output);

		return output;

	}

	public void paragraph() {

		Lexicon lexicon = Lexicon.getDefaultLexicon();
		NLGFactory nlgFactory = new NLGFactory(lexicon);
		Realiser realiser = new Realiser(lexicon);

		SPhraseSpec p1 = nlgFactory.createClause("Mary", "chase", "the monkey");
		SPhraseSpec p2 = nlgFactory.createClause("The monkey", "fight back");
		SPhraseSpec p3 = nlgFactory.createClause("Mary", "be", "nervous");

		DocumentElement s1 = nlgFactory.createSentence(p1);
		DocumentElement s2 = nlgFactory.createSentence(p2);
		DocumentElement s3 = nlgFactory.createSentence(p3);

		DocumentElement par1 = nlgFactory.createParagraph(Arrays.asList(s1, s2,
				s3));

		String output = realiser.realise(par1).getRealisation();
		System.out.println(output);

	}

	public void example() {

		Lexicon lexicon = Lexicon.getDefaultLexicon();
		NLGFactory nlgFactory = new NLGFactory(lexicon);
		Realiser realiser = new Realiser(lexicon);

		SPhraseSpec p = nlgFactory.createClause();

		p.setSubject("my");
		p.setVerb("likes");
		p.setObject("dog");

		SPhraseSpec p2 = nlgFactory.createClause();

		p2.setSubject("you");
		p2.setVerb("likes");
		p2.setObject("cat");

		DocumentElement s1 = nlgFactory.createSentence(p);
		DocumentElement s2 = nlgFactory.createSentence(p2);

		DocumentElement par1 = nlgFactory
				.createParagraph(Arrays.asList(s1, s2));

		String output = realiser.realise(par1).getRealisation();
		System.out.println(output);

	}

}