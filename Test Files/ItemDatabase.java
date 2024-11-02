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
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Updates.set;
import static com.mongodb.client.model.Aggregates.match;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ItemDatabase {

	private static String userid;
	private static MongoCollection collection;
	private static Date thresholdDate;
	private static Date expirationDate;
	private String item;

	// Connect to Database
	// Each Collection in the Item Database will have
	// 1: The user-ID : To seperate differnt users Database because we don't want to 
	// create differnt clusters for differnt users. So it's like Primary Key 
	static {
		try {
			collection = MongoDBUtil.getItemCollection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 5); // Set to 5 days from now
        thresholdDate = calendar.getTime();
	}

	// Each ItemDatabase will have different user-ID so that whenever a user is logged in, 
	// We can pull the exact data for that user
	public ItemDatabase(String userid) {
		this.userid = userid;
	}

	// Add new Item (Did not exists in the current user database)
	// We will input item name
	// Item category
	// Item Quantity
	// The threshold for generating shopping list
	// The expiration date
	public void addNewItem(String item, String category, int quantity, int threshold, int expiration) {
		Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, expiration);
        expirationDate = calendar.getTime();
		Document document = new Document("userid", userid);
		document.append("item", item);
		document.append("category", category);
		document.append("quantity", quantity);
		document.append("threshold", threshold);
		document.append("expiration", expirationDate);
		collection.insertOne(document);
	}

	// Add item that is currently existed in the list
	// Only Item name and quantity and expiration date
	public void addItem(String item, int quantity, int expiration) {
		Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, expiration);
        expirationDate = calendar.getTime();
		Document found = (Document) collection.find(new Document("userid", userid).append("item", item)).first();
		int currQuantity = found.getInteger("quantity");
		int newQuantity = currQuantity + quantity;
		collection.updateOne(and(eq("userid", userid), eq("item", item)), set("quantity", newQuantity));
		collection.updateOne(and(eq("userid", userid), eq("item", item)), set("expiration", expirationDate));
	}

	// Check if the Item exists for the current User
	public boolean itemExist(String item) {
		Document found = (Document) collection.find(new Document("userid", userid).append("item", item)).first();
		if (found != null) {
			return true;
		} else {
			return false;
		}
	}

	// Remove the item for the current user with the quantity wanted to be removed
	public void removeItem(String item, int quantity) {
		Document found = (Document) collection.find(new Document("userid", userid).append("item", item)).first();
		int currQuantity = found.getInteger("quantity");
		int newQuantity;
		if (currQuantity == 0 || currQuantity <= quantity) {
			newQuantity = 0;
		} else {
			newQuantity = currQuantity - quantity;
		}
		collection.updateOne(and(eq("userid", userid), eq("item", item)), set("quantity", newQuantity));
	}
	
	// Remove the whole item from inventory/database of the current user
	public void removeFromInventory(String item) {
		Document found = (Document) collection.find(new Document("userid", userid).append("item", item)).first();
		if (found != null) {
			collection.deleteOne(found);
		}
	}
	
	// Get the quantity of a specfic item of the current user
	public int getQuantity(String item) {
		Document found = (Document) collection.find(new Document("userid", userid).append("item", item)).first();
		if(found != null) {
			return found.getInteger("quantity");
		} else {
			return -1;
		}		
	}
	
	// Check if the specific item is expired or not (5 days threshold - 5 days before the expiration date
	// it will appear as expired)
	public boolean expired(String item) {
         Document expiringItem = (Document) collection.find(and(eq("item", item),lt("expiration", thresholdDate))).first();
         if(expiringItem != null) {
        	 return true;
         } else {
        	 return false;
         }
	}
	
	// Return the expration date of an Item
	public Date getExprDate(String item) {
		Document found = (Document) collection.find(new Document("userid", userid).append("item", item)).first();
        if (found != null) {
            return found.getDate("expiration");
        } else {
            return null;
        }
	}
	
	// Return a List of String for shopping List: This shopping List either contain items that expired or 
	// about to be expired as well as item that is below threshold
	public List<String> shoppingList(){
		List<Document> expiringLowThresholdItems = new ArrayList<>();
	    List<Document> items = (List<Document>) collection.find(new Document("userid", userid)).into(new ArrayList<>());

	    for (Document item : items) {
	        Date expirationDate = item.getDate("expiration");
	        int quantity = item.getInteger("quantity");
	        int threshold = item.getInteger("threshold");

	        // Check if the item is expiring and below the threshold
	        if (expirationDate != null && expirationDate.before(thresholdDate) || quantity < threshold) {
	            expiringLowThresholdItems.add(item);
	        }
	    }

        List<String> shoppingList = new ArrayList<>();
        for (Document currItem : expiringLowThresholdItems) {
            shoppingList.add(currItem.getString("item"));
        }
        return shoppingList;
	}
	
	
}
