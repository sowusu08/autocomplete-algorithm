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
        // initialize int "search" to this.myPrefixSize
        int search = this.myPrefixSize;
        //System.out.println("prefix length: "+search);

        // set vCurrent to the first character in v
        String vCurrent = String.valueOf(v.getWord().charAt(0));
        // set wCurrent to the first character in w
        String wCurrent = String.valueOf(w.getWord().charAt(0));

        // loop through parallel characters up to "search" and compare their values
        for(int k = 0; k < search; k++){
            // if the characters are index k are different return .compareTo of those characters
            if(!vCurrent.equals(wCurrent)) {
                int comp = vCurrent.compareTo(wCurrent);
                return comp;
            }

            // Set vCurrent and wCurrent to the next characters if they exist
            // otherwise set them to  " ".
            if(k == v.getWord().toCharArray().length - 1){
                vCurrent = " ";
            } else {
                vCurrent = String.valueOf(v.getWord().charAt(k+1));
            }

            if(k == w.getWord().toCharArray().length - 1){
                wCurrent = " ";
            } else {
                wCurrent = String.valueOf(w.getWord().charAt(k+1));
            }
        }

        // if we exit the loop that means all charcaters were equal; return 0
        return 0;
    }
}
