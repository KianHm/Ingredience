import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class randomRecipeFetcher {
    
    private static final String apiKey = "336a693afe864f4986be0494ec07ab86";  
    private static final String randomRecipeUrl = "https://api.spoonacular.com/recipes/random?apiKey=" + apiKey;

    public static void main(String[] args) {
        try {
            String recipe = getRandomRecipe();
            System.out.println("Recipe: " + recipe);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    //function to get recipe from api
    public static String getRandomRecipe() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(randomRecipeUrl))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //parse through json received
        JSONObject jsonResponse = new JSONObject(response.body());
        JSONObject recipe = jsonResponse.getJSONArray("recipes").getJSONObject(0);
        
        //recipe info gets pulled
        String title = recipe.getString("title");
        String instructions = recipe.getString("instructions");
        String ingredients = recipe.getJSONArray("extendedIngredients").toString();

        return "Title: " + title + "\nIngredients: " + ingredients + "\nInstructions: " + instructions;
    }
}
