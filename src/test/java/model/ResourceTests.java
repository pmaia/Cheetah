package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 
 * @author Patrick Maia
 *
 */
public class ResourceTests {
	
	@Test
	public void allocateTest() {
		ResourceConfig configuration = new ResourceConfig();
		
		configuration.setDurationLimit(ResourceConfig.INFINITY_DURATION);
		Resource resource = new Memory(configuration);
		
		Allocation allocation = resource.allocate(1024, 60, AllocationType.BYTE_ARRAY);
		
		assertEquals(1024, allocation.getMaxSize());
		assertTrue(allocation instanceof ByteArray);
	}
	
}
