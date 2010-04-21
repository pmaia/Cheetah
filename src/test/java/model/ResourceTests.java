package model;

import static org.junit.Assert.assertEquals;

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
		
		configuration.setMaxDuration(ResourceConfig.INFINITY_DURATION);
		Resource resource = new Memory(configuration);
		
		Allocation allocation = resource.allocate(1024, 60, Allocation.Type.BYTE_ARRAY);
		
		assertEquals(1024, allocation.getMaxSize());
		assertEquals(Allocation.Type.BYTE_ARRAY, allocation.getType());
	}
	
}
