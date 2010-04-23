package model;

import static org.junit.Assert.assertEquals;

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

		byte [] readData = new byte[10]; 
			
		int read = ais.read(readData, 0, 10);
		
		assertEquals(data.length, read);
	}
	
	@Test(expected=exceptions.AllocationIllegalAccessException.class)
	public void writeMoreBytesThanMaxSize() throws AllocationIllegalAccessException {
		Allocation byteArrayAllocation = resource.allocate(2, 30, AllocationType.BYTE_ARRAY);
		AllocationOutputStream aos = byteArrayAllocation.getOutputStream();
		
		byte [] data = "abc".getBytes();
		
		aos.write(data, 0, data.length);
	}
	
	/*
	 * FIXME como implementar restricoes baseadas em concorrencia? Nao adianta apenas colocar um mutex ou semaphore em Allocation do jeito que 
	 * ta implementado agora, pq uma leitura/escrita pode ser feita de forma parcial (utilizando um buffer temporario).
	 * Talvez exista uma forma de resolver encapsulado leituras e escritas em Input/Output streams
	 * 
	 */
}
