package com.examples.bddschool.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static java.util.Arrays.asList;

import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.examples.bddschool.model.Student;
import com.examples.bddschool.repository.StudentRepository;
import com.examples.bddschool.view.StudentView;

public class SchoolControllerTest {

	@Mock
	private StudentRepository studentRepository;

	@Mock
	private StudentView studentView;
	
	@InjectMocks
	private SchoolController schoolController;


	@Test
	public void testAllStudends() {
		List<Student> studends = asList(new Student());
		when(studentRepository.findAll()).
			thenReturn(studends);
		schoolController.allStudents();
		verify(studentView).showAllStudents(studends);
	}

}
