package App;
import java.util.List;
//import java.util.ArrayList;

public class TDIDF {
    //give it the number of words in the hashtable, and the frequency of the word given
    public double getTF(int numWords, int frequency) {
        return (double) frequency / numWords;
    }

    public double getIDF(List<List<String>> words, String s) {
        double ans = 0.0;
        int count = 0;
        for(List<String> l: words) {
            for(String looking: l) {
                if (looking.equals(s)) {
                    count++;
                    break;
                }
            }
        }
        return 1 + Math.log(words.size()/count);
    }

    public double cosineSimilarity(List<Double> vecOne, List<Double> vecTwo) {
        double dotprod = 0.0;
        double magOne = 0.0;
        double magTwo = 0.0;
        double similarity = 0.0;

        for(int i = 0; i < vecOne.size(); i++) {
            double place = 0.0;

            dotprod = dotprod + (vecOne.get(i) * vecTwo.get(i));
            magOne = magOne + Math.pow(vecOne.get(i), 2);
            magTwo = magTwo + Math.pow(vecTwo.get(i), 2);
        }
        magOne = Math.sqrt(magOne);
        magTwo = Math.sqrt(magTwo);
        //System.out.println(magOne);
        //System.out.println(magTwo);

        if(magOne != 0.0 && magTwo != 0.0)
            similarity = dotprod / (magOne * magTwo);
        else
            similarity = 0.0;
        return similarity;
    }
}
