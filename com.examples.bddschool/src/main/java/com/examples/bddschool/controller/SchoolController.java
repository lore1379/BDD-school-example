package com.examples.bddschool.controller;

import com.examples.bddschool.model.Student;
import com.examples.bddschool.repository.StudentRepository;
import com.examples.bddschool.view.StudentView;

public class SchoolController {
	
	private StudentView studentView;
	private StudentRepository studentRepository;

	public SchoolController(StudentView studentView, StudentRepository studentRepository) {
		this.studentView = studentView;
		this.studentRepository = studentRepository;
	}

	public void allStudents() {
		studentView.showAllStudents(studentRepository.findAll());
	}

	public void newStudent(Student student) {
		// TODO Auto-generated method stub
		
	}

}
