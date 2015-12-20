import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.lang.Character;

public class BoyerMoore {

	/* This method implements the "bad character rule":
	 * it computes an array (or here, a HashMap) of positions of each character
	 * of the alphabet.
	 */
	public static Map<Character, LinkedList<Integer>> badCharacterRule (String q) {
		int m = q.length();
		
		// We use a HashMap that associates to each character a list of its positions
		// in the String q.
		Map<Character, LinkedList<Integer>> r = new HashMap<Character, LinkedList<Integer>>();
		
		for (int i=0; i<m; i++) {
			// For each character in q, if it is the first time we encounter it,
			// we create a List to hold its position.
			LinkedList<Integer> l = r.get(q.charAt(i));
			if (l == null) {
				r.put(q.charAt(i), new LinkedList<Integer>());
				r.get(q.charAt(i)).push(i);
			}
			else { // Otherwise, we just add the new position encountered.
				l.push(i);
			}
		}
		
		return r;
	}
	
	
	public static LinkedList<Integer> boyermoore (String s, String q) {
		LinkedList<Integer> res = new LinkedList<>();
		
		Map<Character, LinkedList<Integer>> r = badCharacterRule(q);
		
		
		return res;
	}
	
}
