package model;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Patrick Maia
 *
 */
public class Resource {
	
	private static int nameSequence = 0;
	
	private Map<String, Allocation> allocations = new HashMap<String, Allocation>();
	
	public enum Type {
		MEMORY
	}
	
	public Resource(Type type) {
		
	}
	
	public Allocation allocate(long size, long duration, Allocation.Type type) {
		Allocation allocation = new Allocation("allocation-" + nameSequence++, duration);
		
		allocations.put(allocation.getName(), allocation);
		
		return allocation;
	}
	
	public Allocation getAllocation(String name) {
		return allocations.get(name);
	}
}
