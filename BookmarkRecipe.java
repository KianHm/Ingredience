// BookmarkRecipe.java

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a user's bookmarked recipes.
 */
public class BookmarkRecipe {
    private Set<Recipe> bookmarks;

    /**
     * Constructor for BookmarkRecipe.
     */
    public BookmarkRecipe() {
        this.bookmarks = new HashSet<>();
    }

    /**
     * Adds a recipe to the bookmarks.
     *
     * @param recipe The recipe to bookmark.
     */
    public void addBookmark(Recipe recipe) {
        if (bookmarks.add(recipe)) {
            System.out.println("Recipe '" + recipe.getName() + "' has been bookmarked.");
        } else {
            System.out.println("Recipe '" + recipe.getName() + "' is already bookmarked.");
        }
    }

    /**
     * Removes a recipe from the bookmarks.
     *
     * @param recipe The recipe to remove from bookmarks.
     */
    public void removeBookmark(Recipe recipe) {
        if (bookmarks.remove(recipe)) {
            System.out.println("Recipe '" + recipe.getName() + "' has been removed from bookmarks.");
        } else {
            System.out.println("Recipe '" + recipe.getName() + "' is not in bookmarks.");
        }
    }

    /**
     * Retrieves the set of bookmarked recipes.
     *
     * @return The set of bookmarked recipes.
     */
    public Set<Recipe> getBookmarks() {
        return bookmarks;
    }

    /**
     * Displays the bookmarked recipes.
     */
    public void displayBookmarks() {
        if (bookmarks.isEmpty()) {
            System.out.println("No bookmarked recipes.");
        } else {
            for (Recipe recipe : bookmarks) {
                System.out.println(recipe);
            }
        }
    }
}
