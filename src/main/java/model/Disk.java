package model;

import java.io.File;


/**
 * 
 * @author Patrick Maia
 *
 */
public class Disk extends Resource {
	
	private final File location;
	
	public Disk(ResourceConfig configuration, File location) {
		super(configuration);
		
		this.location = location;
	}
	
	public Allocation allocate(long size, long duration, Allocation.Type type) {
//		Allocation allocation = new Allocation("allocation-" + nameSequence++, duration);
//		
//		allAllocations.add(allocation);
//		
//		return allocation;
		
		return null;
	}
	
	public Allocation getAllocation(String name) {
//		return allAllocations.get(name);
		
		return null;
	}
	
	public boolean isAvailable() {
		return location.exists();
	}
}
