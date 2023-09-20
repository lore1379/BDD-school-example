package com.examples.bddschool.controller;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.examples.bddschool.model.Student;
import com.examples.bddschool.repository.mongo.StudentMongoRepository;
import com.examples.bddschool.view.StudentView;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

public class SchoolControllerIT {
	
	private static int mongoPort =
			Integer.parseInt(System.getProperty("mongo.port", "27017"));
	
	@Mock
	private StudentView studentView;
	
	private StudentMongoRepository studentRepository;
	
	@InjectMocks
	private SchoolController schoolController;
	
	private AutoCloseable closeable;
	
	public static final String STUDENT_COLLECTION_NAME = "student";
	public static final String SCHOOL_DB_NAME = "school";

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		MongoClient mongoClient = new MongoClient(
				new ServerAddress("localhost", mongoPort));
		studentRepository = new StudentMongoRepository(mongoClient,
				SCHOOL_DB_NAME, STUDENT_COLLECTION_NAME);
		MongoDatabase database = mongoClient.getDatabase(SCHOOL_DB_NAME); 
		database.drop();
		schoolController = new SchoolController(studentView, studentRepository);	
	}
	
	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}

	@Test
	public void testAllStudents() {
		Student student = new Student("1", "test");
		studentRepository.save(student);
		schoolController.allStudents();
		verify(studentView)
			.showAllStudents(asList(student));
	}

}
