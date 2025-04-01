package com.epam;

import com.mongodb.client.*;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.time.Instant;

import static com.mongodb.client.model.Filters.*;


public class MongoClientHandler {

    private static final MongoCollection<Document> collection;
    private static final MongoDatabase mongoDatabase;
    private static final String CATEGORY = "category";

    static {
        String uri = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(uri);
        mongoDatabase = mongoClient.getDatabase("task_manager_db");
        mongoDatabase.createCollection("task_manager");
        collection = mongoDatabase.getCollection("task_manager");
    }

    public static void displayAllTasks() {
        collection.find()
                .iterator().forEachRemaining(document -> System.out.println(document.toJson()));
    }

    public static void displayOverdueTasks() {
        collection.find(lt("deadline", Instant.now())).iterator().forEachRemaining(document -> System.out.println(document.toJson()));
    }

    public static void displayTasksByCategory(String category) {
        collection.find(eq(CATEGORY, category)).iterator().forEachRemaining(document -> System.out.println(document.toJson()));
    }

    public static void addNewTask() {
        collection.insertOne(new Document()
                .append("_id", new ObjectId())
                .append("date_of_creation", "30/03/2025")
                .append("deadline", "15/04/2025")
                .append("name", RandomStringUtils.randomAlphabetic(10))
                .append("description", RandomStringUtils.randomAlphabetic(20))
                .append(CATEGORY, "SAMPLE_CATEGORY"));
    }

    public static void fullTextSearch(String word) {
        collection.find(text(word)).iterator().forEachRemaining(document -> System.out.println(document.toJson()));
    }

    public static void displaySubtasksByCategory(String category) {
        FindIterable<Document> tasksWithSubtasks = collection.find(exists("subtasks")).filter(eq(CATEGORY, category));
        FindIterable<Document> subtasks = tasksWithSubtasks.projection(Projections.include("subtasks"));
        subtasks.projection(Projections.include("name", "description")).iterator().forEachRemaining(document -> System.out.println(document.toJson()));
    }

    public static void fullTextSearchBySubtaskName(String word) {

    }

    public static void deleteTask(String id) {
        collection.deleteOne(eq("_id", new ObjectId(id)));
    }

    public static void editTask(String id) {
        Document updateQuery = new Document().append("_id", new ObjectId(id));
        Bson update = Updates.combine(
                Updates.set("deadline", Instant.now()),
                Updates.set("name", RandomStringUtils.randomAlphabetic(10)),
                Updates.set("description", RandomStringUtils.randomAlphabetic(20)));
        collection.updateOne(updateQuery, update);
    }
}
