// User.java

/**
 * Represents a User who can bookmark recipes and view recipe history.
 */
public class User {
    private String username;
    private BookmarkRecipe bookmarks;
    private RecipeHistory recipeHistory;

    /**
     * Constructor for User.
     *
     * @param username The username of the user.
     */
    public User(String username) {
        this.username = username;
        this.bookmarks = new BookmarkRecipe();
        this.recipeHistory = new RecipeHistory();
    }

    /**
     * Bookmarks a recipe for the user.
     *
     * @param recipe The recipe to bookmark.
     */
    public void bookmarkRecipe(Recipe recipe) {
        bookmarks.addBookmark(recipe);
    }

    /**
     * Displays the user's bookmarked recipes.
     */
    public void viewBookmarkedRecipes() {
        System.out.println("\nBookmarked Recipes for user: " + username);
        bookmarks.displayBookmarks();
    }

    /**
     * Adds a recipe to the user's viewing history.
     *
     * @param recipe The recipe that was viewed.
     */
    public void addToRecipeHistory(Recipe recipe) {
        recipeHistory.addRecipe(recipe);
    }

    /**
     * Displays the user's recipe viewing history.
     */
    public void viewRecipeHistory() {
        System.out.println("\nRecipe History for user: " + username);
        recipeHistory.displayHistory();
    }
}
