package com.examples.bddschool.repository.mongo;

import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class StudentMongoRepositoryDockerIT {
	
	private static int mongoPort =
			Integer.parseInt(System.getProperty("mongo.port", "27017"));

	@Test
	public void test() {
		
		MongoClient mongoClient = new MongoClient(
				new ServerAddress("localhost", mongoPort));
		StudentMongoRepository studentRepository = new StudentMongoRepository(
				mongoClient, "school", "student");
		mongoClient.getDatabase("school").drop(); 
		mongoClient.close();
	}

}
