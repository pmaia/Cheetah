package model;

public class AllocationFactory {
	
	public static final Allocation create(AllocationType type, AllocationDriver allocationDriver) {
		
		Allocation allocation = null;
		
		switch(type) {
			case BYTE_ARRAY: 		allocation = new ByteArray(allocationDriver); break;
			case BUFFER:			allocation = new Buffer(); break;
			case CIRCULAR_QUEUE:	allocation = new CircularQueue(); break;
			case FIFO:				allocation = new FIFO(); break;
		}
		
		return allocation;
	}
	
}
