package com.examples.bddschool.view.swing;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GUITestRunner.class)
public class StudentSwingViewTest extends AssertJSwingJUnitTestCase {


	private StudentSwingView studentSwingView;

	@Override
	protected void onSetUp() throws Exception {
		
		GuiActionRunner.execute(() -> {
			studentSwingView = new StudentSwingView();
			return studentSwingView;
		});	
		FrameFixture window = new FrameFixture(robot(), studentSwingView);
		window.show();	
	}

	@Test
	public void test() {
	}
}
