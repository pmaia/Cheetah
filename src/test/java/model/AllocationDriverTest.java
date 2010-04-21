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
		
		allocDriver.write(data, 0, 0, data.length);
		
		byte [] readData = allocDriver.read(0, data.length);
		
		assertEquals("Cheetah", new String(readData));
	}
	
	@Test
	public void writeReadWithAllocationOffsetTest() {
		byte [] data = "Cheetah".getBytes();
		
		allocDriver.write(data, 0, 10, data.length);
		
		byte [] readData = allocDriver.read(10, data.length);
		
		assertEquals("Cheetah", new String(readData));
	}
	
	@Test
	public void writeReadWithDataOffset() {
		byte [] data = "Cheetah Cheetah".getBytes();
		
		allocDriver.write(data, 8, 0, data.length - 8);
		
		byte [] readData = allocDriver.read(0, data.length - 8);
		
		assertEquals("Cheetah", new String(readData));
	}
	 
}
