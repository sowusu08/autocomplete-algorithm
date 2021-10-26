import java.util.*;

public class HashListAutocomplete implements Autocompletor {
    // instance variables
    private static final int MAX_PREFIX = 10;
    private Map<String, ArrayList<Term>> myMap = new HashMap<>();
    private int mySize;

    protected Term[] myTerms;

    // constructor
    public HashListAutocomplete(String[] terms, double[] weights) {

        if (terms == null || weights == null) {
            throw new NullPointerException("One or more arguments null");
        }

        if (terms.length != weights.length) {
            throw new IllegalArgumentException("terms and weights are not the same length");
        }
        initialize(terms,weights);
    }

    @Override
    public List<Term> topMatches(String prefix, int k) {
        // initialize String "actualPrefix" to "prefix"
        String actualPrefix = prefix;

        // If prefix.toCharArray.length() is greater than MAX_PREFIX
        if(prefix.length() > MAX_PREFIX){
            // set "actualPrefix" to prefix.substring(0, MAX_PREFIX)
            actualPrefix = prefix.substring(0, MAX_PREFIX);
        }

        // if myMap contains prefix (myMap.containsKey(prefix))
        if(myMap.containsKey(prefix)) {
            List<Term> all = myMap.get(prefix);
            List<Term> list = all.subList(0, Math.min(k, all.size()));
            return list;
        }
        // otherwise, if we make it this far, return empty list (no terms with prefix)
        //return null;
        return new ArrayList<>();
    }

    @Override
    public void initialize(String[] terms, double[] weights) {
        myTerms = new Term[terms.length];
        //Term[] myTerms = new Term[terms.length];

        // clear myMap
        myMap.clear();

        for (int i = 0; i < terms.length; i++) {
            myTerms[i] = new Term(terms[i], weights[i]);
            if (weights[i] < 0) {
                throw new IllegalArgumentException("Negative weight "+ weights[i]);
            }
        }

        // 1. enter all keys and values in myMap
        // loop through each element of type Term in "terms"
        // set numChar (maximum number of characters in substring) to MAX_PREFIX
        int numChar = MAX_PREFIX;
        for(Term t : myTerms){
            // loop through i = 1 to i = MAX_PREFIX
            if(t.getWord().length() < MAX_PREFIX) {numChar = t.getWord().length();}
            for(int i = 0; i <= numChar; i++) {
                // myMap.putIfAbsent the element.getWord().substring(0, i), new ArrayList<String>()
                myMap.putIfAbsent(t.getWord().substring(0,i), new ArrayList<Term>());
                // create "temp" ArrayList equal to map.get(element.getWord().substring(0, i))
                ArrayList<Term> temp = myMap.get(t.getWord().substring(0, i));
                // add the value of the element to "temp"
                temp.add(t);
                // myMap.put at the element.getWord().substring(0, i), the value of "temp"
                myMap.put(t.getWord().substring(0,i), temp);
            }
        }
        // 2. sort each value in myMap
        // loop through each key in myMap
        for(String k : myMap.keySet()) {
            // .get() the value at the key and store in "temp" ArrayList
            ArrayList<Term> temp = myMap.get(k);
            // sort "temp" using Arrays.sort("temp", Comparator.comparing(Term::getWeight).reversed())
            Collections.sort(temp,Comparator.comparing(Term::getWeight).reversed());
            // .put() at the current key the value of "temp"
            myMap.put(k, temp);
        }
    }

    @Override
    public int sizeInBytes() {
        if (mySize == 0) {
            // bytes for every term
            for(Term t : myTerms) {
                mySize += BYTES_PER_DOUBLE +
                        BYTES_PER_CHAR*t.getWord().length();
            }

            // bytes for every key in myMap
            for(String k : myMap.keySet()){
                mySize += BYTES_PER_CHAR*k.length();
            }
        }
        return mySize;
        //return 0;
    }
}
