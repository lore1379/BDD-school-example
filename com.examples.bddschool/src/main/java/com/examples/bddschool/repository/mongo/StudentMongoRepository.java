package com.examples.bddschool.repository.mongo;

import java.util.Collections;
import java.util.List;

import com.examples.bddschool.model.Student;
import com.examples.bddschool.repository.StudentRepository;
import com.mongodb.MongoClient;

public class StudentMongoRepository implements StudentRepository{

	public StudentMongoRepository(MongoClient mongoClient, 
			String databaseName, String collectionName) {

	}

	@Override
	public List<Student> findAll() {
		return Collections.emptyList();
	}

	@Override
	public Student findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Student student) {
		// TODO Auto-generated method stub
		
	}

}
