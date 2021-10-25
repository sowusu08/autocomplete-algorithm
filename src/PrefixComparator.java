import java.util.Comparator;

/**
 * Factor pattern for obtaining PrefixComparator objects
 * without calling new. Users simply use
 *
 *     Comparator<Term> comp = PrefixComparator.getComparator(size)
 *
 * @author owen astrachan
 * @date October 8, 2020
 */
public class    PrefixComparator implements Comparator<Term> {

    private int myPrefixSize; // size of prefix

    /**
     * private constructor, called by getComparator
     * @param prefix is prefix used in compare method
     */
    private PrefixComparator(int prefix) {
        myPrefixSize = prefix;
    }


    /**
     * Factory method to return a PrefixComparator object
     * @param prefix is the size of the prefix to compare with
     * @return PrefixComparator that uses prefix
     */
    public static PrefixComparator getComparator(int prefix) {
        return new PrefixComparator(prefix);
    }


    @Override
    /**
     * Use at most myPrefixSize characters from each of v and w
     * to return a value comparing v and w by words. Comparisons
     * should be made based on the first myPrefixSize chars in v and w.
     * @return < 0 if v < w, == 0 if v == w, and > 0 if v > w
     */

    public int compare(Term v, Term w) {
        // change this to use myPrefixSize as specified,
        // replacing line below with code

        // set myPrefixSize instance variable to r
        //this.myPrefixSize = r;

        // initialize int "search" to this.myPrefixSize
        int search = this.myPrefixSize;
        // if v.toCharArray().length < this.myPrefixSize && < w.toCharArray().length set "search" to v.toCharArray().length
        if((v.getWord().toCharArray().length < search) && (v.getWord().toCharArray().length < w.getWord().toCharArray().length)){
            search = v.getWord().toCharArray().length;
        }
        // if w.toCharArray().length < this.myPrefixSize && < w.toCharArray().length set "search" to w.toCharArray().length
        if((w.getWord().toCharArray().length < search) && (w.getWord().toCharArray().length < v.getWord().toCharArray().length)){
            search = w.getWord().toCharArray().length;
        }

        System.out.println("prefix length: "+search);

        // loop through parallel characters up to "search" and compare their values
        for(int k = 0; k < search; k++){
            // if we reach the last character in either of the words (and there are still characters in the other word) and the characters at that last index are equal
            // return empty string compared to the other word's next character
            // if(k === "search" && "search" == v.getWord().toCharArray().length && "search" != w.getWord().toCharArray().length && v.getWord().charAt(k) == w.getWord().charAt(k)){
            // return "".compareTo(w.getWord().charAt(k))
            // }
            if((k == search - 1) && (search == v.getWord().toCharArray().length) && (search != w.getWord().toCharArray().length) && (v.getWord().charAt(k) - w.getWord().charAt(k) == 0)){
                return -1;
                // "".compareTo(String.valueOf(w.getWord().charAt(k)));
            }
            System.out.println("Made it past first if");
            if((k == search - 1) && (search == w.getWord().toCharArray().length) && (search != v.getWord().toCharArray().length) && (v.getWord().charAt(k) - w.getWord().charAt(k) == 0)){
                return -1;
                //"".compareTo(String.valueOf(v.getWord().charAt(k)));
            }
            System.out.println("Made it past second if");

            // if the characters are different return a comparison of the characters
            //System.out.println("fail");
            //System.out.println(String.valueOf(v.getWord().charAt(k)));
            //System.out.println(String.valueOf(w.getWord().charAt(k)));
            //System.out.println(String.valueOf(v.getWord().charAt(k))+" "+String.valueOf(w.getWord().charAt(k)));
            // System.out.println(String.valueOf(v.getWord().charAt(k)) == String.valueOf(w.getWord().charAt(k))); returns false even when characters are the same and idk why???
            //System.out.println(v.getWord().charAt(k) - w.getWord().charAt(k));

            if(v.getWord().charAt(k) - w.getWord().charAt(k) != 0) {
                return String.valueOf(v.getWord().charAt(k)).compareTo(String.valueOf(w.getWord().charAt(k)));
            }
            System.out.println("loop number: "+k);
        }

        // loop shouldn't make it this far
        return -1;
    }
}
