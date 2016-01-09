import java.util.*;
import java.lang.Character;

public class BoyerMoore {

    /* This method implements the "bad character rule":
     * it computes an array (or here, a HashMap) of positions of each character
     * of the alphabet.
     */
    public static Map<Character, ArrayList<Integer>> badCharacterRule (String q) {
        int m = q.length();

        // We use a HashMap that associates to each character a list of its positions
        // in the String q.
        Map<Character, ArrayList<Integer>> r = new HashMap<Character, ArrayList<Integer>>();

        for (int i=m-1; i>=0; i--) {
            // For each character in q, if it is the first time we encounter it,
            // we create a List to hold its position.
            if (r.get(q.charAt(i)) == null) {
                r.put(q.charAt(i), new ArrayList<Integer>());
            }
            r.get(q.charAt(i)).add(i);
        }

        return r;
    }

    /* This method implements the "good suffix rule":
     * it computes an array of positions of the rightmost copy
     * of every suffix.
     */
    public static Object[] goodSuffixRule (String q) {

        int m = q.length();
        // f[i] stores the beginning of the rightmost copy
        // of the suffix beginning at i.
        int[] f = new int[m];
        for (int i=0; i<m; i++) {
            f[i] = -1;
        }
        // l[i] stores the beginning of the largest suffix of q[i..m-1]
        // which is also a prefix of q.
        int[] l = new int[m+1];
        l[m] = -1;

        // suffixCopies is a list of the indexes of the beginning
        // of all the copies of the current suffix.
        ArrayList<Integer> suffixCopies = new ArrayList<>(m);

        // We fill suffixCopies with all the indexes where the last
        // character is present.
        for (int k=m-2; k>=0; k--) {
            if (q.charAt(k) == q.charAt(m-1)) {
                suffixCopies.add(k);
            }
        }

        // For each position beginning at the end
        for (int i=m-1; i>=0; i--) {

            // if the leftmost copy is a prefix,
            // we add it to the array l.
            if (suffixCopies.size() > 0 && suffixCopies.get(suffixCopies.size()-1) == 0) {
                l[i] = i;
            }
            else { // otherwise the previous suffix is still the best border available:
                l[i] = l[i+1];
            }

            // We go through suffixCopies, starting at the end
            for (int j=suffixCopies.size()-1; j>=0; j--) {

                // We retrieve the index of the copy of the current suffix
                int k = suffixCopies.get(j);

                // If k==0, this copy is useless now, we can remove it.
                if (k == 0) {
                    suffixCopies.remove(j);
                }
                // If the next characters (on the left) are not the same,
                else if (q.charAt(k-1) != q.charAt(i-1)) {
                    // we put the link to the copy in the result array
                    f[i] = k;
                    // and we delete this index, since it isn't a correct copy anymore.
                    suffixCopies.remove(j);
                }
                else if (q.charAt(k-1) == q.charAt(i-1)) {
                    // If the characters on the left are the same,
                    // we add the next character to the copy.
                    suffixCopies.set(j, k-1);
                }
            }
        }

        // We return both f and l.
        return new Object[]{f, l};
    }

    /* This method computes the shift when we are at position i in q.
     * It takes the tables from the bad character and the good suffix rules.
     */
    public static int shift(String q, Map<Character, ArrayList<Integer>> r, int[] f, int[] l, int i) {

        int m = q.length();

        int bcrule = 0; // shift for the bad character rule
        int gsrule = 0; // shift for the good suffix rule

        // Bad character rule
        // This list contains all the positions of the character at i in q.
        ArrayList<Integer> positions = r.get(q.charAt(i));
        // We retrieve the first position of this character before i:
        int j = 0;
        while (j < positions.size() && positions.get(j) >= i) {
            j++;
        }
        // If such a position exists (i.e. we have not reached the end of the list)
        // we set the shift to i-j.
        if (j < positions.size()) {
            //System.out.println(positions.get(j));
            bcrule = i-positions.get(j);
        }
        // Otherwise, the character does not appear anywhere
        // in the substring left of position i.
        // We can shift the pattern past the character at hand.
        else {
            bcrule = i;
        }

        // Good suffix rule
        // Case 1: The suffix beginning at i has a copy,
        // which begins at f[i].
        if (f[i] >= 0) {
            gsrule = i - f[i];
        }
        // Case 2: The suffix has no copy in the pattern:
        // we look for the longest of its suffixes
        // which is also a prefix.
        else if (l[i] >= 0) {
            gsrule = l[i];
        }
        // Case 3: everything else has failed, we shift the pattern
        // by m places.
        else {
            gsrule = m;
        }

        // We set the shift to the maximum determined by the two rules:
        return Math.max(bcrule, gsrule);
    }

    public static LinkedList<Integer> boyermoore (String s, String q) {
        LinkedList<Integer> res = new LinkedList<>();

        Map<Character, ArrayList<Integer>> r = badCharacterRule(q);
        Object[] o = goodSuffixRule(q);
        int[] f = (int[]) o[0];
        int[] l = (int[]) o[1];

        int n = s.length();
        int m = q.length();

        int k = m-1;

        // Until we reach the end of the text:
        while (k < n) {
            int i = m-1;
            int h = k;
            // We compare the text to the pattern, from right to left.
            while (i >= 0 && s.charAt(h) == q.charAt(i)) {
                h--; i--;
            }

            // If we have reached the beginning of the pattern,
            // we have found a match.
            if (i == -1) {
                res.add(k-m+1);

                // Shift by the biggest suffix which is also a prefix:
                if (l[1] >= 0) {
                    k = k + l[1];
                }
                // Or shift completely the pattern.
                else {
                    k = k + m + 1;
                }
            }
            else {
                // If the first character was different, we shift only by one place.
                if (i+1 == m) {
                    k++;
                }
                // Otherwise, we use the shift method.
                else {
                    k = k + shift(q, r, f, l, i+1);
                }
            }
        }

        return res;
    }

}
