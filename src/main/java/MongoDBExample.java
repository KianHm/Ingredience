import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;


public class MongoDBExample {
    public static void main(String[] args) {
        // Create a MongoClient object
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        // Access a database
        MongoDatabase database = mongoClient.getDatabase("test");

        System.out.println("Connected to database: " + database.getName());

        // Close the connection
        mongoClient.close();
    }
}
