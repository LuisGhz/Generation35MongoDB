package com.exercise35mongodb.db;

import com.exercise35mongodb.model.Dog;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import java.util.List;
import java.util.ArrayList;


public class DatabaseManager {
	
	public void addDog(Dog dog)
	{
		MongoClient client = MongoClients.create();
		MongoDatabase myDB = client.getDatabase("venerinaria");
		MongoCollection<Document> dogsCollection = myDB.getCollection("dogs");
		
		Document dogDoc = new Document("name", dog.getName())
				.append("age", dog.getAge())
				.append("wieght", dog.getWeight())
				.append("color", dog.getColor())
				.append("breed", dog.getBreed())
				.append("isLive", dog.isLive());
		
		dogsCollection.insertOne(dogDoc);
		
		client.close();
	}
	
	public List<Dog> getDogs()
	{
		List<Dog> dogsList = new ArrayList<Dog>();
		
		MongoClient client = MongoClients.create();
		MongoDatabase myDB = client.getDatabase("venerinaria");
		MongoCollection<Document> dogsCollection = myDB.getCollection("dogs");
		
		MongoCursor<Document> iterator = dogsCollection.find().iterator();
		
		while(iterator.hasNext())
		{
			Document doc = iterator.next();
			Dog dog = new Dog();
			dog.setName(doc.getString("name"));
			dog.setAge(doc.getInteger("age"));
			dog.setWeight(doc.getDouble("weight"));
			dog.setColor(doc.getString("color"));
			dog.setBreed(doc.getString("breed"));
			dog.setLive(doc.getBoolean("isLive"));
			
			dogsList.add(dog);
		}
		
		client.close();
		
		return dogsList;
	}
}
