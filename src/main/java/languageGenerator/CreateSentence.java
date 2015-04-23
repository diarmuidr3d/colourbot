package languageGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.english.Realiser;
import stanfordParser.*;
import simplenlg.framework.DocumentElement;

public class CreateSentence  implements LanguageGen {

	private static final int MINIMUM_SENTENCE_SIZE = 4;
	Lexicon lexicon = Lexicon.getDefaultLexicon();
	NLGFactory nlgFactory = new NLGFactory(lexicon);
	Realiser realiser = new Realiser(lexicon);

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub

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

		// if nnp join

		HashMap<String, Stack<Token>> x = f.sortList(a);

		list.add(new Token("shouts", "VBZ"));
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

	}*/

	public String process(ArrayList<Token> list,
			HashMap<String, Stack<Token>> stack) {

		List<String> VerbsAndComplements = getVerbAndComplement(list);

		SPhraseSpec p = nlgFactory.createClause(getSubjects(stack),
				VerbsAndComplements.get(0));

		p.addComplement(VerbsAndComplements.get(1));

		p.addPostModifier(getObjects(stack));

		Conjunctions conjunction = new Conjunctions();
		String conj = conjunction.getRandomConjunction();

		p.addPostModifier(conj);

		List<String> VerbsAndComplements2 = getVerbAndComplement(list);

		SPhraseSpec p2 = nlgFactory.createClause(getSubjects(stack),
				VerbsAndComplements2.get(0));

		p2.addComplement(VerbsAndComplements2.get(1));

		p2.addPostModifier(getObjects(stack));

		DocumentElement s1 = nlgFactory.createSentence(p);
		DocumentElement s2 = nlgFactory.createSentence(p2);

		DocumentElement par1 = nlgFactory
				.createParagraph(Arrays.asList(s1, s2));

		String output = realiser.realise(par1).getRealisation();

		output = output.replace(".", " ");

		if (output.length() > 140) {

			output = output.substring(0, Math.min(output.length(), 140));
		}

		// System.out.println(output);

		// System.out.println(list.get(0));

		return output;

	}

	private List<String> getVerbAndComplement(ArrayList<Token> list) {

		List<String> verbAndComplementList = new ArrayList<String>();

		String verb = null;
		String complement = " ";
		boolean firstVerb = false;
		int indexCounter = 0;
		int countFromVerb = 0;

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

		verbAndComplementList.add(verb);
		verbAndComplementList.add(complement);

		if (indexCounter + 1 > list.size()) {
			indexCounter = indexCounter - 1;
		}
		list.subList(0, indexCounter + 1).clear();

		return verbAndComplementList;

	}

	private String getSubjects(HashMap<String, Stack<Token>> stack) {

		Stack<Token> readStack = stack.get("NNP");
		if (readStack == null) {

			readStack = stack.get("NNPS");
		}
		Token nnp1 = readStack.pop();
		//Token nnp2 = readStack.pop();

		String subject = nnp1.getWord();
		//String subject2 = nnp2.getWord();
		//String fullSubject = subject2 + " " + subject;

		//return fullSubject;
		return subject;

	}

	private String getObjects(HashMap<String, Stack<Token>> stack) {

		Stack<Token> readStack = stack.get("NN");
		if (readStack == null) {

			readStack = stack.get("NNS");
		}

		Token nn1 = readStack.pop();

		String object = nn1.getWord();

		return object;
	}

	private List<String> conjunctionsList() {

		return null;

	}

	/*public boolean containsNoun(ArrayList<Token> tokens) {
		FrequencyStack f = new FrequencyStack();
		HashMap<String, Stack<Token>> temp = f.sortList(tokens);
		boolean nounPresent = false;
		Stack<Token> readStack2 = temp.get("NN");
		Stack<Token> readStack3 = temp.get("NNP");
		Stack<Token> readStack4 = temp.get("NNS");
		Stack<Token> readStack5 = temp.get("NNPS");
		if (readStack2 != null || readStack3 != null || readStack4 != null
				|| readStack5 != null) {
			nounPresent = true;
		}
		return nounPresent;
	}*/

}
