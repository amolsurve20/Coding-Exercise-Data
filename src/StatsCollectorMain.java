import java.util.ArrayList;
/*
 * StatsCollector contains the ArrayList of responseTime & following important methods
 * 1. pushValue(int requestResponseTime):
 * 		To identify the type of value to be pushed inside the ArrayList. 
 * 		Assumption is that only integer array will be used 
 * 		and the response time of more than 19000ms and less than or equal to zero will be discarded  
 * 
 * 2. getArray():
 * 		Returns the array created from the ArrayList
 * 
 * 3. getMedian():
 * 		Returns the median calculated using the Order Statistics logic in linear time
 * 
 * 4. partition(int[] array, int first, int last)
 * 		QuickSort based partition function to identify the pivot 
 * 
 * 5. orderStatistic(int[] array, int k, int first, int last) 
 * 		Recursively searches left or right subarray to indetify the median
 * 		
 * 6. streamAvg()
 * 		Calculated the average at each stage 
 * 
 * 7. getMedianNaive()
 * 		sorts the array and then returns the median based on even or odd number of values
 * 
 */

import java.util.Arrays;
class StatsCollector
{
	private ArrayList<Integer> responseTime;
	
	public StatsCollector()
	{
		responseTime = new ArrayList<Integer>();
	}
	
	public ArrayList<Integer>getResponseList()
	{
		return this.responseTime;
	}
	
	public int getCurrentsize()
	{
		return responseTime.size();
	}
	
	//pushValue() method adds the responseTime to the arrayList if it is a valid number
	public int pushValue(int requestResponseTime) throws Exception
	{
		/*
		if(requestResponseTimee<=0)
		{
			throw new Exception("response time is invalid, it must be positive");
		}
		//Assuming that the response above 19000ms won't be considered
		if(requestResponseTime> 19000)
		{
			throw new Exception("response timeout occured!");
		}
		*/
		
		if(requestResponseTime<=0 || requestResponseTime>19000)
		{
			return -1;
		}
		else
		{
			//adding the value inside the arraylist of response time
			this.responseTime.add(requestResponseTime);
			return requestResponseTime;
		}
	}
	
	//converts an ArrayList into Array
	public int[] getArray()
	{
		int[] arr = new int[this.responseTime.size()];
	    for (int i=0; i < arr.length; i++)
	    {
	        arr[i] = this.responseTime.get(i).intValue();
	    }
	    
	    return arr;
	}
	
	/*
	 * getMedian() method takes the input as the array of integer and finds the median WITHOUT sorting the array
	 * It uses the Order statistics along implemented on the basis of the quick sort
	 * 
	 * 1. It chooses a pivot element
	 * 
	 * 2. It then moves all elements less than the pivot element to the left 
	 * and all elements that are larger than the pivot element to the right. 
	 * 
	 * 3. After this operation the pivot element is on the right spot and if the k == the pivot's index, sorting is done
	 *  and return the pivot.
	 *  
	 *  Here, for finding the median, the value of k will be the index of the middle element 
	 *  To be more specific, 
	 *   a)if the number of elements are odd, we will simply return the middle element as the median 
	 *   b)else, if the number of elements are even, we will return the average of the middle two elements, since there is no single mid element.
	 * 
	 * 4. Hence, there is no need to sort the entire array as it will be a very large set of numbers considering the heavy server log
	 * 
	 * 5. Sorting would take additional nlog(n) time and is unnecessary as we need to just get the mid element
	 * 
	 * 6. Unlike merge sort, QuickSort doesn't require additional temporary array 
	 */
	
	public float getMedian()
	{
		//Converting the arraylist of responsetime to array for easier processing 
		
		int[] arr = this.getArray();
	    
		//handling the zero response time error
		if(arr.length==0)
			return -1;
		else
			//single response time 
			if(arr.length==1)
				return arr[0];
			//if even number of response times, then return the average of the middle two values as the median
			else
				if(arr.length%2==0)
				{
					float one=kthSmallest(arr, arr.length/2);
					float two=kthSmallest(arr, arr.length/2+1);
					float result = (one+two)/2;
					return result;
				}
				else
				//if odd number of response times, then simply return the middle value	
				return kthSmallest(arr, arr.length/2+1);
	
	}
	
