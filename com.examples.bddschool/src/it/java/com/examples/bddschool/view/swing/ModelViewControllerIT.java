package com.examples.bddschool.view.swing;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.examples.bddschool.controller.SchoolController;
import com.examples.bddschool.model.Student;
import com.examples.bddschool.repository.mongo.StudentMongoRepository;
import com.examples.bddschool.view.swing.StudentSwingView;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

@RunWith(GUITestRunner.class)
public class ModelViewControllerIT extends AssertJSwingJUnitTestCase {
	
	private static int mongoPort =
			Integer.parseInt(System.getProperty("mongo.port", "27017"));
	
	private StudentSwingView studentSwingView;
	private SchoolController schoolController;
	private FrameFixture window;
	private StudentMongoRepository studentRepository;
	
	private MongoClient mongoClient;
	
	public static final String STUDENT_COLLECTION_NAME = "student";
	public static final String SCHOOL_DB_NAME = "school";
	
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

	@Test
	public void testAddStudent() {
		window.textBox("idTextBox").enterText("1");
		window.textBox("nameTextBox").enterText("test");
		window.button(JButtonMatcher.withText("Add")).click();
		assertThat(studentRepository.findById("1"))
			.isEqualTo(new Student("1", "test"));
	}

}
