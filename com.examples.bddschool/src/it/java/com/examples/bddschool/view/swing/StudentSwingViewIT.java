package com.examples.bddschool.view.swing;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.examples.bddschool.controller.SchoolController;
import com.examples.bddschool.repository.mongo.StudentMongoRepository;
import com.examples.bddschool.model.Student;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

@RunWith(GUITestRunner.class)
public class StudentSwingViewIT extends AssertJSwingJUnitTestCase {
	
	private static int mongoPort =
			Integer.parseInt(System.getProperty("mongo.port", "27017"));
	
	private StudentSwingView studentSwingView;
	private SchoolController schoolController;
	private FrameFixture window;
	private StudentMongoRepository studentRepository;
	
	public static final String STUDENT_COLLECTION_NAME = "student";
	public static final String SCHOOL_DB_NAME = "school";

	private MongoClient mongoClient;

	@Override
	protected void onSetUp() throws Exception {
		mongoClient = new MongoClient(
				new ServerAddress("localhost", mongoPort));
		studentRepository = new StudentMongoRepository(mongoClient,
				SCHOOL_DB_NAME, STUDENT_COLLECTION_NAME);
		MongoDatabase database = mongoClient.getDatabase(SCHOOL_DB_NAME);
		database.drop();
		GuiActionRunner.execute(() -> {
			studentSwingView = new StudentSwingView();
			schoolController = new SchoolController(studentSwingView, studentRepository);
			studentSwingView.setSchoolController(schoolController);
			return studentSwingView;
		});
		window = new FrameFixture(robot(), studentSwingView);
		window.show();
	}
	
	@Override
	protected void onTearDown() {
		mongoClient.close();
	}
	
	@Test @GUITest
	public void testAllStudents() {
		// use the repository to add students to the database
		Student student1 = new Student("1", "test1");
		Student student2 = new Student("2", "test2");
		studentRepository.save(student1);
		studentRepository.save(student2);
		// use the controller's allStudents
		GuiActionRunner.execute(
			() -> schoolController.allStudents());
		// and verify that the view's list is populated
		assertThat(window.list().contents())
			.containsExactly(student1.toString(), student2.toString());
	}

}
