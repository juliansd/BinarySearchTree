package edu.nyu.cs.jsd410;

import java.util.Scanner;

class PracticeBinarySearch {
    public static int binarySearch(int[] array, int key) {
        int low = 0;
        int high = array.length - 1;
        
        while( high >= low) {
        		int mid = (low + high) / 2;
        		if (key < array[mid]) {
        			high = mid - 1;
        		} else if (key == array[mid]) {
        			return mid;
        		} else {
        			low = mid + 1;
        		}
        }
        return -low - 1;
    }
    
    public static int[] sortArray(int[] array) {
    		for (int i = 0; i < array.length - 1; i++) {
    			int currentMin = array[i];
    			int currentMinIndex = i;
    			for (int j = i + 1; j < array.length; j++) {
    				if (currentMin > array[j]) {
    					currentMin = array[j];
    					currentMinIndex = j;
    				}
    			}
    			if (currentMinIndex != i) {
    				array[currentMinIndex] = array[i];
    				array[i] = currentMin;
    			}
    		}
    		return array;
    }
    
    public static void main(String[] args) {
    		int[] nums = {2, 4, 7, 10, 11, 45, 50, 59, 60, 66, 69, 70, 79};
    		Scanner scan = new Scanner(System.in);
    		System.out.println("Enter a number that you would like to search for: ");
    		int searchVal = scan.nextInt();
    		sortArray(nums);
//    		for (int i = 0; i < nums.length; i++) {
//    			System.out.println(nums[i]);
//    		}
    		int i = binarySearch(nums, 43);
    		System.out.println(i);
    }
}