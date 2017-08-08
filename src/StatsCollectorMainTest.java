import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class StatsCollectorMainTest 
{
	StatsCollector obj = new StatsCollector();
	
	//returns -1 if the response time is more than 19000 and doesn't add to the arraylist
	@Test
	public void pushValueMoreThan19000Test() throws Exception 
	{
		int val=obj.pushValue(20000);
		Assert.assertEquals(-1, val);
		
	}
	
	//returns -1 if the response time is <0 and doesn't add to the arraylist
	@Test
	public void pushValueLessThan0Test() throws Exception 
	{
		int val=obj.pushValue(-1);
		Assert.assertEquals(-1, val);
			
	}
	
	//returns -1 if the response time is equals 0 and doesn't add to the arraylist
	@Test
	public void pushValueZeroTest() throws Exception 
	{
		int val=obj.pushValue(0);
		Assert.assertEquals(-1, val);
			
	}
	
	//Additionally, the pushValue() takes only integers as it's input parameters in this implementation
	//Java Generics can be used but for keeping it's scope limited, only integers are passed as input parameters
	
	
	//function converting arraylist to array
	@Test
	public void testGetArray() throws Exception
	{
		obj.pushValue(10);
		obj.pushValue(20);
		obj.pushValue(30);
		
		int arr[] = obj.getArray();
		
		for(int i=0; i<arr.length; i++)
		{
			System.out.println(arr[i]);
		}
	}
	
	/*
	 * comparing the get median methods (1. Naive method using array sort and 2.Order statistics ) for run time
	 * 
	 */
	
	@Test
	public void getMedianRuntimeTest() throws Exception 
	{
			StatsCollector obj1 = new StatsCollector();
			Random random = new Random();
			
			for(int i=0;i<10000000;i++)
			{
				obj1.pushValue(random.nextInt(19000));
			}
			
			final long startTime = System.currentTimeMillis();
			obj1.getMedianNaive();
			final long endTime = System.currentTimeMillis();
			long runTime = endTime - startTime;
			System.out.println("Naive method run time in milliseconds "+runTime);

			for(int k=3;k<1000000;k++)
			{
				obj1.pushValue(random.nextInt(19000));
			}
			final long startTime1 = System.currentTimeMillis();
			obj1.getMedian();
			final long endTime1 = System.currentTimeMillis();
			long runTime1 = endTime1 - startTime1;
			System.out.println("Order statistics run time in milliseconds "+runTime1);

	}

}
