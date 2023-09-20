package com.examples.bddschool.repository.mongo;

import java.util.List;

import com.examples.bddschool.model.Student;
import com.examples.bddschool.repository.StudentRepository;
import com.mongodb.MongoClient;

public class StudentMongoRepository implements StudentRepository{

	public StudentMongoRepository(MongoClient mongoClient, String string, String string2) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Student> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
