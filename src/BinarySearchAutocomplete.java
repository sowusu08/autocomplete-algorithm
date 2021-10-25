import java.util.*;

/**
 * 
 * Using a sorted array of Term objects, this implementation uses binary search
 * to find the top term(s).
 * 
 * @author Austin Lu, adapted from Kevin Wayne
 * @author Jeff Forbes
 * @author Owen Astrachan in Fall 2018, revised API
 */
public class BinarySearchAutocomplete implements Autocompletor {

	private Term[] myTerms;
	private int mySize;

	/**
	 * Given arrays of words and weights, initialize myTerms to a corresponding
	 * array of Terms sorted lexicographically.
	 * 
	 * This constructor is written for you, but you may make modifications to
	 * it.
	 * 
	 * @param terms
	 *            - A list of words to form terms from
	 * @param weights
	 *            - A corresponding list of weights, such that terms[i] has
	 *            weight[i].
	 * @return a BinarySearchAutocomplete whose myTerms object has myTerms[i] =
	 *         a Term with word terms[i] and weight weights[i].
	 * @throws NullPointerException if either argument passed in is null
	 */
	public BinarySearchAutocomplete(String[] terms, double[] weights) {
		if (terms == null || weights == null) {
			throw new NullPointerException("One or more arguments null");
		}
		
		initialize(terms,weights);
	}

	/**
	 * Uses binary search to find the index of the first Term in the passed in
	 * array which is considered equivalent by a comparator to the given key.
	 * This method should not call comparator.compare() more than 1+log n times,
	 * where n is the size of a.
	 * 
	 * @param a
	 *            - The array of Terms being searched
	 * @param key
	 *            - The key being searched for.
	 * @param comparator
	 *            - A comparator, used to determine equivalency between the
	 *            values in a and the key.
	 * @return The first index i for which comparator considers a[i] and key as
	 *         being equal. If no such index exists, return -1 instead.
	 */
	public static int firstIndexOf(Term[] a, Term key, Comparator<Term> comparator) {
		int index = BinarySearchLibrary.firstIndex(Arrays.asList(a), key, comparator);
		return index;
	}

	/**
	 * The same as firstIndexOf, but instead finding the index of the last Term.
	 * 
	 * @param a
	 *            - The array of Terms being searched
	 * @param key
	 *            - The key being searched for.
	 * @param comparator
	 *            - A comparator, used to determine equivalency between the
	 *            values in a and the key.
	 * @return The last index i for which comparator considers a[i] and key as
	 *         being equal. If no such index exists, return -1 instead.
	 */
	public static int lastIndexOf(Term[] a, Term key, Comparator<Term> comparator) {
		int index = BinarySearchLibrary.lastIndex(Arrays.asList(a), key, comparator);
		return index;
	}

	/**
	 * Required by the Autocompletor interface. Returns an array containing the
	 * k words in myTerms with the largest weight which match the given prefix,
	 * in descending weight order. If less than k words exist matching the given
	 * prefix (including if no words exist), then the array instead contains all
	 * those words. e.g. If terms is {air:3, bat:2, bell:4, boy:1}, then
	 * topKMatches("b", 2) should return {"bell", "bat"}, but topKMatches("a",
	 * 2) should return {"air"}
	 * 
	 * @param prefix
	 *            - A prefix which all returned words must start with
	 * @param k
	 *            - The (maximum) number of words to be returned
	 * @return An array of the k words with the largest weights among all words
	 *         starting with prefix, in descending weight order. If less than k
	 *         such words exist, return an array containing all those words If
	 *         no such words exist, reutrn an empty array
	 * @throws NullPointerException if prefix is null
	 */
	
	@Override
	public List<Term> topMatches(String prefix, int k) {
		// throw null pointer exception if prefix is null
		if(prefix == null){
			throw new NullPointerException();
		}

		if (k < 0) {
			throw new IllegalArgumentException("Illegal value of k:"+k);
		}

		Term dummy = new Term(prefix,0);
		PrefixComparator comp = PrefixComparator.getComparator(prefix.length());
		int first = firstIndexOf(myTerms, dummy, comp);
		int last = lastIndexOf(myTerms, dummy, comp);

		if (first == -1) {               // prefix not found
			return new ArrayList<>();
		}

		// write code here for P5 assignment
		// if we make it this far that means Terms with the prefix are found
		// initialize a priority queue "matches" where order of elements (of type "Term") is by "getWeight" method
		// loop through elements at indexes "first" through "last" inclusive in myTerms array
			// if "matches".size() < k
				// add the element to "matches"
			// else add the element to "matches" then call .remove() on "matches"
		PriorityQueue<Term> pq =
				new PriorityQueue<>(Comparator.comparing(Term::getWeight));

		Term currentTerm = null;
		for (int i=first; i <= last; i++){	//Term t : myTerms) {
			//if (!t.getWord().startsWith(prefix)) {
				//continue; // don't process if doesn't begin with prefix
			//}
			currentTerm = myTerms[i];
			if (pq.size() < k) {
				pq.add(currentTerm);
			} else if (pq.peek().getWeight() < currentTerm.getWeight()) {
				pq.remove();
				pq.add(currentTerm);
			}
		}

		//return null;
		// return a linkedList of the terms in pq
		//System.out.println(pq);
		int total_terms = pq.size();		// save the total number of terms to put in list (because it will change as we remove terms from pq)
		LinkedList<Term> ret = new LinkedList<>();
		for (int i = 0; i < total_terms; i++) {
			ret.addFirst(pq.remove());
		}
		//System.out.println(ret.size());
		//System.out.println(pq);
		return ret;
	
	}

	@Override
	public void initialize(String[] terms, double[] weights) {
		myTerms = new Term[terms.length];
		
		for (int i = 0; i < terms.length; i++) {
			myTerms[i] = new Term(terms[i], weights[i]);
		}
		
		Arrays.sort(myTerms);
	}
	
	@Override
	public int sizeInBytes() {
		if (mySize == 0) {
			
			for(Term t : myTerms) {
			    mySize += BYTES_PER_DOUBLE + 
			    		  BYTES_PER_CHAR*t.getWord().length();	
			}
		}
		return mySize;
	}
}
