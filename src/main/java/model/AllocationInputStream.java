package model;

/**
 * 
 * @author Patrick Maia
 *
 */
public interface AllocationInputStream {
	
	void close();
	
	int read(byte[] buffer, int allocationStartOffset, int length);
	
}
