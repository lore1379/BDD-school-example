package com.examples.bddschool.controller;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.examples.bddschool.model.Student;
import com.examples.bddschool.repository.StudentRepository;
import com.examples.bddschool.view.StudentView;

public class SchoolControllerTest {

	@Mock
	private StudentView studentView;
	
	@Mock
	private StudentRepository studentRepository;
	
	@InjectMocks
	private SchoolController schoolController;

	private AutoCloseable closeable;

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);	
	}
	
	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}
	
	@Test
	public void testAllStudends() {
		List<Student> studends = asList(new Student());
		when(studentRepository.findAll()).
			thenReturn(studends);
		schoolController.allStudents();
		verify(studentView).showAllStudents(studends);
	}
	
	@Test
	public void testNewStudentWhenStudentDoesNotAlreadyExist() {
		Student student = new Student ("1", "test");
		when(studentRepository.findById("1")).
			thenReturn(null);
		schoolController.newStudent(student);
		InOrder inOrder = inOrder(studentRepository, studentView);
		inOrder.verify(studentRepository).save(student);
		inOrder.verify(studentView).studentAdded(student);
		
	}
	
	@Test
	public void testNewStudentWhenStudentAlreadyExists() {
		Student studentToAdd = new Student ("1", "test");
		Student existingStudent = new Student ("1", "name");
		when(studentRepository.findById("1")).
			thenReturn(existingStudent);
		schoolController.newStudent(studentToAdd);
		verify(studentView)
			.showError("Already existing student with id 1", existingStudent);
		verifyNoMoreInteractions(ignoreStubs(studentRepository));
	}

}
