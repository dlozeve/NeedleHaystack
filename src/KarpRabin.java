import java.util.LinkedList;

public class KarpRabin {

    /* Computes the hash of a string,
     * for a given modulo w.
     */
    public static int hash (String s, int w) {
        int hash = 0;
        int m = s.length();

        int power = 1;

        for (int i = m-1; i >= 0; i--) {
            hash = (hash +  s.charAt(i)*power) % w;
            power = power*2;
        }

        return hash;
    }

    public static LinkedList<Integer> karprabin (String s, String q) {

        LinkedList<Integer> res = new LinkedList<>();

        int n = s.length();
        int m = q.length();

        // We define globally the modulus w,
        // and we compute the first hashes.
        int w = 3271753;
        int hash = hash(s.substring(0, m),w);
        int queryHash = hash(q,w);

        // We'll need this in the loop,
        // so we only compute it once.
        int pow = (int) Math.pow(2, m-1);

        for (int i = 0; i < n-m; i++) {
            if (hash == queryHash && s.substring(i, i+m).equals(q)) {
                res.add(i);
            }
            // Update the hash
            hash = ((hash - s.charAt(i)*pow)*2 + s.charAt(i+m)) % w;
        }

        return res;

    }

}
