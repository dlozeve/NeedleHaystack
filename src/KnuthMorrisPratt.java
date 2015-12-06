import java.util.LinkedList;

public class KnuthMorrisPratt {
	
	public static int[] preprocess (String q) {
		
		int m = q.length();
		
		// next[k] stores the length of the maximal substring of q
		// ending at k that matches a prefix of q.
		int[] next = new int[m];
		
		// l is the length we want to compute.
		int l = 0;
		
		// The following part is almost impossible to explain
		// or understand without some drawings.
		// OK, I'll try:
		// We go through the string with k.
		for (int k=1; k<m; k++) {
			// At each point, we compare the next character in q
			// with the next character after the prefix already computed.
			// If they do not match, we check again for a smaller prefix,
			// (that is identical to a suffix of the previous prefix).
			// If the previous prefix was q[0..l-1],
			// the new prefix will be q[0..next[l-1]].
			while (l > 0 && q.charAt(l) != q.charAt(k)) {
				l = next[l-1];
			}
			// If the characters match, yes! we can go further,
			// incrementing the prefix length.
			if (q.charAt(l) == q.charAt(k)) {
				l++;
			}
			// We can now store the computed prefix length in the array:
			next[k] = l;
		}
		
		return next;
	}
	
	public static LinkedList<Integer> kmp (String s, String q) {
		
		LinkedList<Integer> res = new LinkedList<>();
		
		int n = s.length();
		int m = q.length();
		
		int[] next = preprocess(q);
		
		// This is nearly the same algorithm as in preprocessing.
		// But this time, we have *two* strings to compare.
		
		// k represents the length of the maximal prefix of q
		// that matches a substring of s beginning at i.
		int k = 0;
		
		// i is th position in s.
		for (int i =0; i<n; i++) {
			// If the next characters do not match,
			// we shift q of the maximal amount possible,
			// using the array next computed during prepocessing.
			while (k > 0 && q.charAt(k) != s.charAt(i)) {
				k = next[k-1];
			}
			// If the next characters do match,
			// we go to the next characters (of both strings).
			if (q.charAt(k) == s.charAt(i)) {
				k++;
			}
			// When k==m, we have reached the end of q,
			// and so we have found a match! Yay!
			if (k == m) {
				res.add(i-k+1);
				// We can go now to the next step to find the next match.
				k = next[k-1];
			}
		}
		
		return res;
	}
	
}
