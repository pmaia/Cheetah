package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Patrick Maia
 *
 */
public class AllocationDriverTest {
	
	AllocationDriver allocDriver;
	
	@Before
	public void setup() {
		allocDriver = new MemoryAllocationDriver();
	}

	@Test
	public void writeReadTest() {
		byte [] data = "Cheetah".getBytes();
		
		allocDriver.write(data, 0, data.length);
		
		byte [] readData = new byte[data.length]; 
			
		allocDriver.read(readData, 0, data.length);
		
		assertEquals("Cheetah", new String(readData));
	}
	
	@Test
	public void writeReadWithAllocationOffsetTest() {
		byte [] data = "Cheetah".getBytes();
		
		allocDriver.write(data, 10, data.length);
		
		byte [] readData = new byte[data.length];
		
		allocDriver.read(readData, 10, data.length);
		
		assertEquals("Cheetah", new String(readData));
	}
	 
}
