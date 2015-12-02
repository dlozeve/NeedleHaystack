import java.util.LinkedList;

public class Naive {

	public static LinkedList<Integer> naive(String s, String q) {
		
		LinkedList<Integer> res = new LinkedList<>();
		
		int n = s.length();
		int m = q.length();		
		
		for (int i = 0; i < n-m; i++) {
			if (s.substring(i, i+m).equals(q)) {
				res.add(i);
			}
		}
		
		return res;		
	}
	
}
