package model;

/**
 * 
 * @author Patrick Maia
 *
 */
public class MemoryAllocationDriver implements AllocationDriver {
	
	private byte [] buffer;
	
	/**
	 * Creates a MemoryAllocationDriver with the specified initial capacity 
	 * 
	 * @param initialCapacity the internal buffer initial capacity
	 */
	public MemoryAllocationDriver(long initialCapacity) {
		//FIXME implementar de modo a nao ser limitiada pelo tamanho maximo de um array
		buffer = new byte[(int)initialCapacity];
	}
	
	/**
	 * Creates a MemoryAllocationDriver with the default initial capacity (64 Kb)
	 */
	public MemoryAllocationDriver() {
		this(64 * 1024);
	}

	@Override
	public byte[] read(int allocationStartOffset, int length) {
		byte [] result = new byte[length];
		
		System.arraycopy(buffer, allocationStartOffset, result, 0, length);
		
		return result;
	}

	@Override
	public void write(byte[] data, long allocationStartOffset, int length) {
		if(buffer.length < allocationStartOffset + length) {
			byte [] temp = new byte[(int)allocationStartOffset + length];
			
			System.arraycopy(buffer, 0, temp, 0, buffer.length);
			
			buffer = temp;
		}
		
		System.arraycopy(data, 0, this.buffer, (int)allocationStartOffset, length);
	}

}
