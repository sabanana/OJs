// 1. Selection Sort
// iterate i from [0, array.length-2], find the smallest ele among
// [i, array.length-1] and swap it with array[i];
// Time Complexity: O(N^2)
public class Solution {
	private void swap(int[] array, int i , int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	public void solve(int[] array) {
		// Initial Check
		if (array == null || array.length == 0) {
			return;
		}

		for (int i = 0; i < array.length - 1; i++) {
			for (int j = i + 1; j < array.length; j++) {
				if (array[j] < array[i]) {
					swap(array, i, j);
				}
			}
		}
	}
}

// 2. Merge Sort
// Time Complexity: O(N logN)
// Space: 
// To save space, instead of allocate a new space in each recursion
// we pass in a helper array, each time before merge, we copy the left
// and right half arrays in helper[] and then merge them and save it
// in the original array; -- O(N)

private void merge(int[] array, int[] helper, int low, int mid, int high) {
	// 1) copy array to helper
	for (int i = 0; i < array.length; i++) {
		helper[i] = array[i];
	}

	// 2) merge left & right halves and write the result in array
	int i = low, l = low, r = mid+1;
	while (l <= mid && r <= high) {
		if (helper[l] < helper[r]) {
			array[i++] = helper[l++];
		}
		else {
			array[i++] = helper[r++];
		}
	}

	// 	if there're still ele in left half, we need to copy them to the result array
	//  but, if there're still ele in right half, we need to do nothing
	//  because they're already in there
	while (l <= mid) {
		array[i++] = helper[l++];
	}
}

private void mergeSort(int[] array, int[] helper, int low, int high) {
	// Base Case
	if (high <= low) {
		return;
	}

	// Recursively mergeSort left and right halves
	int mid = low + (high - low)/2;
	mergeSort(array, helper, low, mid);
	mergeSort(array, helper, mid+1, high);

	merge(array, helper, low, mid, high);
}

public int[] solve(int[] array) {
	// Initial Check
	if (array == null || array.length == 0) {
		return array;
	}

	int[] helper = new int[array.length];
	mergeSort(array, helper, 0, array.length-1);

	return array;
}

// 3. Quick Sort
// - Select a random (very important) pivot;
// - Partition the array into 2 parts (less or equal to pivot, greater than pivot)
// - Put the pivot into its correct place;
// - Recursively process left and right side;
//
// Time Complexity: 
// - Worst Case: O(N^2) if array is already sorted, we can only deduce the 
// 	 problem size by 1 element in each recursion;
// - Average: O(N logN) if the pivot is randomly selected at each recuision
//   (which is very important)

public class Solution {
	private int randomPivot(int left, int right) {
		return left + (int)(Math.random() * (right - left + 1));
	}

	private void swap(int[] array, int a , int b) {
		int temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}

	private int partition(int[] array, int left, int right) {
		int pivotIndex = randomPivot(left, right);
		swap(array, pivotIndex, left);

		int i = left + 1, j = right;
		while (i <= j) {
			if (array[i] <= array[left]) {
				i++;
			}
			else if (array[j] > array[left]) {
				j--;
			}
			else {
				swap(array, i, j);
			}
		}

		swap(array, left, j);

		return j;
	}

	private void quickSortHelper(int[] array, int, low, int high) {
		// Base Case
		if (low >= high) {
			return;
		}

		// Invariant
		// 1) partition into 2 parts
		int p = partition(array, low, high);
		// 2) recursively process the 2 parts;
		quickSortHelper(array, low, p-1);
		quickSortHelper(array, p+1, high);
	}

	public int[] quickSort (int[] array) {
		// Initial Check
		if (array == null || array.length == 0) {
			return array;
		}

		quickSortHelper(array, 0, array.length-1);
		return array;
	}
}

// 4. Rainbow Sort
// - 3 pointers: neg, zero, one
// - [0, neg) are all -1s; [neg, zero) are all 0s; (one, array.lengh - 1] are all 1s;
// - [zero, one] are all elements waiting to be detected;
//
// Time Complexity: O(N)

// We use the same strategy as QuickSort's partition
// This time, in order to get 3 parts, we need 3 pointers neg, zero, one
// [0, neg) are all -1s; [neg,zero) are all 0s; (one, array.length-1] all 1s;
// [zero, one] is elements waiting to be discover;
// Because we're using 'zero' to detect the unknown section, in each moving step
// we should consider situations zero meets with
//
// Time: O(N)
//
public class Solution {
  private void swap(int[] array, int p, int q) {
    int temp = array[p];
    array[p] = array[q];
    array[q] = temp;
  }
  
  public int[] rainbowSort(int[] array) {
    // Check Input
    if (array == null) {
      return null;
    }
    
    // 3 sections --> 3 borders
    int neg = 0, zero = 0, one = array.length-1;
    while (zero <= one) {
      if (array[zero] == -1) {
        swap(array, neg++, zero++);
        // the left side of 'zero' is the part we've already examined,
        // thus, we can have zero++
      }
      else if (array[zero] == 1) {
        swap(array, zero, one--);
        // because we haven't examine the right side yet, 
        // thus we can't write zero++ here.
      }
      else {
        zero++;
      }
    }
    
    return array;
  }
}
