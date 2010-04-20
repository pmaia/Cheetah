package model;

/**
 * 
 * @author Patrick Maia
 *
 */
public interface AllAllocations {

	void add(Allocation allocation);

	Allocation get(String name);
	
	void update(Allocation allocation);

}
