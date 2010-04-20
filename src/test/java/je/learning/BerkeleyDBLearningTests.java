package je.learning;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.naming.ConfigurationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.EnvironmentNotFoundException;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityIndex;
import com.sleepycat.persist.EntityJoin;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.ForwardCursor;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import com.sleepycat.persist.StoreConfig;

/**
 * 
 * @author Patrick Maia
 *
 */
public class BerkeleyDBLearningTests {
	
	private File environmentDir;
	
	@Before
	public void setup() throws IOException, ConfigurationException {
		String tempDir = System.getProperty("java.io.tmpdir");
		
		if(!tempDir.endsWith(File.separator))
			tempDir += File.separator;
		
		environmentDir = new File(tempDir + "dbEnv-" + System.currentTimeMillis());
		
		if(!environmentDir.mkdir()) {
			throw new ConfigurationException("Could not create temp dir to hold a JE Environment");
		}
	}
	
	@After
	public void cleanUp() {
		deleteDir(environmentDir);
	}
	
	private void deleteDir(File dir) {
		String[] envContents = dir.list();
		
		for(String envContent : envContents ) {
			File contentFile = new File(dir, envContent);
			
			if(contentFile.isDirectory()) {
				deleteDir(contentFile);
			} else {
				contentFile.delete();
			}
		}
		
		dir.delete();
	}
	
	/**
	 * The Environment instantiation fails once EnvironmentConfig.setAllowCreate() isn't set to true and the environment doesn't exist yet
	 */
	@Test(expected=EnvironmentNotFoundException.class)
	public void creationFailedTest() {
		EnvironmentConfig envConfig = new EnvironmentConfig();
		
		@SuppressWarnings("unused")
		Environment myDbEnvironment = new Environment(environmentDir, envConfig);
	}

	@Test
	public void environmentCreationTest() {
		Environment myDbEnvironment1 = null;
		
		try {
			EnvironmentConfig envConfig = new EnvironmentConfig();
			
			envConfig.setAllowCreate(true);
			envConfig.setSharedCache(true);
			
			myDbEnvironment1 = new Environment(environmentDir, envConfig);
			
			/*
			 	If you want to make sure that the cleaner has finished running before the environment
				is closed, call Environment.cleanLog() before calling Environment.close():
			 */
			myDbEnvironment1.cleanLog();
			
			/*
			  	If you are using the DPL, then close your environment(s) only after all other store activities
				have completed and you have closed any stores currently opened in the environment. If you
				are using the base API, then close your environment(s) only after all other database activities
				have completed and you have closed any databases currently opened in the environment. 
			 */
			myDbEnvironment1.close();
		} catch (DatabaseException dbe) {
			dbe.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void storeCreationTest() {
		EnvironmentConfig envConfig = new EnvironmentConfig();
		
		envConfig.setAllowCreate(true);
		
		Environment myDbEnvironment = new Environment(environmentDir, envConfig);
		
		StoreConfig storeConfig = new StoreConfig();
		
		storeConfig.setAllowCreate(true);
		
		EntityStore entityStore = new EntityStore(myDbEnvironment, "PersonStore", storeConfig);
		
		assertEquals("PersonStore", entityStore.getStoreName());
		
		entityStore.close();
		myDbEnvironment.close();
	}
	
	@Test
	public void savingRetrievingTest() {
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);
		
		Environment myDbEnvironment = new Environment(environmentDir, envConfig);
		
		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setAllowCreate(true);
		
		EntityStore entityStore = new EntityStore(myDbEnvironment, "PersonStore", storeConfig);
		
		PrimaryIndex<String, Person> primaryIndex = entityStore.getPrimaryIndex(String.class, Person.class);
		
		Person person = new Person("Patrick Maia");
		
		primaryIndex.put(person);
		
		Person retrieved = primaryIndex.get("Patrick Maia");
		
		assertEquals(person.getName(), retrieved.getName());
		
		entityStore.close();
		myDbEnvironment.close();
	}
	
	@Test
	public void retrieveUsingSecondaryKey() {
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);
		
		Environment myDbEnvironment = new Environment(environmentDir, envConfig);
		
		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setAllowCreate(true);
		
		EntityStore entityStore = new EntityStore(myDbEnvironment, "PersonStore", storeConfig);
		
		Calendar octoberTwentyTwo = Calendar.getInstance();
		octoberTwentyTwo.set(1985, Calendar.OCTOBER, 22);
		
		Person patrick = new Person("Patrick Maia");
		patrick.setBirthday(octoberTwentyTwo.getTime());
		
		PrimaryIndex<String, Person> personByName = entityStore.getPrimaryIndex(String.class, Person.class);
		personByName.put(patrick);
		
		SecondaryIndex<Date, String, Person> personByBirthday = entityStore.getSecondaryIndex(personByName, Date.class, "birthday");
		Person retrieved = personByBirthday.get(octoberTwentyTwo.getTime());
		
		assertEquals(patrick.getName(), retrieved.getName());
		assertEquals(patrick.getBirthday(), retrieved.getBirthday());
		
		entityStore.close();
		myDbEnvironment.close();
	}
	
	@Test
	public void retrieveMultipleElementsWithSameSecondaryKey() {
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);
		
		Environment myDbEnvironment = new Environment(environmentDir, envConfig);
		
		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setAllowCreate(true);
		
		EntityStore entityStore = new EntityStore(myDbEnvironment, "PersonStore", storeConfig);
		
