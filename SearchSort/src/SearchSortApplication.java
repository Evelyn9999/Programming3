import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
public class SearchSortApplication {
    private static final int MAX_VALUE = 100;
    private static Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Menu of Searching and Sorting Testbed.");
            System.out.println("1) Linear searching");
            System.out.println("2) Binary searching");
            System.out.println("3) O(n^2) type of sorting");
            System.out.println("4) O(n*log(n)) type of sorting");
            System.out.println("5) Sorting performance");
            System.out.println("q/Q) Quit");
            System.out.print("Your choice: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    performLinearSearch(scanner);
                    break;
                case 2:
                    performBinarySearch(scanner);
                    break;
                case 3:
                    performInsertionSort();
                    break;
                case 4:
                    performQuickSort();
                    break;
                case 5:
                    performSortingPerformance();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1-5 or 'q' to quit.");
            }
        } while (choice != 'q' && choice != 'Q');

        scanner.close();
        System.out.println("Application exited.");
    }


    // class for choice 5
    static class SortableItem implements Comparable<SortableItem> {
        private int value;
        static long comparisonCount = 0;

        public SortableItem(int value) {
            this.value = value;
        }

        @Override
        public int compareTo(SortableItem other) {
            comparisonCount++;
            return Integer.compare(this.value, other.value);
        }

        // Reset the comparison count
        public static void resetComparisonCount() {
            comparisonCount = 0;
        }
    }


    /*********************************
     * Functions for all the choices *
     *********************************/

    // function for choice 1
    private static void performLinearSearch(Scanner scanner) {
        int[] list = generateArray(0, 9);
        System.out.println("In the list are values:" + Arrays.toString(list));
        System.out.print("Which value would you like to search with linear search? ");
        int value = scanner.nextInt();
        boolean found = linearSearch(list, value);
        System.out.println(found ? "Found" : "Not found");
    }

    // function for choice 2
    private static void performBinarySearch(Scanner scanner) {
        int[] list = generateArray(0, 9);
        Arrays.sort(list); // Binary search requires sorted array
        System.out.println("In the list are values:" + Arrays.toString(list));
        System.out.print("Which value would you like to search with binary search? ");
        int value = scanner.nextInt();
        boolean found = binarySearch(list, value);
        System.out.println(found ? "Found" : "Not found");
    }

    // function for choice 3
    private static void performInsertionSort() {
        int[] data = generateRandomIntArray();
        System.out.println("Data set before insertion sorting: " + Arrays.toString(data));
        insertionSort(data);
        System.out.println("Data set after insertion sorting: " + Arrays.toString(data));
    }

    // function for choice 4
    private static void performQuickSort() {
        int[] data = generateRandomIntArray();
        System.out.println("Data set before quicksort: " + Arrays.toString(data));
        quickSort(data, 0, data.length - 1);
        System.out.println("Data set after quicksort: " + Arrays.toString(data));
    }

    // function for choice 5
    private static void performSortingPerformance() {
        // Starting size of the data set
        int n = 1000;

        // The line of size
        System.out.format("%-35s", "Size");
        for (int i = 1; i <= 10; i++) {
            System.out.format("%-15d", n * i);
        }
        System.out.println();

        // bubbleSort,random,comparisons
        System.out.format("%-35s", "bubbleSort,random,comparisons");
        for (int i = 1; i <= 10; i++) {
            int size = n * i;

            SortableItem[] originalArray = generateSortableItemArray(size);

            // Perform Bubble Sort
            SortableItem[] bubbleSortItems = originalArray.clone();
            SortableItem.resetComparisonCount();
            bubbleSortableItem(bubbleSortItems);
            long bubbleComparisons = SortableItem.comparisonCount;

            System.out.format("%-15d", bubbleComparisons);
        }
        System.out.println();

        // bubbleSort,random,ms
        System.out.format("%-35s", "bubbleSort,random,ms");
        for (int i = 1; i <= 10; i++) {
            int size = n * i;

            SortableItem[] originalArray = generateSortableItemArray(size);

            // Perform Bubble Sort
            SortableItem[] bubbleSortItems = originalArray.clone();
            SortableItem.resetComparisonCount();
            long bubbleStart = System.nanoTime();
            bubbleSortableItem(bubbleSortItems);
            long bubbleEnd = System.nanoTime();
            long bubbleTime = (bubbleEnd - bubbleStart) / 1_000_000;

            System.out.format("%-15d", bubbleTime);
        }
        System.out.println();

        // insertionSort,random,comparisons
        System.out.format("%-35s", "insertionSort,random,comparisons");
        for (int i = 1; i <= 10; i++) {
            int size = n * i;

            SortableItem[] originalArray = generateSortableItemArray(size);

            // Perform Insertion Sort
            SortableItem[] insertionSortItems = originalArray.clone();
            SortableItem.resetComparisonCount();
            insertionSortableItem(insertionSortItems);
            long insertionComparisons = SortableItem.comparisonCount;

            System.out.format("%-15d", insertionComparisons);
        }
        System.out.println();

        // insertionSort,random,ms
        System.out.format("%-35s", "insertionSort,random,ms");
        for (int i = 1; i <= 10; i++) {
            int size = n * i;

            SortableItem[] originalArray = generateSortableItemArray(size);

            // Perform Insertion Sort
            SortableItem[] insertionSortItems = originalArray.clone();
            SortableItem.resetComparisonCount();
            long insertionStart = System.nanoTime();
            insertionSortableItem(insertionSortItems);
            long insertionEnd = System.nanoTime();
            long insertionTime = (insertionEnd - insertionStart) / 1_000_000;

            System.out.format("%-15d", insertionTime);
        }
        System.out.println();

        // mergeSort,random,comparisons
        System.out.format("%-35s", "mergeSort,random,comparisons");
        for (int i = 1; i <= 10; i++) {
            int size = n * i;

            SortableItem[] originalArray = generateSortableItemArray(size);

            // Perform Merge Sort
            SortableItem[] mergeSortItems = originalArray.clone();
            SortableItem.resetComparisonCount();
            mergeSort(mergeSortItems, 0, mergeSortItems.length - 1);
            long mergeComparisons = SortableItem.comparisonCount;

            System.out.format("%-15d", mergeComparisons);
        }
        System.out.println();

        // mergeSort,random,ms
        System.out.format("%-35s", "mergeSort,random,ms");
        for (int i = 1; i <= 10; i++) {
            int size = n * i;

            SortableItem[] originalArray = generateSortableItemArray(size);

            // Perform Merge Sort
            SortableItem[] mergeSortItems = originalArray.clone();
            SortableItem.resetComparisonCount();
            long mergeStart = System.nanoTime();
            mergeSort(mergeSortItems, 0, mergeSortItems.length - 1);
            long mergeEnd = System.nanoTime();
            long mergeTime = (mergeEnd - mergeStart) / 1_000_000;

            System.out.format("%-15d", mergeTime);
        }
        System.out.println();

        // quickSort,random,comparisons
        System.out.format("%-35s", "quickSort,random,comparisons");
        for (int i = 1; i <= 10; i++) {
            int size = n * i;

            SortableItem[] originalArray = generateSortableItemArray(size);

            // Perform Quick Sort
            SortableItem[] quickSortItems = originalArray.clone();
            SortableItem.resetComparisonCount();
            quickSortableItem(quickSortItems, 0, quickSortItems.length - 1);
            long quickComparisons = SortableItem.comparisonCount;

            System.out.format("%-15d", quickComparisons);
        }
        System.out.println();

        // quickSort,random,ms
        System.out.format("%-35s", "quickSort,random,ms");
        for (int i = 1; i <= 10; i++) {
            int size = n * i;

            SortableItem[] originalArray = generateSortableItemArray(size);

            // Perform Quick Sort
            SortableItem[] quickSortItems = originalArray.clone();
            SortableItem.resetComparisonCount();
            long quickStart = System.nanoTime();
            quickSortableItem(quickSortItems, 0, quickSortItems.length - 1);
            long quickEnd = System.nanoTime();
            long quickTime = (quickEnd - quickStart) / 1_000_000;

            System.out.format("%-15d", quickTime);
        }
        System.out.println();

        // selectionSort,random,comparisons
        System.out.format("%-35s", "selectionSort,random,comparisons");
        for (int i = 1; i <= 10; i++) {
            int size = n * i;

            SortableItem[] originalArray = generateSortableItemArray(size);

            // Perform Selection Sort (assuming you have this method implemented)
            SortableItem[] selectionSortItems = originalArray.clone();
            SortableItem.resetComparisonCount();
            selectionSortableItem(selectionSortItems);
            long selectionComparisons = SortableItem.comparisonCount;

            System.out.format("%-15d", selectionComparisons);
        }
        System.out.println();

        // selectionSort,random,ms
        System.out.format("%-35s", "selectionSort,random,ms");
        for (int i = 1; i <= 10; i++) {
            int size = n * i;

            SortableItem[] originalArray = generateSortableItemArray(size);

            // Perform Selection Sort (assuming you have this method implemented)
            SortableItem[] selectionSortItems = originalArray.clone();
            SortableItem.resetComparisonCount();
            long selectionStart = System.nanoTime();
            selectionSortableItem(selectionSortItems);
            long selectionEnd = System.nanoTime();
            long selectionTime = (selectionEnd - selectionStart) / 1_000_000;

            System.out.format("%-15d", selectionTime);

        }
        System.out.println();
    }

    private static SortableItem[] generateSortableItemArray(int size) {
        SortableItem[] array = new SortableItem[size];
        for (int i = 0; i < size; i++) {
            array[i] = new SortableItem(random.nextInt(MAX_VALUE * 2 + 1) - MAX_VALUE);
        }
        return array;
    }


    /*********************************
     *         Utility methods       *
     *********************************/

    /* Generate arrays */
    // Generate an array of 0-9 integers for choice 1 and 2
    private static int[] generateArray(int min, int max) {
        int[] array = new int[max - min + 1];
        for (int i = min; i <= max; i++) {
            array[i - min] = i; // Fill the array with consecutive numbers
        }
        shuffleArray(array); // Shuffle to randomize the order
        return array;
    }

    private static void shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    // Generate an array of 10 random integers for choice 3 and 4
    private static int[] generateRandomIntArray() {
        int[] array = new int[10]; // Fixed size of 10
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(MAX_VALUE * 2 + 1) - MAX_VALUE; // Random number between -MAX_VALUE and MAX_VALUE
        }
        return array;
    }

    /* Method for choice 1 */
    private static boolean linearSearch(int[] list, int value) {
        for (int i : list) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }

    /* Method for choice 2 */
    private static boolean binarySearch(int[] list, int value) {
        int low = 0;
        int high = list.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (list[mid] == value) {
                return true;
            } else if (list[mid] < value) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return false;
    }

    /* Method for choice 3 */
    private static void insertionSort(int[] data) {
        for (int i = 1; i < data.length; i++) {
            int current = data[i];
            int j = i - 1;
            while (j >= 0 && data[j] > current) {
                data[j + 1] = data[j];
                j--;
            }
            data[j + 1] = current;
        }
    }

    /* Method for choice 4 */
    private static void quickSort(int[] array, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(array, begin, end);

            quickSort(array, begin, partitionIndex - 1);
            quickSort(array, partitionIndex + 1, end);
        }
    }

    private static int partition(int[] array, int begin, int end) {
        int pivot = array[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (array[j] <= pivot) {
                i++;

                int swapTemp = array[i];
                array[i] = array[j];
                array[j] = swapTemp;
            }
        }

        int swapTemp = array[i + 1];
        array[i + 1] = array[end];
        array[end] = swapTemp;

        return i + 1;
    }

    /* Method for choice 5 */
    //  Method for Insertion Sort
    private static void insertionSortableItem(SortableItem[] data) {
        for (int i = 1; i < data.length; i++) {
            SortableItem current = data[i];
            int j = i - 1;
            while (j >= 0 && data[j].compareTo(current) > 0) {
                data[j + 1] = data[j];
                j--;
            }
            data[j + 1] = current;
        }
    }

    // Method for Bubble Sort
    private static void bubbleSortableItem(SortableItem[] data) {
        int n = data.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (data[j].compareTo(data[j + 1]) > 0) {
                    // swap temp and data[i]
                    SortableItem temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                }
            }
        }
    }

    // Method for Quick Sort
    private static void quickSortableItem(SortableItem[] array, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(array, begin, end);

            quickSortableItem(array, begin, partitionIndex - 1);
            quickSortableItem(array, partitionIndex + 1, end);
        }
    }

    private static int partition(SortableItem[] array, int begin, int end) {
        SortableItem pivot = array[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (array[j].compareTo(pivot) <= 0) {
                i++;

                SortableItem swapTemp = array[i];
                array[i] = array[j];
                array[j] = swapTemp;
            }
        }

        SortableItem swapTemp = array[i + 1];
        array[i + 1] = array[end];
        array[end] = swapTemp;

        return i + 1;
    }

    // Method for Merge Sort
    private static void mergeSortableItem(SortableItem[] array, int left, int middle, int right) {
        // Find sizes of two subarrays to be merged
        int n1 = middle - left + 1;
        int n2 = right - middle;

        /* Create temp arrays */
        SortableItem[] L = new SortableItem[n1];
        SortableItem[] R = new SortableItem[n2];

        /*Copy data to temp arrays*/
        System.arraycopy(array, left, L, 0, n1);
        System.arraycopy(array, middle + 1, R, 0, n2);

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray array
        int k = left;
        while (i < n1 && j < n2) {
            if (L[i].compareTo(R[j]) <= 0) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            array[k] = R[j];
            j++;
            k++;
        }
    }

    // Method to initiate the Merge Sort process
    private static void mergeSort(SortableItem[] array, int left, int right) {
        if (left < right) {
            // Find the middle point
            int middle = (left + right) / 2;

            // Sort first and second halves
            mergeSort(array, left, middle);
            mergeSort(array, middle + 1, right);

            // Merge the sorted halves
            mergeSortableItem(array, left, middle, right);
        }
    }

    // Method for Selection Sort
    private static void selectionSortableItem(SortableItem[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            // Find the minimum element in unsorted array
            int minIndex = i;
            for (int j = i + 1; j < data.length; j++)
                if (data[j].compareTo(data[minIndex]) < 0)
                    minIndex = j;

            // Swap the found minimum element with the first element
            SortableItem temp = data[minIndex];
            data[minIndex] = data[i];
            data[i] = temp;
        }
    }



}
