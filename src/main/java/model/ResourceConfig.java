package model;

/**
 * 
 * @author Patrick Maia
 *
 */
public class ResourceConfig {
	
	public static final int INFINITY_DURATION = -1;
	
	private long maxDuration = -1;
	
	private long maxSize = 0;

	public long getMaxDuration() {
		return maxDuration;
	}

	public void setDurationLimit(long maxDuration) {
		this.maxDuration = maxDuration;
	}

	public long getMaxSize() {
		return maxSize;
	}

	public void setSpaceLimit(long maxSize) {
		this.maxSize = maxSize;
	}
	
}
