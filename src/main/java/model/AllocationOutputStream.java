package model;

import exceptions.AllocationIllegalAccessException;

/**
 * 
 * @author Patrick Maia
 *
 */
public interface AllocationOutputStream {
	
	void close();
	
	int write(byte[] data, long allocationStartOffset, int length) throws AllocationIllegalAccessException;
	
}
