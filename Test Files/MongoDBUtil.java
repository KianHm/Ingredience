import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

// This class is to connect the program to the MongoDB Database Atlas
public class MongoDBUtil {
	
	private static MongoClient mongoClient;
	private static MongoDatabase database;
	
	// Connecting to the MongoDB Atlas Account Cluster
	static {
        String connectionString = "mongodb+srv://kvan27082002:nobita2002@testcuster.4gsqi.mongodb.net/?retryWrites=true&w=majority&appName=TestCuster";
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        // Create a new client and connect to the server
        try {
            mongoClient = MongoClients.create(settings);
            // Enter the Cluster Name
            database = mongoClient.getDatabase("Ingredience");

            // Test the connection with a ping
            database.runCommand(new Document("ping", 1));
            System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }
	
	// This will return the Collection for Item 
	// This Collection will include the UserID - Primary Key 
	// Include item name, categories, expr dates, threshold, quantity
	public static MongoCollection<Document>	getItemCollection(){
		return database.getCollection("Item");
	}
	
	// This will return the Collection for User
	// THis Collection will contains User log in information and their dietary restrictions
	public static MongoCollection<Document> getUserCollection(){
		return database.getCollection("User");
	}
	
	// Shutting down the Database Connection
	public static void shutdown() {
		if(mongoClient != null) {
			mongoClient.close();
			System.out.println("MongoClient connection closed");
		}
	}
}
