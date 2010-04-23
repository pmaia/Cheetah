package model;

import java.util.UUID;


/**
 * 
 * @author Patrick Maia
 *
 */
public abstract class Resource {
	
	private ResourceConfig configuration;
	
	public Resource(ResourceConfig configuration) {
		this.configuration = configuration;
	}
	
	public ResourceConfig getConfiguration() {
		return configuration;
	}
	
	protected String randomName() {
		return UUID.randomUUID().toString();
	}
	
	public abstract Allocation allocate(long size, long duration, AllocationType type);
	
	//TODO ver se eh melhor retornar nulo ou lancar uma excecao para o caso da alocacao nao existir
	public abstract Allocation getAllocation(String name);
	
	public abstract  boolean isAvailable();
}
