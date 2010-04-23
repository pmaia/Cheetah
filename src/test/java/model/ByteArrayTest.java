package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import exceptions.IllegalAccessException;

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
	public void readMoreBytesThanAvailableTest() throws IllegalAccessException {
		Allocation byteArrayAllocation = resource.allocate(10, 30, AllocationType.BYTE_ARRAY );

		byte [] data = "a".getBytes();

		byteArrayAllocation.write(data, 0, 0, data.length);
		
		byte [] readData = byteArrayAllocation.read(0, 10);
		
		assertEquals(data.length, readData.length);
	}
	
	@Test(expected=exceptions.IllegalAccessException.class)
	public void writeMoreBytesThanMaxSize() throws IllegalAccessException {
		Allocation byteArray = resource.allocate(2, 30, AllocationType.BYTE_ARRAY);
		
		byte [] data = "abc".getBytes();
		
		byteArray.write(data, 0, 0, data.length);
	}
	
	/*
	 * FIXME como implementar restricoes baseadas em concorrencia? Nao adianta apenas colocar um mutex ou semaphore em Allocation do jeito que 
	 * ta implementado agora, pq uma leitura/escrita pode ser feita de forma parcial (utilizando um buffer temporario).
	 * Talvez exista uma forma de resolver encapsulado leituras e escritas em Input/Output streams
	 * 
	 */
}
