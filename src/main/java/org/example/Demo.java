package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.sql.*;

import static com.mongodb.client.model.Filters.eq;

public class Demo {
    public static void main(String[] args) {
        //testmysql();
        testmongo();
    }

    private static void testmysql() {
        String uri ="jdbc:mysql://localhost/mydatabase?user=myuser&password=mypass";
        try(Connection conn = DriverManager.getConnection(uri)){
            //INSERT
            PreparedStatement statement = conn.prepareStatement("INSERT INTO thing(title) VALUES(?)");
            statement.setString(1, "Batman");
            statement.executeUpdate();

            //QUERY
            ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM thing");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void testmongo(){
        String uri = "mongodb://localhost";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("thing");

            // INSERT
            Document doc = new Document();
            doc.append("title", "Batman");
            collection.insertOne(doc);

            //QUERY
            System.out.println(collection.find(eq("title", "Batman")).first().toJson());
        }
    }
}
