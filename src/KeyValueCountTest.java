import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.Assert;
import org.junit.Test;

public class KeyValueCountTest extends KeyValueCount 
{
	public static ConcurrentHashMap<String, HashSet<String>> map = new ConcurrentHashMap<String,HashSet<String>>();
	
	String filePaths[] = 
		{
			"src/key_val1.txt",
			"src/key_val2.txt", 
			"src/key_val3.txt"
		};
	
	//file read and merging the key values into the hashMap where key is string and value is HashSet
	//Checking whether the HashSet returned by the key1 is same
	@Test
	public void filereadKeyValueTest() throws IOException 
	{
		ConcurrentHashMap<String, HashSet<String>> map = KeyValueCount.computeFileValues(filePaths);
		HashSet<String>hs = new HashSet<String>();
		
		hs.add("value2");
		hs.add("value1");
		hs.add("value3");
		hs.add("value9");
		
		Assert.assertEquals(hs,map.get("key1") );

	}
	
	//console input and output test
	@Test
	public void consoleKeyValueTest() throws IOException 
	{
		KeyValueCount.consoleInput();
	}

}
