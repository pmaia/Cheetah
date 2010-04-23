package model;

import java.util.Calendar;
import java.util.Date;

import com.sleepycat.persist.model.Entity;

import exceptions.IllegalAccessException;

/**
 * 
 * @author Patrick Maia
 *
 */
@Entity
public abstract class Allocation {

	private String	name;
	
	private String 	manageKey;
	
	private String 	writeKey;
	
	private String 	readKey;
	
	private long 	maxSize;
	
	private long 	currentSize;
	
	private Date	expirationTime;
	
	public Allocation() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2200, Calendar.DECEMBER, 31);
		
		expirationTime = calendar.getTime();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setManageKey(String manageKey) {
		this.manageKey = manageKey;
	}
	
	public String getManageKey() {
		return manageKey;
	}
	
	public void setWriteKey(String writeKey) {
		this.writeKey = writeKey;
	}

	public String getWriteKey() {
		return writeKey;
	}
	
	public void setReadKey(String readKey) {
		this.readKey = readKey;
	}

	public String getReadKey() {
		return readKey;
	}
	
	public void setCurrentSize(long currentSize) {
		this.currentSize = currentSize;
	}
	
	public long getCurrentSize() {
		return currentSize;
	}
	
	public long getMaxSize() {
		return maxSize;
	}
	
	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
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
	 * @throws IllegalAccessException 
	 */
	public abstract void write(byte[] data, int dataStartOffset, long allocationStartOffset, int length) throws IllegalAccessException;

	/**
	 * Reads up to length bytes from the Allocation 
	 * 
	 * @param allocationStartOffset the position in the Allocation to start reading
	 * @param length the amount of bytes to read
	 * @return a array of bytes containing the read data
	 */
	public abstract byte[] read(int allocationStartOffset, int length);

}
