package model;

import java.util.concurrent.locks.ReentrantLock;

import exceptions.AllocationIllegalAccessException;


/**
 * Represents a allocation of type IBP_BYTEARRAY which treats the allocated area as a flat bytearray. 
 * This will have the following implications on future accesses to that storage area:
 * <br>
 * <li>Only the amount of available data will be returned if there are not enough data to satisfy the read request at the time 
 * the request is received by the IBP server.
 * <li>Requests to write (append) to the allocated area will be denied if it leads to the total size of the storage area exceeding the 
 * maximum allowable size specified in size.
 * <li>A maximum of one write operation can be actively writing to the storage area at any given time; other write requests received by the server 
 * are queued pending completion of the running write process.
 * <li>No limit is imposed on the number of simultaneous read accesses to the storage area. In addition, due to the use of append-only 
 * semantics for write operations, a write operation can be simultaneously active with any number of read operations to the same storage area.   
 * @author Patrick Maia
*/
public class ByteArray extends Allocation {
	
	private AllocationDriver allocationDriver;
	private ByteArrayInputStream inputStream;
	private ByteArrayOutputStream outputStream;
	
	public ByteArray(AllocationDriver allocationDriver) {
		this.allocationDriver = allocationDriver;
		
		inputStream = new ByteArrayInputStream();
		outputStream = new ByteArrayOutputStream();
	}

	@Override
	public AllocationInputStream getInputStream() {
		return inputStream;
	}

	@Override
	public AllocationOutputStream getOutputStream() {
		return outputStream;
	}

	private class ByteArrayOutputStream implements AllocationOutputStream {
		
		private ReentrantLock writeLock = new ReentrantLock();
		
		@Override
		public void close() {
			writeLock.unlock();
		}

		@Override
		public int write(byte[] data, long allocationStartOffset, int length) throws AllocationIllegalAccessException {
			writeLock.lock();
		
			if(allocationStartOffset + length > getMaxSize()) {
				throw new AllocationIllegalAccessException();
			} else if(allocationStartOffset + length > getCurrentSize()) {
				setCurrentSize(allocationStartOffset + length);
			}

			allocationDriver.write(data, allocationStartOffset, length);
			
			return length;
		}
		
	}
	
	private class ByteArrayInputStream implements AllocationInputStream {

		@Override
		public void close() { }

		@Override
		public int read(byte[] buffer, int allocationStartOffset, int length) {

			if(allocationStartOffset + length > getCurrentSize()) {
				length = (int)(getCurrentSize() - allocationStartOffset);
			}
			
			allocationDriver.read(buffer, allocationStartOffset, length);
			
			return length;
		}
		
	}
}
