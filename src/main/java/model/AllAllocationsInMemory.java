package model;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Patrick Maia
 *
 */
public class AllAllocationsInMemory implements AllAllocations {
	
	private Map<String, Allocation> allocations = new HashMap<String, Allocation>();

	@Override
	public void add(Allocation allocation) {
		allocations.put(allocation.getName(), allocation);
	}

	@Override
	public Allocation get(String name) {
		return allocations.get(name);
	}

	@Override
	public void update(Allocation allocation) {
		allocations.put(allocation.getName(), allocation);
	}

}
