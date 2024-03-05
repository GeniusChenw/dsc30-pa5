/*
 * NAME: Wenting Chen
 * PID:  A16964784
 */

//Provided imports, feel free to use these if needed
import java.util.Collections;
import java.util.ArrayList;

/**
 * Sortings
 */
public class Sorts {

    /**
     * Sorts a portion of an ArrayList using the insertion sort algorithm.
     *
     * @param list  The ArrayList to be sorted.
     * @param start The index where sorting begins.
     * @param end   The index where sorting ends.
     */
    public static void insertionSort(ArrayList<Integer> list, int start, int end) {
        for (int i = start + 1; i <= end; i++) {
            int key = list.get(i); // Select the current element as the key to be inserted.
            int j = i - 1; // Initialize a variable to track elements before the key.

            // Move elements of the subarray that are greater than the key
            // to one position ahead of their current position.
            while (j >= start && list.get(j) > key) {
                list.set(j + 1, list.get(j));
                j = j - 1; // Move to the previous element on the left.
            }
            list.set(j + 1, key);
        }
    }

    /**
     * This method performs quick sort on the input arraylist
     *
     * @param list The arraylist we want to sort
     * @param start The initial index on subsection of Arraylist we want to sort
     * @param end The final index of the subsection of Arraylist we want to sort
     */
    public static void quickSort(ArrayList<Integer> list, int start, int end) {
        if (start < end) {
            int pivotIndex = inPlacePartition(list, start, end, start);
            /*------------------------------
            The current array at this point
            should be added to your answer
            -------------------------------*/
            quickSort(list, start, pivotIndex - 1);
            quickSort(list, pivotIndex + 1, end);
        }
    }

    /**
     * This is a helper method to swap element in idx1 with element in idx2
     *
     * @param arr  The arraylist we want to swap its elements
     * @param idx1 The index of element 1
     * @param idx2  The index of element 2
     */
    private static void swap(ArrayList<Integer> arr, int idx1, int idx2) {
        int temp = arr.get(idx1);
        arr.set(idx1, arr.get(idx2));
        arr.set(idx2, temp);
    }

    /**
     * This is a method that partitions a list inplace where everything to the left of the pivot
     * is less than the pivot and everything to the right of the pivot is greater than the pivot
     *
     * @param arr The list to partition
     * @param start The index that represents the start of part to partition
     * @param stop The index that represents the end of the part to partition (inclusive)
     * @param pivotIx The index desired to be the pivot
     * @return returns the correct position of the pivot
     */
    private static int inPlacePartition(ArrayList<Integer> arr, int start, int stop, int pivotIx) {
        int pivot = arr.get(pivotIx);
        swap(arr, pivotIx, stop);
        int middleBarrier = start;
        for (int endBarrier = start; endBarrier < stop; endBarrier++) {
            if (arr.get(endBarrier) < pivot) {
                swap(arr, middleBarrier, endBarrier);
                middleBarrier++;
            }
        }
        swap(arr, middleBarrier, stop);
        return middleBarrier;
    }

    /**
     * this helper finds the appropriate number of buckets you should allocate
     * based on the range of the values in the input list
     * @param list the input list to bucket sort
     * @return number of buckets
     */
    private static int assignNumBuckets(ArrayList<Integer> list) {
        Integer max = Collections.max(list);
        Integer min = Collections.min(list);
        return (int) Math.sqrt(max - min) + 1;
    }

    /**
     * this helper finds the appropriate bucket index that a data should be
     * placed in
     * @param data a particular data from the input list if you are using
     *             loop iteration
     * @param numBuckets number of buckets
     * @param listMin the smallest element of the input list
     * @return the index of the bucket for which the particular data should
     * be placed in
     */
    private static int assignBucketIndex(Integer data, int numBuckets, Integer listMin) {
        return (data - listMin) / numBuckets;
    }

    /**
     * This method performs bucket sort on the input arraylist
     *
     * @param list The arraylist we want to sort
     * @return sortedList
     */
    public static ArrayList<Integer> bucketSort(ArrayList<Integer> list) {
        ArrayList<ArrayList<Integer>> buckets = new ArrayList<>();
        int numBuckets = assignNumBuckets(list);
        for (int i = 0; i < numBuckets; i++) {
            ArrayList<Integer> freshBucket = new ArrayList<>();
            buckets.add(i, freshBucket);
        }
        Integer listMin = Collections.min(list);
        for (Integer data : list) {
            int bucketIndex = assignBucketIndex(data, numBuckets, listMin);
            buckets.get(bucketIndex).add(data);
        }

        ArrayList<Integer> sortedList = new ArrayList<>();
        for (ArrayList<Integer> bucket : buckets) {
            if (bucket.size() > 0)
                insertionSort(bucket, 0, bucket.size() - 1);
            sortedList.addAll(bucket);
        }
        return sortedList;
    }

    /**
     * This method performs count sort on the input arraylist
     *
     * @param list The arraylist we want to sort
     * @return output
     */
    public static ArrayList<Integer> countSort(ArrayList<Integer> list) {
        ArrayList<Integer> output = new ArrayList<Integer>();

        // Find the largest element of the array
        int max = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) > max)
                max = list.get(i);
        }
        int[] count = new int[max + 1];

        // Initialize count array with all zeros.
        for (int i = 0; i < max; ++i) {
            count[i] = 0;
        }

        // Initialize output with all zeros.
        for (int i = 0; i < list.size(); ++i) {
            output.add(0);
        }

        // Store the count of each element
        for (int i = 0; i < list.size(); i++) {
            int value = list.get(i);
            count[value]++;
        }

        // Store the cummulative count of each array
        for (int i = 1; i <= max; i++) {
            count[i] += count[i - 1];
        }

        // Find the index of each element of the original array in count array, and
        // place the elements in output array
        for (int i = 0; i < list.size(); i++) {
            count[list.get(i)]--;
            output.set(count[list.get(i)], list.get(i));
        }

        return output;
    }

    ////////////////////////
    ///// EXTRA CREDIT /////
    ////////////////////////


    /**
     * This method takes in an array of integers and
     * return true or false based on different situation.
     *
     * @param arr an array of integers
     * @param n   iterations of some sorting algorithm
     * @return true if this algorithm could have been
     * insertion sort and false otherwise.
     */
    public static boolean inspectInsertion(int[] arr, int n) {
        for (int i = n; i < arr.length; i++) {
            int j = i;
            while (j > 0 && arr[j] < arr[j - 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method takes in an array of integers and
     * return true or false based on different situation.
     *
     * @param arr an array of integers
     * @param n   iterations
     * @return true if this algorithm could have been
     * selection sort and false otherwise.
     */
    public static boolean inspectSelection(int[] arr, int n) {
        for (int i = 0; i < n && i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                return false;
            }
        }
        return true;
    }

    /**
     * This is a method takes in an array of integer,
     * n numbers of iterations to judge if it is a Merge Sort.
     * @param arr an array of integers
     * @param n n iterations
     * @return true if this algorithm could
     * have been merge sort and false otherwise
     */
    public static boolean inspectMerge(int[] arr, int n) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
