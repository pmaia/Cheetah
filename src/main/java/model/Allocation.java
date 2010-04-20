package model;

import java.util.Date;

import com.sleepycat.persist.model.Entity;

/**
 * 
 * @author Patrick Maia
 *
 */
@Entity
public class Allocation {
	
	private AllocationDriver driver = new MemoryAllocationDriver();
	
	private String	name;
	
	private String 	manageKey;
	
	private String 	writeKey;
	
	private String 	readKey;
	
	private Date	expirationTime;
	
	public static enum Type {
		BYTE_ARRAY
	}
	
	/**
	 * Creates a new Allocation object
	 * 
	 * @param name the Allocation's name
	 * @param duration the Allocation's duration in seconds
	 */
	public Allocation(String name, long duration) {
		this.name = name;
		expirationTime = new Date(System.currentTimeMillis() + (duration * 1000));
	}
	
	public String getName() {
		return name;
	}
	
	public String getManageKey() {
		return manageKey;
	}

	public String getWriteKey() {
		return writeKey;
	}

	public String getReadKey() {
		return readKey;
	}

	/**
	 * Increases the Allocation duration
	 * 
	 * @param addend the amount of time in seconds in which the duration must be augmented
	 */
	public void increaseDuration(long addend) {
		expirationTime = new Date(expirationTime.getTime() + (addend * 1000));
	}

	public Date getExpirationTime() {
		return expirationTime;
	}

	/**
	 * Writes up to length bytes in the Allocation 
	 * 
	 * @param data the data to store in the Allocation
	 * @param dataStartOffset the start offset in the data array
	 * @param allocationStartOffset the start offset in the allocation
	 * @param length the amount of bytes to write
	 */
	public void write(byte[] data, int dataStartOffset, long allocationStartOffset, int length) {
		driver.write(data, dataStartOffset, allocationStartOffset, length);		
	}

	/**
	 * Reads up to length bytes from the Allocation 
	 * 
	 * @param allocationStartOffset the position in the Allocation to start reading
	 * @param length the amount of bytes to read
	 * @return a array of bytes containing the read data
	 */
	public byte[] read(int allocationStartOffset, int length) {
		return driver.read(allocationStartOffset, length);
	}

}
