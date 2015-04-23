package languageGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import main.java.languageGenerator.Conjunctions;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.english.Realiser;
import stanfordParser.*;
import simplenlg.framework.DocumentElement;

public class CreateSentence implements LanguageGen {

	private static final int MINIMUM_SENTENCE_SIZE = 4;
	Lexicon lexicon = Lexicon.getDefaultLexicon();
	NLGFactory nlgFactory = new NLGFactory(lexicon);
	Realiser realiser = new Realiser(lexicon);

	public String process(ArrayList<Token> list,
			HashMap<String, Stack<Token>> stack) {

		List<String> VerbsAndComplements = getVerbAndComplement(list,false);

		SPhraseSpec sentenceOne = nlgFactory.createClause(getSubjects(stack),
				VerbsAndComplements.get(0));

		sentenceOne.addComplement(VerbsAndComplements.get(1));

		sentenceOne.addPostModifier(getObjects(stack));

		Conjunctions getConjunction = new Conjunctions();
		String conj = getConjunction.getRandomConjunction();

		sentenceOne.addPostModifier(conj);

		List<String> VerbsAndComplements2 = getVerbAndComplement(list,true);

		SPhraseSpec sentenceTwo = nlgFactory.createClause(getSubjects(stack),
				VerbsAndComplements2.get(0));

		sentenceTwo.addComplement(VerbsAndComplements2.get(1));

		sentenceTwo.addPostModifier(getObjects(stack));

		DocumentElement s1 = nlgFactory.createSentence(sentenceOne);
		DocumentElement s2 = nlgFactory.createSentence(sentenceTwo);

		DocumentElement par1 = nlgFactory
				.createParagraph(Arrays.asList(s1, s2));

		String output = realiser.realise(par1).getRealisation();

		output = output.replace(".", " ");

		if (output.length() > 140) {

			output = output.substring(0, Math.min(output.length(), 140));
		}

		return output;

	}

	private List<String> getVerbAndComplement(ArrayList<Token> list,boolean partTwo) {

		List<String> verbAndComplementList = new ArrayList<String>();

		String verb = null;
		String complement = " ";
		boolean firstVerb = false;
		int indexCounter = 0;
		int countFromVerb = 0; // gathering words from verb until NN is hit

		for (Token pair : list) {

			if (pair.getPosTag().equals("VB") || pair.getPosTag().equals("VBD")
					|| pair.getPosTag().equals("VBG")
					|| pair.getPosTag().equals("VBN")
					|| pair.getPosTag().equals("VBP")
					|| pair.getPosTag().equals("VBZ")) {

				if (verb == null) {
					verb = pair.getWord();
					firstVerb = true;

				}

			}

			if (firstVerb == true) {

				if (countFromVerb > MINIMUM_SENTENCE_SIZE) {
					if (pair.getPosTag().equals("NN")
							|| pair.getPosTag().equals("NNS")
							|| pair.getPosTag().equals("NNP")
							|| pair.getPosTag().equals("NNPS")) {

						break;
					}
				}

				if (!pair.getWord().equals(verb)) {
					complement += " ";
					complement += pair.getWord();

				}

				countFromVerb++;
			}

			indexCounter++;

		}

		if(partTwo == true){verb ="";}
		verbAndComplementList.add(verb);
		verbAndComplementList.add(complement);

		if (indexCounter + 1 > list.size()) {
			indexCounter = indexCounter - 1;
		}
		list.subList(0, indexCounter + 1).clear();

		return verbAndComplementList;

	}

	private String getSubjects(HashMap<String, Stack<Token>> stack) {

		String subject = "";

		Stack<Token> readStack = stack.get("NNP");

		if (readStack == null) {

			readStack = stack.get("NNPS");

			if (readStack == null) {

				return subject;
			}
		}

		Token nnp1 = readStack.pop();
		subject = nnp1.getWord();
		return subject;
	}

	private String getObjects(HashMap<String, Stack<Token>> stack) {

		String object = "";

		Stack<Token> readStack = stack.get("NN");
		if (readStack == null) {

			readStack = stack.get("NNS");

			if (readStack == null) {

				return object;

			}

		}

		Token nn1 = readStack.pop();

		object = nn1.getWord();

		return object;
	}
}
