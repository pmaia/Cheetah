package model;

/**
 * 
 * 
 * @author Patrick Maia
 *
 */
public interface AllocationDriver {
	
	/**
	 * Writes up to length bytes in the Allocation 
	 * 
	 * @param data the data to store in the Allocation
	 * @param allocationStartOffset the start offset in the allocation
	 * @param length the amount of bytes to write
	 */
	void write(byte[] data, long allocationStartOffset, int length);

	/**
	 * Reads up to length bytes from the Allocation 
	 * 
	 * @param allocationStartOffset the position in the Allocation to start reading
	 * @param length the amount of bytes to read
	 * @return a array of bytes containing the read data
	 */
	byte[] read(int allocationStartOffset, int length);
	
}
