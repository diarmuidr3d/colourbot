package stanfordParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public interface StackBuilder {
	public HashMap<String, Stack<Token>> sortList (ArrayList<ArrayList<Token>> document);
}
