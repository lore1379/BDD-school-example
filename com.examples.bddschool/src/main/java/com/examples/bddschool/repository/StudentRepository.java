package com.examples.bddschool.repository;

import java.util.List;

import com.examples.bddschool.model.Student;

public interface StudentRepository {

	public List<Student> findAll();

}
