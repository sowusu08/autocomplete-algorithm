import java.util.*;

/**
 * Facilitates using fast binary search with
 * a Comparator. The methods firstIndex and lastIndex
 * run in time ceiling(1 + log(n)) where n is size of list.
 * @author ola for framework
 * @author 201 student implementing firstIndex and lastIndex
 *
 */
public class BinarySearchLibrary {
	
	/**
	 * Return the index of the first object (smallest index)
	 * o in parameter "equal" to target, that is
	 * the first object o such that comp.compare(o,target) == 0
	 *
	 * @param list is list of Items being searched
	 * @param target is Item searched for
	 * @param comp how Items are compared for binary search
	 * @return smallest index k such that comp.compare(list.get(k),target) == 0
	 */
	public static <T>
	    int firstIndexSlow(List<T> list,
	    		           T target, Comparator<T> comp) {
		int index = Collections.binarySearch(list, target,comp);
		
		if (index < 0) return index;
		
		while (0 <= index && comp.compare(list.get(index),target) == 0) {
			index -= 1;
		}
		return index+1;
	}
	
	/**
	 * Return smallest index of target in list using comp
	 * Guaranteed to make ceiling(1 + log(list.size())) comparisons
	 * @param list is list of Items being searched
	 * @param target is Item searched for
	 * @param comp how Items are compared for binary search
	 * @return smallest index k such that comp.compare(list.get(k),target) == 0
	 * Return -1 if there is no such object in list.               
	 */
	public static <T>
    	int firstIndex(List<T> list,
	               	   T target, Comparator<T> comp) {
		
		int low = -1;
		int high = list.size()-1;

		// (low,high] contains target
		// TODO: write method

		int res = -1;
		while (low + 1 != high) {
			int mid = (low+high)/2;

			// use comp.compare here to adjust low or high
			res = comp.compare(list.get(mid),target);

			// if res < 0  then target is greater than mid
			// so adjust low to mid
			// otherwise (target is less than or equal to mid) set high to mid
			if(res < 0) {
				low = mid;
			} else {
				high = mid;
			}

			// if res > 0 then target is less than mid
			// so adjust high to mid
			// if(res > 0){high = mid;}

			// if res == 0 then target is equal to words at mid
			// so return mid as the index where target occurs
			// if(res == 0) {return mid;}
		}
		// check that high is an index in list, if not? return -1
		// check list.get(high) to see if it's target, use comp
		if(high > -1){
			res = comp.compare(list.get(high),target);
		} else {
			return -1;
		}

		// if we make it this far evaluate res
		// if res == 0 then target is found at the index "high"
		// otherwise target was not found in the list; return -1
		if(res == 0) {
			return high;
		} else {
			return -1;
		}
	}

	 /**                                                                                          
     * Return the index of the last object (largest index)
	 * o in parameter "equal" to target, that is
     * the last object o such that comp.compare(o,target) == 0.
     * Guaranteed to make ceiling(1 + log(list.size())) comparisons
     *
     * @param list is the list of objects being searched                                         
     * @param target is the object being searched for                                            
     * @param comp is how comparisons are made                                                   
     * @return index i such that comp.compare(list.get(i),target) == 0                           
     * and there is no index > i such that this is true. Return -1                               
     * if there is no such object in list.                                                       
     */
	public static <T>
	int lastIndex(List<T> list, 
               	  T target, Comparator<T> comp) {
		
		int low = 0;
		int high = list.size();
		
		// target in [low,high)
		// TODO: write  method
		int res = -1;
		while (low + 1 != high) {
			int mid = (low+high)/2;

			// use comp.compare here to adjust low or high
			res = comp.compare(list.get(mid),target);

			// if res > 0  then target is less than mid
			// so adjust high to mid
			// otherwise (target is greater than or equal to mid) set low to mid
			if(res > 0) {
				high = mid;
			} else {
				low = mid;
			}
		}
		// check that low is an index in list, if not? return -1
		// check list.get(low) to see if it's target, use comp
		if(low > -1){
			res = comp.compare(list.get(low),target);
		} else {
			return -1;
		}

		// if we make it this far evaluate res
		// if res == 0 then target is found at the index "high"
		// otherwise target was not found in the list; return -1
		if(res == 0) {
			return low;
		} else {
			return -1;
		}
		
		//return -1;
	}
	
}
