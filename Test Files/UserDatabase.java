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
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import java.util.Scanner;

import static com.mongodb.client.model.Filters.and;

// This class will have methods to pull data for users from Users Database Collection
// I haven't completed this class yet bc I don't know how Vincent want to implement the login page logic
public class UserDatabase {
	
	private static MongoCollection collection;
	private static String userid;
	private static String password;
	private static String dietaryRestrict;
	
	// Getting the User database collection
	static {
		try {
			collection = MongoDBUtil.getUserCollection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public UserDatabase() {
		System.out.print("Please Enter UserID: ");
		Scanner sc = new Scanner(System.in);
		String newUserId = sc.next();
		System.out.print("Please Enter Password: ");
		String newPassword = sc.next();
		System.out.println("Please Enter Dietary Restrictions: ");
		String dietRestrict = sc.nextLine();
		createNewAccount(newUserId, newPassword, dietRestrict);
		sc.close();
		
	}
	// Inputing Userid and password
	public UserDatabase(String userid, String password) {
		this.userid = userid;
		this.password = password;
	}
	
	// Return true if successfully log-in | else return false
	public boolean validate() {
		Document found = (Document) collection.find(new Document("userid", userid).append("password", password)).first();
		if(found != null) {
			return true;
		} else {
			return false;
		}
	}
	
	// User Exists - Wrong Password
	public boolean userExist() {
		Document found = (Document) collection.find(new Document("userid", userid).append("password", password)).first();
		if(found != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void createNewAccount(String userid, String password, String dietaryRestrict) {
		this.userid = userid;
		this.password = password;
		this.dietaryRestrict = dietaryRestrict;
		Document document = new Document("userid", this.userid);
		document.append("password", this.password);
		document.append("diet restriction", this.dietaryRestrict);
		collection.insertOne(document);
		System.out.println("Successfully Registered");
		}
	
	public String getUserId() {
		return this.userid;
	}
	
	public void resetPassword(String newPassword) {
		collection.updateOne(eq("userid", this.userid), set("password", newPassword));
	}
	
	public String getDietRestrict() {
		return this.dietaryRestrict;
	}
	
	public void updateDietRestrict(String newRestrict) {
		collection.updateOne(and(eq("userid", this.userid), eq("password", this.password)), set("diet restriction", newRestrict));
	}
	
}
