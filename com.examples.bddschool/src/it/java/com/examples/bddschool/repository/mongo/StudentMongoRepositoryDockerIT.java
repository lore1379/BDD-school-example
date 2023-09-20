package com.examples.bddschool.repository.mongo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.examples.bddschool.model.Student;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class StudentMongoRepositoryDockerIT {
	
	private static int mongoPort =
			Integer.parseInt(System.getProperty("mongo.port", "27017"));
	
	public static final String SCHOOL_DB_NAME = "school";
	public static final String STUDENT_COLLECTION_NAME = "student";
	
	private StudentMongoRepository studentRepository;
	private MongoClient mongoClient;
	private MongoCollection<Document> studentCollection;
	
	@Before
	public void setup() {
		mongoClient = new MongoClient(
				new ServerAddress("localhost", mongoPort));
		studentRepository = new StudentMongoRepository(mongoClient, 
				SCHOOL_DB_NAME, STUDENT_COLLECTION_NAME);
		MongoDatabase database = mongoClient.getDatabase(SCHOOL_DB_NAME); 
		database.drop();
		studentCollection = database.getCollection(STUDENT_COLLECTION_NAME);
	}

	@After
	public void onTearDown() throws Exception {
		mongoClient.close();
	}
	
	@Test
	public void testFindAllWhenDatabaseIsEmpty() {
		assertThat(studentRepository.findAll()).isEmpty();
	}
	
	@Test
	public void testFindAllWhenDatabaseIsNotEmpty() {
		addTestStudentToDatabase("1", "test1");
		addTestStudentToDatabase("2", "test2");
		assertThat(studentRepository.findAll())
			.containsExactly(
					new Student("1", "test1"),
					new Student("2", "test2"));
	}
	
	@Test
	public void testFindByIdNotFound() {
		assertThat(studentRepository.findById("1"))
			.isNull();
	}
	
	@Test
	public void testFindByIdFound() {
		addTestStudentToDatabase("1", "test1");
		addTestStudentToDatabase("2", "test2");
		assertThat(studentRepository.findById("2"))
			.isEqualTo(new Student("2", "test2"));
	}
	
	@Test
	public void testSave() {
		Student student = new Student("1", "addedStudent");
		studentRepository.save(student);
		assertThat(StreamSupport
				.stream(studentCollection.find().spliterator(), false)
				.map(d -> new Student(""+d.get("id"), ""+d.get("name")))
				.collect(Collectors.toList()))
			.containsExactly(student);
	}
	
	private void addTestStudentToDatabase(String id, String name) {
		studentCollection.insertOne(
				new Document()
					.append("id", id)
					.append("name", name));
	}

}
