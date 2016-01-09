import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class Main {

    /* Small function that reads a file
     * and returns it in a String.
     */
    static String readFile(String path) 
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded);
    }

    /* The function should be called by the list of method
     * we want to use:
     * -n for the naive algorithm
     * -kr for Karp-Rabin
     * -kmp for Knuth-Morris-Pratt
     * -bm for Boyer-Moore
     * followed by the path to the text,
     * and a string for the query.
     */
    public static void main(String[] args) {

        int l = args.length;

        // We need at minimum a file and a query.
        if (l < 2 ) {
            System.out.println("You did not specify a text and a query.");
            return;
        }
        String filename = args[l-2];
        String query = args[l-1];

        try {
            String text = readFile(filename);

            // We parse the arguments to find the methods wanted
            boolean naive=false, karprabin=false, kmp=false, boyermoore=false;
            for (int i = 0; i < l-2; i++){
                if (args[i].equals("-n")) { naive = true; }
                else if (args[i].equals("-kr")) { karprabin = true; }
                else if (args[i].equals("-kmp")) { kmp = true; }
                else if (args[i].equals("-bm")) { boyermoore = true; }
            }

            if (naive) {
                long startTime = System.nanoTime();
                LinkedList<Integer> res = Naive.naive(text,query);
                long endTime = System.nanoTime();
                System.out.println("********** Naive **********\n"
                        + "Result: " + res.toString() + "\n"
                        + "Time: " + (endTime - startTime)/1000000 + " ms\n"
                        + "***************************\n\n");
            }


            if (karprabin) {
                long startTime = System.nanoTime();
                LinkedList<Integer> res = KarpRabin.karprabin(text, query);
                long endTime = System.nanoTime();
                System.out.println("********** Karp-Rabin **********\n"
                        + "Result: " + res.toString() + "\n"
                        + "Time: " + (endTime - startTime)/1000000 + " ms\n"
                        + "********************************\n\n");
            }


            if (kmp) {
                long startTime = System.nanoTime();
                LinkedList<Integer> res = KnuthMorrisPratt.kmp(text,query);
                long endTime = System.nanoTime();
                System.out.println("********** Knuth-Morris-Pratt **********\n"
                        + "Result: " + res.toString() + "\n"
                        + "Time: " + (endTime - startTime)/1000000 + " ms\n"
                        + "****************************************\n\n");
            }


            if (boyermoore) {
                long startTime = System.nanoTime();
                LinkedList<Integer> res = BoyerMoore.boyermoore(text, query);
                long endTime = System.nanoTime();
                System.out.println("********** Boyer-Moore **********\n"
                        + "Result: " + res.toString() + "\n"
                        + "Time: " + (endTime - startTime)/1000000 + " ms\n"
                        + "********************************\n\n");
            }


        }
        catch (IOException e) {
            System.out.println("File not found.");
        }

    }

}
