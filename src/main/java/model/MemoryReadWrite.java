package model;

/**
 * 
 * @author Patrick Maia
 *
 */
public class MemoryReadWrite implements ReadWrite {
	
	private byte [] data;
	
	public MemoryReadWrite(int size) {
		data = new byte[size];
	}

	@Override
	public byte[] read(int allocationStartOffset, int length) {
		byte [] result = new byte[length];
		
		System.arraycopy(data, allocationStartOffset, result, 0, length);
		
		return result;
	}

	@Override
	public void write(byte[] data, int dataStartOffset, long allocationStartOffset, int length) {
		if(this.data.length < dataStartOffset + length) {
			byte [] temp = new byte[dataStartOffset + length];
			
			System.arraycopy(this.data, 0, temp, 0, this.data.length);
			
			this.data = temp;
		}
		
		System.arraycopy(data, dataStartOffset, this.data, (int)allocationStartOffset, length);
	}

}