		Calendar octoberTwentyTwo = Calendar.getInstance();
		octoberTwentyTwo.set(1985, Calendar.OCTOBER, 22);
		
		Person patrick = new Person("Patrick Maia");
		patrick.setBirthday(octoberTwentyTwo.getTime());
		
		Person leonardo = new Person("Leonardo Silva");
		leonardo.setBirthday(octoberTwentyTwo.getTime());
		
		Calendar decemberTwentySeven = Calendar.getInstance();
		decemberTwentySeven.set(1964, Calendar.DECEMBER, 27);
		
		Person ana = new Person("Ana Coeli");
		ana.setBirthday(decemberTwentySeven.getTime());
		
		PrimaryIndex<String, Person> personByName = entityStore.getPrimaryIndex(String.class, Person.class);
		personByName.put(patrick);
		personByName.put(leonardo);
		personByName.put(ana);
		
		SecondaryIndex<Date, String, Person> 	personByBirthday = 
			entityStore.getSecondaryIndex(personByName, Date.class, "birthday");
		EntityIndex<String, Person> 			entityIndex = 
			personByBirthday.subIndex(octoberTwentyTwo.getTime());
		EntityCursor<Person> 					entityCursor = 
			entityIndex.entities();
		
		ArrayList<Person> result = new ArrayList<Person>();
		for(Person person : entityCursor) {
			result.add(person);
		}
		
		assertEquals(2, result.size());
		assertTrue(result.contains(patrick));
		assertTrue(result.contains(leonardo));
		assertFalse(result.contains(ana));
		
		entityCursor.close();
		entityStore.close();
		myDbEnvironment.close();
	}
	
	@Test
	public void keyRangeTest() {
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);
		
		Environment myDbEnvironment = new Environment(environmentDir, envConfig);
		
		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setAllowCreate(true);
		
		EntityStore entityStore = new EntityStore(myDbEnvironment, "PersonStore", storeConfig);
		
		PrimaryIndex<String,Person> personByName = entityStore.getPrimaryIndex(String.class, Person.class);
		
		Person adeilton = new Person("Adeilton");
		personByName.put(adeilton);
		Person bartolomeu = new Person("Bartolomeu");
		personByName.put(bartolomeu);
		Person cristina = new Person("Cristina");
		personByName.put(cristina);
		Person denilson = new Person("Denilson");
		personByName.put(denilson);
		Person erick = new Person("Erick");
		personByName.put(erick);
		Person frederica = new Person("Frederica");
		personByName.put(frederica);
		Person gabriela = new Person("Gabriela");
		personByName.put(gabriela);
		
		EntityCursor<Person> peopleCursor = personByName.entities("D", true, "G", false);

		ArrayList<Person> dAndF_People = new ArrayList<Person>();
		for(Person person : peopleCursor) {
			dAndF_People.add(person);
		}
		
		assertEquals(3, dAndF_People.size());
		
		assertTrue(dAndF_People.contains(denilson));
		assertTrue(dAndF_People.contains(erick));
		assertTrue(dAndF_People.contains(frederica));
		
		assertFalse(dAndF_People.contains(adeilton));
		assertFalse(dAndF_People.contains(bartolomeu));
		assertFalse(dAndF_People.contains(gabriela));
		
		peopleCursor.close();
		entityStore.close();
		myDbEnvironment.close();
	}
	
	@Test
	public void joinCursorsTest() {
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);
		
		Environment dbEnv = new Environment(environmentDir, envConfig);
		
		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setAllowCreate(true);
		
		EntityStore entityStore = new EntityStore(dbEnv, "PersonStore", storeConfig);
		
		Calendar decemberTwentySeven = Calendar.getInstance();
		decemberTwentySeven.set(1964, Calendar.DECEMBER, 27);
		
		Person ze = new Person("Jose");
		ze.setLastName("Silva");
		ze.setBirthday(decemberTwentySeven.getTime());
		
		Person joao = new Person("Joao");
		joao.setLastName("Silva");
		joao.setBirthday(decemberTwentySeven.getTime());
		
		Person florisbaldo = new Person("Florisbaldo");
		florisbaldo.setLastName("Santos");
		florisbaldo.setBirthday(decemberTwentySeven.getTime());
		
		PrimaryIndex<String, Person> personByName = entityStore.getPrimaryIndex(String.class, Person.class);
		SecondaryIndex<Date, String, Person> personByBirthDay = entityStore.getSecondaryIndex(personByName, Date.class, "birthday");
		SecondaryIndex<String, String, Person> personByLastName = entityStore.getSecondaryIndex(personByName, String.class, "lastName");
		
		personByName.put(ze);
		personByName.put(joao);
		personByName.put(florisbaldo);

		EntityJoin<String, Person> entityJoin = new EntityJoin<String, Person>(personByName);
		
		entityJoin.addCondition(personByBirthDay, decemberTwentySeven.getTime());
		entityJoin.addCondition(personByLastName, "Silva");
		
		ForwardCursor<Person> peopleCursor = entityJoin.entities();
		
		ArrayList<Person> peopleList = new ArrayList<Person>();
		for(Person person : peopleCursor) {
			peopleList.add(person);
		}
		
		assertEquals(2, peopleList.size());
		
		assertTrue(peopleList.contains(ze));
		assertTrue(peopleList.contains(joao));
		
		assertFalse(peopleList.contains(florisbaldo));
		
		peopleCursor.close();
		entityStore.close();
		dbEnv.close();
	}
}
