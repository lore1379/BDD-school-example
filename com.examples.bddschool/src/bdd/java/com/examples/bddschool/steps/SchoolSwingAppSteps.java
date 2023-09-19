package com.examples.bddschool.steps;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SchoolSwingAppSteps {

	static final String DB_NAME = "test-db";
	static final String COLLECTION_NAME = "test-collection";

	private static int mongoPort = 
			Integer.parseInt(System.getProperty("mongo.port", "27017"));
	
	static final String STUDENT_FIXTURE_2_NAME = "second student";
	static final String STUDENT_FIXTURE_2_ID = "2";
	static final String STUDENT_FIXTURE_1_NAME = "first student";
	static final String STUDENT_FIXTURE_1_ID = "1";
	
	private MongoClient mongoClient;

	@Before
	public void setup() {		
		mongoClient = new MongoClient(
				new ServerAddress("localhost", mongoPort));
		mongoClient.getDatabase(DB_NAME).drop();
	}
	
	@After
	public void tearDown() {
		mongoClient.close();
	}
	
	@Given("The database contains a few students")
	public void the_database_contains_a_few_students() {
	    addStudentToDatabase(STUDENT_FIXTURE_1_ID, STUDENT_FIXTURE_1_NAME);
	    addStudentToDatabase(STUDENT_FIXTURE_2_ID, STUDENT_FIXTURE_2_NAME);
	}

	@Given("The Student View is shown")
	public void the_Student_View_is_shown() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Given("The user provides student data in the text fields")
	public void the_user_provides_student_data_in_the_text_fields() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("The user clicks the {string} button")
	public void the_user_clicks_the_button(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("The list contains the new student")
	public void the_list_contains_the_new_student() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	private void addStudentToDatabase(String id, String name) {
		mongoClient
		.getDatabase(DB_NAME)
		.getCollection(COLLECTION_NAME)
		.insertOne(
				new Document()
				.append("id", id)
				.append("name", name));
	}
}
