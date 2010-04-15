package model;

/**
 * 
 * @author Patrick Maia
 *
 */
public interface AllocationDriver {
	public byte[] read(int allocationStartOffset, int length);
	
	public void write(byte[] data, int dataStartOffset, long allocationStartOffset, int length);
}