	/*
	 * It takes the first element in arrays as the pivot element and partitions the array
	 * such that all the elements less than pivot are to the left and all the elements greater than pivot are to the right 
	 */
	
	public int partition(int[] array, int first, int last) 
	{
	    int pivot = array[first];
	    int pivotPosition = first++;
	    
	    while (first <= last) 
	    {
	        // adjust values less than pivot value
	        while ((first <= last) && (array[first] < pivot)) 
	        {
	            first++;
	        }
	
	        // adjust values greater than pivot values
	        while ((last >= first) && (array[last] >= pivot))
	        {
	            last--;
	        }
	
	        if (first > last) 
	        {
	            // swapping last element with pivot
	            swap(array, pivotPosition, last); 
	        }
	        else 
	        {
	            // swapping first that was greater than the pivot 
	            // & last that was smaller than the pivot
	            swap(array, first, last);
	        }
	    }
	    return last;
	}
	
	//function to swap the two elements in given array
	public void swap(int arr[], int first, int last)
	{
		int temp = arr[first];
		arr[first] = arr[last];
		arr[last] = temp;
				
	}
	
	//Based on the pivot position, if the pivot is at Median's index, then we have found the pivot else
	//if the pivot is larger than median index, search in pivot's left sub-array else search in pivot's right sub-array
	private int orderStatistic( int[] array, int k, int first, int last) 
	{   
	    int pivotPosition = partition(array, first, last);
	    
	    if ( pivotPosition == k - 1) 
	    {
	        return array[k - 1];
	    }
	    if (k - 1 < pivotPosition ) 
	    {
	        return orderStatistic(array, k, first, pivotPosition - 1);
	    }
	    else 
	    {
	        return orderStatistic(array, k, pivotPosition + 1, last);
	    }
	}
	
	public int kthSmallest(int[] array, int k) 
	{
	    return orderStatistic(array, k, 0, array.length - 1);
	}

	/*
	 * The databstream can be very large and it is highly  
	 * So basically, you calculate the mean of elements 1 and 2, that becomes you new element 1. 
	 * the mean of 3 and 4 is your new second element, and so on.
	 *  Then apply the same algorithm to the new list until only 1 element remains. 
	 *  If you encounter a list of odd size, either add an element at the end or ignore the last one. 
	 *  If you add one, you should try to get as close as possible to your expected mean.
	 *  While this won't calculate the mean mathematically exactly, for lists of that size, it will be sufficiently close.
	 */
	
	public float getAverage(float previousAvg, int x, int n)
	{
	    return (previousAvg*n + x)/(n+1);
	}
	 
	// Prints average of a stream of numbers
	public float streamAvg()
	{
		int[] arr = this.getArray();
		int n = arr.length;
		
		float avg = 0;
	   for(int i = 0; i < n; i++)
	   {
	       avg  = getAverage(avg, arr[i], i);
	       System.out.println("Average of  numbers is "+ avg);
	   }
	   
	   	return avg;
	}
	
	public double getMedianNaive()
	{
		int[] arr = this.getArray();
		Arrays.sort(arr);
		double median;
		if (arr.length % 2 == 0)
		    median = ((double)arr[arr.length/2] + (double)arr[arr.length/2 - 1])/2;
		else
		    median = (double) arr[arr.length/2];
		
		return median;
	}
}

public class StatsCollectorMain
{
	public static void main(String args[]) throws Exception
	{
			StatsCollector obj = new StatsCollector();
			
			//Lets assume that the array values are the response times and we need to eliminate the negative, zero 
			//and values >19000 when pushing inside our ArrayList of the StatsCalculator using it's pushValue() method			
			int arr[] = {1,2,100,200,300,400};
			
			for(int i=0; i<arr.length; i++)
			{
				obj.pushValue(arr[i]);
			}
			
			float median = obj.getMedian();
			float average = obj.streamAvg();
			
			System.out.println("Result of the execution is ");
			System.out.println("Median is "+median);
			System.out.println("Average is "+average);
			
		}

}