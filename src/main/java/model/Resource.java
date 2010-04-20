package model;


/**
 * 
 * @author Patrick Maia
 *
 */
public class Resource {
	
	private static int nameSequence = 0;
	
	private AllAllocations allAllocations =  new AllAllocationsInMemory();
	
	public static enum Type {
		MEMORY
	}
	
	public Resource(Type type) {
		
	}
	
	public Allocation allocate(long size, long duration, Allocation.Type type) {
		Allocation allocation = new Allocation("allocation-" + nameSequence++, duration);
		
		allAllocations.add(allocation);
		
		return allocation;
	}
	
	public Allocation getAllocation(String name) {
		return allAllocations.get(name);
	}
	
	public boolean isAvailable() {
		return true;
	}
}
