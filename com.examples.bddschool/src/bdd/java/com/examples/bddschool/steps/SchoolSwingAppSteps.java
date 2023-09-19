package com.examples.bddschool.steps;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SchoolSwingAppSteps {
	
	@Given("The database contains a few students")
	public void the_database_contains_a_few_students() {
	    MongoClient mongoClient = new MongoClient(
	    		new ServerAddress("localhost", Integer.parseInt(System.getProperty("mongo.port", "27017"))));
	    mongoClient.getDatabase("test-db").drop();
	    mongoClient
			.getDatabase("test-db")
			.getCollection("test-collection")
			.insertOne(
				new Document()
					.append("id", "1")
					.append("name", "first student"));
	    mongoClient
			.getDatabase("test-db")
			.getCollection("test-collection")
			.insertOne(
				new Document()
					.append("id", "2")
					.append("name", "second student"));
	    mongoClient.close();
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

}
