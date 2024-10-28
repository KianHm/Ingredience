import java.util.Date;
import java.util.Scanner;

public class Item {
	int quantity;
	String name;
	int calories;
	Date exprDate;
	int threshold;
	boolean expired;
	
	public Item(String name) {
		this.name = name;
		quantity++;
	}
	
	public Item(String name, int calories, int threshold, int quantity) {
		this.name = name;
		this.threshold = threshold;
		this.calories = calories;
		this.quantity += quantity;
	}
	
	public void add(int quantity) {
		this.quantity += quantity;
	}
	
	boolean isLow() {
		return quantity <= threshold;
	}
	
	String itemName() {
		return name;
	}
}
