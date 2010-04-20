package model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.Date;

import org.junit.Test;

/**
 * 
 * @author Patrick Maia
 *
 */
public class ResourceTests {
	
	@Test
	public void allocateTest() {
		Resource resource = new Resource(Resource.Type.MEMORY);
		
		long size = 1;
		long duration = 5 * 60;
		
		Allocation allocation1 = resource.allocate(size, duration, Allocation.Type.BYTE_ARRAY);
		Allocation allocation2 = resource.allocate(size, duration, Allocation.Type.BYTE_ARRAY);
		
		Allocation allocationRead1 = resource.getAllocation(allocation1.getName());
		Allocation allocationRead2 = resource.getAllocation(allocation2.getName());
		
		assertEquals(allocation1, allocationRead1);
		assertEquals(allocation2, allocationRead2);
		
		assertNotSame(allocation1, allocation2);
	}
	
	@Test
	public void updateAllocationTest() {
		Resource resource = new Resource(Resource.Type.MEMORY);
		
		long size = 1;
		long duration = 5 * 60; // 5 min
		
		Allocation allocation = resource.allocate(size, duration, Allocation.Type.BYTE_ARRAY);
		
		Date oldTime = allocation.getExpirationTime();
		
		allocation.increaseDuration(1000);
		
		Allocation allocationRead = resource.getAllocation(allocation.getName());
		
		assertEquals(new Date(oldTime.getTime() + (1000 * 1000)), allocationRead.getExpirationTime());
	}
	
	@Test
	public void allocationReadWriteTest() {
		Resource resource = new Resource(Resource.Type.MEMORY);
		
		long size = 1;
		long duration = 5 * 60; // 5 min
		
		Allocation allocation = resource.allocate(size, duration, Allocation.Type.BYTE_ARRAY);
		
		byte [] data = "Cheetah: the fastest IBP server".getBytes();
		
		allocation.write(data, 0, 0, data.length);
		
		byte [] readData = allocation.read(0, data.length);
		
		assertArrayEquals(data, readData);
	}
	
}
