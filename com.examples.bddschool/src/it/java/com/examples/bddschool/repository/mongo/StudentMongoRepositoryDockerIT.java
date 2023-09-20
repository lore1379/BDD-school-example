package com.examples.bddschool.repository.mongo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class StudentMongoRepositoryDockerIT {
	
	private static int mongoPort =
			Integer.parseInt(System.getProperty("mongo.port", "27017"));
	
	public static final String SCHOOL_DB_NAME = "school";
	public static final String STUDENT_COLLECTION_NAME = "student";
	
	private StudentMongoRepository studentRepository;

	private MongoClient mongoClient;
	
	@Before
	public void setup() {
		mongoClient = new MongoClient(
				new ServerAddress("localhost", mongoPort));
		studentRepository = new StudentMongoRepository(mongoClient, 
				SCHOOL_DB_NAME, STUDENT_COLLECTION_NAME);
		mongoClient.getDatabase(SCHOOL_DB_NAME).drop(); 		
	}

	@After
	public void onTearDown() throws Exception {
		mongoClient.close();
	}
	
	@Test
	public void testFindAllWhenDatabaseIsEmpty() {
		assertThat(studentRepository.findAll()).isEmpty();
	}

}
