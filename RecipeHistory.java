// RecipeHistory.java

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user's recipe viewing history.
 */
public class RecipeHistory {
    private List<Recipe> history;

    /**
     * Constructor for RecipeHistory.
     */
    public RecipeHistory() {
        this.history = new ArrayList<>();
    }

    /**
     * Adds a recipe to the viewing history.
     *
     * @param recipe The recipe to add to history.
     */
    public void addRecipe(Recipe recipe) {
        history.add(recipe);
        System.out.println("Recipe '" + recipe.getName() + "' added to history.");
    }

    /**
     * Retrieves the list of recipes in the viewing history.
     *
     * @return The list of recipes in the history.
     */
    public List<Recipe> getHistory() {
        return history;
    }

    /**
     * Displays the viewing history.
     */
    public void displayHistory() {
        if (history.isEmpty()) {
            System.out.println("No recipes in history.");
        } else {
            for (Recipe recipe : history) {
                System.out.println(recipe);
            }
        }
    }
}
