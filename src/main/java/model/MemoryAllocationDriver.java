package model;

/**
 * 
 * @author Patrick Maia
 *
 */
public class MemoryAllocationDriver implements AllocationDriver {
	
	private byte [] allocData;
	
	/**
	 * Creates a MemoryAllocationDriver with the specified initial capacity 
	 * 
	 * @param initialCapacity the internal buffer initial capacity
	 */
	public MemoryAllocationDriver(long initialCapacity) {
		//FIXME implementar de modo a nao ser limitiada pelo tamanho maximo de um array
		allocData = new byte[(int)initialCapacity];
	}
	
	/**
	 * Creates a MemoryAllocationDriver with the default initial capacity (64 Kb)
	 */
	public MemoryAllocationDriver() {
		this(64 * 1024);
	}

	@Override
	public void read(byte[]  buffer, int allocationStartOffset, int length) {
		System.arraycopy(allocData, allocationStartOffset, buffer, 0, length);
	}

	@Override
	public void write(byte[] data, long allocationStartOffset, int length) {
		if(allocData.length < allocationStartOffset + length) {
			byte [] temp = new byte[(int)allocationStartOffset + length];
			
			System.arraycopy(allocData, 0, temp, 0, allocData.length);
			
			allocData = temp;
		}
		
		System.arraycopy(data, 0, this.allocData, (int)allocationStartOffset, length);
	}

}
