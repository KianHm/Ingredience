public class IngredientService {
    private HashMap<String, Ingredient> ingredientInventory = new HashMap<>();
    
    public void addIngredient(String name, Ingredient ingredient) {
        // 1. Validate name and ingredient not null
        // 2. Check if ingredient already exists
        // 3. Add to inventory
    }
    
    public Ingredient getIngredient(String name) {
        // 1. Return ingredient from inventory
        // 2. Handle not found case
    }
    
    public void updateIngredient(String name, Ingredient ingredient) {
        // 1. Check if exists in inventory
        // 2. Update ingredient details
        // 3. Handle not found case
    }
    
    public void removeExpiredIngredients() {
        // 1. Check each ingredient's expiration
        // 2. Remove if expired
    }
}

class Ingredient {
    private String name;
    private String expirationDate;
    private NutritionalInfo nutrition;
    
    public Ingredient(String name, String expirationDate, NutritionalInfo nutrition) {
        // 1. Validate parameters not null
        // 2. Set instance variables
    }
    
    // Basic getters and setters
}

class NutritionalInfo {
    private double calories;
    private double protein;
    private double carbs;
    private double fat;
    
    public NutritionalInfo(double calories, double protein, double carbs, double fat) {
        // 1. Validate values are positive
        // 2. Set nutritional values
    }
    
    // Basic getters
}
