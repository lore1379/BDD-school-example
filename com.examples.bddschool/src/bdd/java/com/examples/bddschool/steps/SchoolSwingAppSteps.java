package com.examples.bddschool.steps;

import static org.assertj.swing.launcher.ApplicationLauncher.application;

import javax.swing.JFrame;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
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
	private FrameFixture window;

	@Before
	public void setup() {		
		mongoClient = new MongoClient(
				new ServerAddress("localhost", mongoPort));
		mongoClient.getDatabase(DB_NAME).drop();
	}
	
	@After
	public void tearDown() {
		mongoClient.close();
		if (window != null)
			window.cleanUp();
	}
	
	@Given("The database contains a few students")
	public void the_database_contains_a_few_students() {
	    addStudentToDatabase(STUDENT_FIXTURE_1_ID, STUDENT_FIXTURE_1_NAME);
	    addStudentToDatabase(STUDENT_FIXTURE_2_ID, STUDENT_FIXTURE_2_NAME);
	}

	@Given("The Student View is shown")
	public void the_Student_View_is_shown() {
		application("com.examples.bddschool.app.swing.SchoolSwingApp")
			.withArgs(
				"--mongo-host=" + "localhost",
				"--mongo-port=" + Integer.toString(mongoPort),
				"--db-name=" + DB_NAME,
				"--db-collection=" + COLLECTION_NAME
			)
			.start();
		window = WindowFinder.findFrame(new GenericTypeMatcher<JFrame>(JFrame.class) {
			@Override
			protected boolean isMatching(JFrame frame) {
				return "Student View".equals(frame.getTitle()) && frame.isShowing();
			}
		}).using(BasicRobot.robotWithCurrentAwtHierarchy());
	}

	@Given("The user provides student data in the text fields")
	public void the_user_provides_student_data_in_the_text_fields() {
		window.textBox("idTextBox").enterText("10");
	    window.textBox("nameTextBox").enterText("new student");
	}

	@When("The user clicks the {string} button")
	public void the_user_clicks_the_button(String buttonText) {
		window.button(JButtonMatcher.withText(buttonText)).click();
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
