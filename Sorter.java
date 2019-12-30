package pa02;

import java.util.ArrayList;
import java.util.LinkedList;

/***
 * This Class contains different sorting methods, specifically quick sort, radix
 * sort, and bucket sort. These sorting algorithms also contains sub sorting
 * methods like counting sort and insertion sort. The Class allows users to
 * create an object to sort an Array List in order through distinct ways.
 * 
 * @author - James Nguyen
 *
 */
public class Sorter {
	/**
	 * The method will partition an array such that the element at the ending Index
	 * will be set as a pivot. The method will move the different Elements that are
	 * less than or equal to the pivot point will appear before the pivot point
	 * while elements greater than will appear after the pivot point.
	 * 
	 * @param list
	 *            - the list that will be partitioned
	 * @param startIndex
	 *            - the starting index of the list
	 * @param endIndex
	 *            - the ending index of the list
	 * @return - the pivot point
	 */
	public int partition(ArrayList<Integer> list, int startIndex, int endIndex) {
		int pivot = list.get(endIndex); // setting the pivot point
		int i = startIndex - 1;
		for (int j = startIndex; j <= endIndex - 1; j++) {
			if (list.get(j) <= pivot) { // if the number is less then, i is increased and i and j is swapped.
				i++;
				int holder = list.get(j); // swapping i and j
				list.set(j, list.get(i));
				list.set(i, holder);

			}
		}
		int holder = list.get(i + 1);
		list.set(i + 1, list.get(endIndex)); // swapping the pivot with the first appearing element that is greater than
												// the pivot
		list.set(endIndex, holder);
		return i + 1; // the position of the pivot
	}

	/**
	 * This method will quickSort the inputted list, but continually paritioning the
	 * list, through recursive call, such that all the elements will be in the
	 * correct position and in order.
	 * 
	 * @param list
	 *            - the list that will be quickSorted
	 * @param startIndex
	 *            - starting index of list
	 * @param endIndex
	 *            - ending index of list
	 */
	public void quickSort(ArrayList<Integer> list, int startIndex, int endIndex) {
		if (startIndex < endIndex) { // will continually recursive call until there is only 1 element in the list
			int partitioned = partition(list, startIndex, endIndex); // partition the list
			quickSort(list, startIndex, partitioned - 1); // recursively call and partition both sides of the list,
															// starting with the left side first
			quickSort(list, partitioned + 1, endIndex);
		}
	}

	/***
	 * The getter method will return the digit at a specified position of an
	 * inputted number
	 * 
	 * @param num
	 *            - number
	 * @param digitPosition
	 *            - position of digit that should be returned
	 * @return - the digit at the specified position
	 */
	public int getDigitPosition(int num, int digitPosition) {
		int copy = num;
		for (int i = 1; i < digitPosition; i++) {// for loop to move the number down such that it will the wanted
													// position is in the ones place
			copy = copy / 10;
		}
		return copy % 10; // performs modulo operation to get the digit in the ones place
	}

	/**
	 * Method which will use counting sort to sort an inputted list based on the
	 * digit position of each element, and put the sorted elements in a returned
	 * list
	 * 
	 * @param list
	 *            - list that will be used to sort
	 * @param returnedList
	 *            - the list that wil be returned with the elements in order
	 * @param digitPosition
	 *            - the position that the method will sort based on
	 */
	public void countingSort(ArrayList<Integer> list, ArrayList<Integer> returnedList, int digitPosition) {
		ArrayList<Integer> counter = new ArrayList<Integer>();

		for (int i = 0; i <= 9; i++) {
			counter.add(0); // fills the counting array with empty elemnts
		}
		for (int j = 0; j < list.size(); j++) {
			counter.set(getDigitPosition(list.get(j), digitPosition),
					counter.get(getDigitPosition(list.get(j), digitPosition)) + 1); // counts the number of times the
																					// specific digit appears throughout
																					// the list
		}

		for (int i = 1; i <= 9; i++) {
			counter.set(i, counter.get(i) + counter.get(i - 1)); // adds up the element with the element in the previous
																	// index to find the new position for all the
																	// elements in the returned list
		}
		for (int j = list.size() - 1; j >= 0; j--) {
			// copies the elements from the inputted list and puts them in the position in
			// the returned list such that the elements will be least to greatest based on
			// the specified digit position
			returnedList.set(counter.get(getDigitPosition(list.get(j), digitPosition)) - 1, list.get(j));

			counter.set(getDigitPosition(list.get(j), digitPosition),
					counter.get(getDigitPosition(list.get(j), digitPosition)) - 1);

		}
	}

	/***
	 * sorting method which will continually call counting sort on each digit
	 * position such that the elements will all be in order
	 * 
	 * @param list
	 *            - list that will be sorted
	 * @param digit
	 *            - the greatest number of digits in the element
	 */
	public void radixSort(ArrayList<Integer> list, int digit) {
		ArrayList<Integer> returnedList = new ArrayList<Integer>();// creates a returned list and makes it empty by
																	// filling it with zeroes
		for (int i = 1; i <= list.size(); i++) {
			returnedList.add(0);
		}
		for (int i = 1; i <= digit; i++) { // calls counting sort based on each digit starting from the LSD to the MSD
			countingSort(list, returnedList, i);
			for (int j = 0; j < returnedList.size(); j++) {
				list.set(j, returnedList.get(j));
			}
		}

	}

	/**
	 * Sorting method which will sort a linked list through the use of swapping and
	 * comparison
	 * 
	 * @param list
	 *            - the linked list that will be sorted
	 */
	public void insertionSort(LinkedList<Double> list) {
		for (int index = 1; index < list.size(); index++) { // goes through each element within the list, and checks if
															// the element is less than the previous elements. If the
															// element is less than, the element will continually swap
															// with the element in the previous index until it is
															// greater than the
															// previous element.
			double currentNum = list.get(index);
			int preIndex = index - 1;
			while (preIndex >= 0 && list.get(preIndex) > currentNum) {
				list.set(preIndex + 1, list.get(preIndex));
				preIndex--;
			}
			list.set(preIndex + 1, currentNum);
		}
	}

	/**
	 * Method which will sort a list, by sorting them into different buckets, and
	 * then sorting each bucket separately through insertion sort
	 * 
	 * @param list
	 *            - the list that will be sorted and the list is expected to only
	 *            contain values that are greater than or equal to 0 and less than 1
	 * @return - the sorted list
	 */
	public ArrayList<Double> bucketSort(ArrayList<Double> list) {
		int size = list.size();
		ArrayList<Double> returnedList = new ArrayList<Double>(); // the list that will be returned
		ArrayList<LinkedList<Double>> bucketList = new ArrayList<LinkedList<Double>>(); // the list that will hold the
																						// different buckets
		for (int i = 1; i <= size; i++) {
			LinkedList<Double> bucket = new LinkedList<Double>();
			bucketList.add(bucket);// adding buckets into the bucket list
		}
		for (int i = 0; i < list.size(); i++) {
			bucketList.get((int) (size * (list.get(i)))).add(list.get(i)); // sorts the different elements into their
																			// respective bucket as the method will add
																			// the element to the bucket of its value
																			// multiplied by the size
		}
		for (int i = 0; i < bucketList.size(); i++) { // insertion sort each bucket
			insertionSort(bucketList.get(i));
		}
		for (int i = 0; i < bucketList.size(); i++) { // concatenating all the buckets together into one list
			returnedList.addAll(bucketList.get(i));
		}
		return returnedList;
	}

}
