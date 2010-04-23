package model;

import java.util.HashMap;
import java.util.Map;

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
	public Allocation allocate(long size, long duration, AllocationType type) {
		//pre conditions: space and durations constraints
		Allocation allocation = AllocationFactory.create(type, new MemoryAllocationDriver(size));
		
		allocation.setName(randomName());
		allocation.setManageKey(randomName());
		allocation.setReadKey(randomName());
		allocation.setWriteKey(randomName());
		allocation.setMaxSize(size);
		//FIXME duration deve ser um multiplo de 5 minutos para facilitar o trabalho de indexacao das allocacoes para o coletor de alocacoes expiradas
		allocation.increaseDuration(duration);
		
		allAllocations.put(allocation.getName(), allocation);
		
		return allocation;
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
