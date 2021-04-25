import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    
    public static void main(String[] args) {
        String champion = "";
        String currentWord;
        int wordCounter = 0;
        while (!StdIn.isEmpty()) {
            wordCounter += 1;
            currentWord = StdIn.readString();
            if (StdRandom.bernoulli(1./wordCounter)) {
                champion = currentWord;
            }
        }
        StdOut.println(champion);   
    }
}
