// Recipe.java

/**
 * Represents a Recipe with an id, name, and description.
 */
public class Recipe {
    private int id;
    private String name;
    private String description;

    /**
     * Constructor for Recipe.
     *
     * @param id          The unique identifier for the recipe.
     * @param name        The name of the recipe.
     * @param description A brief description of the recipe.
     */
    public Recipe(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getters for Recipe attributes

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // toString method for easy display of recipe information

    @Override
    public String toString() {
        return "Recipe ID: " + id + ", Name: " + name + ", Description: " + description;
    }

    // Override equals and hashCode methods for correct behavior in sets

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Recipe)) return false;
        Recipe other = (Recipe) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
