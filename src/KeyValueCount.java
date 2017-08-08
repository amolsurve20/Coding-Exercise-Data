import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class KeyValueCount 
{
	//ConcurrentHashMap is used to store the key value pairs and it's a tread safe data structure as well
	public static ConcurrentHashMap<String, HashSet<String>> map = new ConcurrentHashMap<String,HashSet<String>>();
	
	public static void main(String args[]) throws IOException
	{
		//I have created the text files with sample key value pairs and stored in the source package to use the relative path as shown
		String filePaths[] = 
			{
				"src/key_val1.txt",
				"src/key_val2.txt", 
				"src/key_val3.txt"
			};
		
		//multipleFileInput(filePaths);
		ConcurrentHashMap<String, HashSet<String>> map = computeFileValues(filePaths);
		printMap(map);
		System.out.println("-------------------------------");
		consoleInput();
		
	}
	
	//consoleInput() takes the input from the console and prints the map entries once the user is done adding the values
	public static void consoleInput()
	{
		//Scanner input to take the input values from the console
		 Scanner stdin = new Scanner(System.in);
		 
		 //HashMap of String as a key and ArrayList of string as a value to store key with corresponding HashSet of values
		 HashMap<String, HashSet<String>> map = new HashMap<String,HashSet<String>>();
		 System.out.println("Enter the input");

	     String input = "";
	     Scanner keyboard = new Scanner(System.in);
	     String line;
	     
	     while (keyboard.hasNextLine()) 
	     {
		         line = keyboard.nextLine();
		         if (line.isEmpty())
		         {
		             break;
		         }
		         
		        String[] tokens = line.split(",");
		        String key = tokens[0];
		        String value = tokens[1];
		       // System.out.println(key);
		       // System.out.println(value);
		        
		        if(!map.containsKey(key))
		        {
		        	map.put(key, new HashSet<String>());
		        }  
		        
		        map.get(key).add(value);
	     }

	     	//printing the HashMap to display the key value pairs
		 	Iterator it = map.entrySet().iterator();
		 
		    while (it.hasNext())
		    {
		        Map.Entry pair = (Map.Entry)it.next();
		        System.out.println(pair.getKey() + " = " + pair.getValue());
		        it.remove(); 
		    }
	
	}
	
	/*
	 * computeFileValues() takes the array of file paths as input and stores the key value pairs using the ConcurrentHashMap
	 * of string as keys and ArrayList of Strings as value
	 */
	public static ConcurrentHashMap<String, HashSet<String>> computeFileValues(String filePaths[]) throws IOException    
	{
		for(String file:filePaths)
		{
			System.out.println("Reading file "+ file);
			  String line;
			  FileReader fr = new FileReader(file);
		      @SuppressWarnings("resource")
		      BufferedReader reader = new BufferedReader(fr);
		      
		      //the file read logic reads the entire file and doesn't terminate till the end of file to take care of the missing lines
		      while ((line = reader.readLine()) != null) 
		      {
		    	    if(line.length() > 0) 
		    	    {
		    	    	String[] tokens = line.split(",");
				        String key = tokens[0];
				        String value = tokens[1];
				        //System.out.println(key);
				        //System.out.println(value);
				        if(!map.containsKey(key))
				        {
				        	map.put(key, new HashSet<String>());
				        } 
				        
				        map.get(key).add(value);
		    	    }
		      }
		      fr.close();
		
		}
		      
		      return map;
	}
	
	//printMap() takes the concurrent hashMap as it's input parameter and prints the key value pairs 
	public static void printMap(ConcurrentHashMap<String, HashSet<String>> map)
	{
			Iterator it = map.entrySet().iterator();
				 
			while (it.hasNext())
			{
				Map.Entry pair = (Map.Entry)it.next();
				System.out.println(pair.getKey() + " = " + pair.getValue());
				it.remove(); 
			}
		
	}

}

