package org.example;

import com.mongodb.client.*;
import org.bson.Document;

import java.sql.*;
import java.util.stream.Stream;

import static com.mongodb.client.model.Filters.eq;


public class MongoRepository implements DatabaseRepository {

    String uri = "mongodb://localhost";

    @Override
    public Stream<Person> createPerson(Person person) {

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("person");

            Document doc = new Document();
            doc.append("personid", person.personid);
            doc.append("name", person.name);
            collection.insertOne(doc);

        }

        return Stream.of(person);

    }

    @Override
    public Stream<Person> readPerson(Integer personid) {

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("person");

            System.out.println(collection.find(eq("personid", personid)).first().toJson());

        }

        return null;

    }

    @Override
    public Stream<Person> readAllPersons() {

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("person");

            FindIterable<Document> iterable = collection.find();
            MongoCursor<Document> cursor = iterable.iterator();
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }

        }

        return null;
    }

    @Override
    public void updatePerson(Person person) {



    }

    @Override
    public void deletePerson(Integer personid) {



    }

    @Override
    public Stream<Thing> createThing(Thing thing) {

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("person");

            Document doc = new Document();
            doc.append("thingid", thing.personid);
            doc.append("title", thing.title);
            doc.append("personid", thing.personid);
            collection.insertOne(doc);

        }

        return null;
    }

    @Override
    public Stream<Thing> readAllThingsByPersonid(Integer personid) {

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("person");

            FindIterable<Document> iterable = collection.find(eq("personid", personid));
            MongoCursor<Document> cursor = iterable.iterator();
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }

        }

        return null;
    }

    @Override
    public void updateThing(Thing thing) {



    }

    @Override
    public void deleteThing(Integer thingid) {



    }



}