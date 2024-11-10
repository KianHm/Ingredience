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

import java.util.List;
import java.util.Scanner;

public class Main {
	
	private static Scanner sc = new Scanner(System.in);
	private static UserDatabase currUser;
	private static List<String> shoppingList;
	private static ItemDatabase currItems;

	public static void main(String[] args) {
		// Add in method to test;
	}

	public static void login() {
		System.out.print("Log In or Create Account (L or C): ");
		char answer = sc.next().toLowerCase().charAt(0);
		boolean loggedIn = false;
		while (!loggedIn) {
			if (answer == 'l') {
				System.out.print("Please enter UserID: ");
				String userId = sc.next();
				System.out.print("Please enter Password: ");
				String password = sc.next();
				currUser = new UserDatabase(userId, password);
				loggedIn = currUser.validate();
				if (loggedIn) {
					break;
				} else {
					System.out.println("Incorrect UserID or Password!");
					System.out.print("Enter Again or Create New (L or C): ");
					answer = sc.next().toLowerCase().charAt(0);
					continue;
				}
			} else {
				currUser = new UserDatabase();
				break;
			}
		}
		System.out.println("Succesfully Logged-In!");
	}

	public static void updateDietRestrict(String update) {
		currUser.updateDietRestrict(update);
	}

	public static void getItemDatabase() {
		currItems = new ItemDatabase(currUser.getUserId());
	}

	public static List<String> getShoppingList() {
		shoppingList = currItems.shoppingList();
		return shoppingList;
	}

	public static void addItem() {
		getItemDatabase();
		System.out.print("Please Enter Item: ");
		String itemName = sc.nextLine();
		if(currItems.itemExist(itemName)) {
			System.out.print("Please Enter Quantity: ");
			int quantity = sc.nextInt();
			System.out.print("Please Enter Dates Until Expiration: ");
			int expDate = sc.nextInt();
			currItems.addItem(itemName, quantity, expDate);
		} else {
			System.out.print("Please Enter Item Category: ");
			String category = sc.next();
			System.out.print("Please Enter Quantity: ");
			int quantity = sc.nextInt();
			System.out.print("Please Enter Threshold: ");
			int threshold = sc.nextInt();
			System.out.print("Please Enter Dates Until Expiration: ");
			int expDate = sc.nextInt();
			currItems.addNewItem(itemName, category, quantity, threshold, expDate);
		}
		System.out.println("Successfully Add Item");
	}
	
	public static void removeFromShoppingList() {
		System.out.print("Please Enter Item To Remove: ");
		boolean itemInList = false;
		String itemName = sc.nextLine();
		int index = 0;
		for(String item : shoppingList) {
			if(itemName == item) {
				shoppingList.remove(index);
				itemInList = true;
			} else {
				index++;
			}
		}
		if(itemInList) {
			System.out.println("Item Successfully Removed From Shopping List!");
		} else {
			System.out.println("No Such Item In Shopping List");
		}
	}
	
	public static void addToShoppingList() {
		System.out.print("Please Enter Item To Remove: ");
		String itemName = sc.nextLine();
		if(currItems.itemExist(itemName)) {
			System.out.println("Item already existed in Shopping List");
		} else {
			shoppingList.add(itemName);
			System.out.println("Successfully Added Item To Shopping List!");
		}
	}
}
