import java.util.ArrayList;
import java.util.Scanner;

public class ShoppingList {
	
	public static ArrayList<Item> Inventory = new ArrayList<Item>();
	
	public static void main(String[] args){
		System.out.print("View Shopping List (V) OR Input/Update Inventory (I): ");
		Scanner sc = new Scanner(System.in);
		String input = sc.next();
		boolean exit = false;
		while(!exit) {
		if(input.toUpperCase().charAt(0) == 'V') {
			viewList();
			System.out.print("Do you want to Input/Update Inventory? (Y/N): ");
			String decision = sc.next().toUpperCase();
			if(decision.charAt(0) == 'Y') {
				input = "I";
				continue;
			} else {
				exit = true;
				continue;
			}
		} else if (input.toUpperCase().charAt(0) == 'I') {
			inputItem();
			System.out.print("Do you want to View Shopping List? (Y/N): ");
			String decision = sc.next().toUpperCase();
			if(decision.charAt(0) == 'Y') {
				input = "V";
				continue;
			} else {
				System.out.print("Do you want to continue Input/Update Inventory? (Y/N): ");
				decision = sc.next().toUpperCase();
				if(decision.charAt(0) == 'Y') {
					input = "I";
					continue;
				} else {
					exit = true;
					continue;
				}
			}
		  }
		}
		System.out.println("Program exit!");
	}
	
	public static void inputItem() {
		boolean exists = false;
		Scanner sc = new Scanner(System.in);
		System.out.print("Please enter Item name: ");
		String itemName = sc.nextLine().toUpperCase();
		System.out.print("Please enter Item quantity: ");
		int quantities = sc.nextInt();
		int calories;
		int threshold;
		for(Item item : Inventory) {
			if(itemName == item.itemName()) {
				item.add(quantities);
				exists = true;
				System.out.println("Item Succesfully Added!\n");
				break;
			}
		}
		if(!exists) {
			System.out.print("Please enter Item Calories: ");
			calories = sc.nextInt();
			System.out.print("Please enter Item Threshold: ");
			threshold = sc.nextInt();
			Inventory.add(new Item(itemName, calories, threshold, quantities));
			System.out.println("NEW Item Successfully Added!\n");
			
		}
	}
	
	public static void viewList() {
		for(Item item : Inventory) {
			if(item.isLow()) {
				System.out.println(item.itemName());
			}
		}
		System.out.println();
	}
}
