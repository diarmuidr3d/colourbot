package languageGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Conjunctions {
	
	private final String resource = "resource"+File.separatorChar;

	public static void main(String[] args) {

		Conjunctions test = new Conjunctions();
		test.getRandomConjunction();

	}

	public String getRandomConjunction() {

		BufferedReader br = null;

		Random rand = new Random();

		List<String> conjunctionsList = new ArrayList<String>();

		try {

			br = new BufferedReader(new FileReader(resource+"Conjunctions"));
			String line = null;

			while ((line = br.readLine()) != null) {

				conjunctionsList.add(line);

			}

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		int n = rand.nextInt(conjunctionsList.size());
		String randConjunction = conjunctionsList.get(n);

		return randConjunction;
	}
}
