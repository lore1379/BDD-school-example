package com.examples.bddschool.repository.mongo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.examples.bddschool.model.Student;
import com.examples.bddschool.repository.StudentRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class StudentMongoRepository implements StudentRepository{
	
	private MongoCollection<Document> studentCollection;

	public StudentMongoRepository(MongoClient mongoClient, 
			String databaseName, String collectionName) {
		studentCollection = mongoClient
				.getDatabase(databaseName)
				.getCollection(collectionName);
	}

	@Override
	public List<Student> findAll() {
		return StreamSupport
				.stream(studentCollection.find().spliterator(), false)
				.map(this::fromDocumentToStudent)
				.collect(Collectors.toList());
	}

	@Override
	public Student findById(String id) {
		Document d = studentCollection.find(Filters.eq("id", id)).first();
		if (d != null) {
			return fromDocumentToStudent(d);
		}
		return null;
	}

	@Override
	public void save(Student student) {
		// TODO Auto-generated method stub
		
	}
	
	private Student fromDocumentToStudent(Document d) {
		return new Student(""+d.get("id"), ""+d.get("name"));
	}

}
