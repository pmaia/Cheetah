package model;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

import exceptions.AllocationIllegalAccessException;

/**
 * 
 * @author Patrick Maia
 *
 */
public class ByteArrayTest {
	
	Resource resource;
	
	@Before
	public void setup() {
		ResourceConfig configuration = new ResourceConfig();
		configuration.setDurationLimit(ResourceConfig.INFINITY_DURATION);
		configuration.setSpaceLimit(1024 * 1024);
		
		resource = new Memory(configuration);
	}
	
	@Test
	public void readMoreBytesThanAvailableTest() throws AllocationIllegalAccessException {
		Allocation byteArrayAllocation = resource.allocate(10, 30, AllocationType.BYTE_ARRAY );
		AllocationOutputStream aos = byteArrayAllocation.getOutputStream();
		AllocationInputStream ais = byteArrayAllocation.getInputStream();
		
		byte [] data = "a".getBytes();

		aos.write(data, 0, data.length);
		aos.close();

		byte [] readData = new byte[10]; 
			
		int read = ais.read(readData, 0, 10);
		ais.close();
		
		assertEquals(data.length, read);
	}
	
	@Test(expected=AllocationIllegalAccessException.class)
	public void writeMoreBytesThanMaxSize() throws AllocationIllegalAccessException {
		Allocation byteArrayAllocation = resource.allocate(2, 30, AllocationType.BYTE_ARRAY);
		AllocationOutputStream aos = byteArrayAllocation.getOutputStream();
		
		byte [] data = "abc".getBytes();
		
		aos.write(data, 0, data.length);
		aos.close();
	}
	
	@Test
	public void concurrentWriteTest() {
		final byte [] data = "Cheetah".getBytes();
		Allocation byteArrayAllocation = resource.allocate(data.length, 30, AllocationType.BYTE_ARRAY);
		final AllocationOutputStream aos = byteArrayAllocation.getOutputStream();
		final AtomicInteger count = new AtomicInteger();
		final String [] executionOrder = new String[10];
		
		Runnable runnable = new Runnable () {
			public void run() {
				try {
					for(int i = 0; i < 5; i++) {
						aos.write(data, 0, data.length);
						executionOrder[count.getAndIncrement()] = Thread.currentThread().getName();
					}
				} catch (AllocationIllegalAccessException e) {
					e.printStackTrace();
				} finally {
					aos.close();
				}
			}
		};
		
		new Thread(runnable).start();
		
		try {
			for(int i = 0; i < 5; i++) {
				aos.write(data, 0, data.length);
				executionOrder[count.getAndIncrement()] = Thread.currentThread().getName();
			}
		} catch (AllocationIllegalAccessException e) {
			e.printStackTrace();
		} finally {
			aos.close();
		}
		
		for(int i = 0; i < 4; i++) {
			assertEquals(executionOrder[i], executionOrder[i + 1]);
		}
	}
	
	/**
	 * Reads in a ByteArrayAllocation aren't mutual exclusive.
	 * Success in this test means that both threads finalized without the necessity of close the read streams. 
	 */
	@Test
	public void concurrentReadTest() throws AllocationIllegalAccessException {
		final byte [] data = "Cheetah".getBytes();
		
		Allocation byteArrayAllocation = resource.allocate(data.length, 30, AllocationType.BYTE_ARRAY);
		
		AllocationOutputStream aos = byteArrayAllocation.getOutputStream();
		aos.write(data, 0, data.length);
		aos.close();
		
		final AllocationInputStream ais = byteArrayAllocation.getInputStream();
		final byte [] readBuffer = new byte[data.length];
		
		Runnable runnable = new Runnable () {
			public void run() {
				for(int i = 0; i < 100; i++) {
					ais.read(readBuffer, 0, data.length);
				}
			}
		};
		
		new Thread(runnable).start();
		
		for(int i = 0; i < 100; i++) {
			ais.read(readBuffer, 0, data.length);
		}

	}
}
