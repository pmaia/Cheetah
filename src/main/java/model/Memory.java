package model;

import java.util.HashMap;
import java.util.Map;

import model.Allocation.Type;

/**
 * 
 * @author Patrick Maia
 *
 */
public class Memory extends Resource {
	
	private Map<String, Allocation> allAllocations = new HashMap<String, Allocation>();
	
	public Memory(ResourceConfig configuration) {
		super(configuration);
	}

	@Override
	public Allocation allocate(long size, long duration, Type type) {
		return null;
	}

	@Override
	public Allocation getAllocation(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAvailable() {
		return true;
	}

}
