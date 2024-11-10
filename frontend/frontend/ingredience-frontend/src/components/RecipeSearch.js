import React, { useState } from "react";
import "./RecipeSearch.css";

function RecipeSearch({ selectedIngredients, navigate }) {
  const [generatedRecipes, setGeneratedRecipes] = useState([]);

  const generateRecipes = () => {
    // Filter recipes based on selected ingredients
    fetch("/data/recipes.csv")
      .then((response) => response.text())
      .then((data) => {
        const recipes = [];
        const lines = data.split("\n");
        lines.forEach((line) => {
          const [recipeName, ingredients, calories, prepTime] = line.split(",");
          if (
            selectedIngredients.every((ingredient) =>
              ingredients.includes(ingredient)
            )
          ) {
            recipes.push({
              recipeName,
              ingredients,
              calories,
              prepTime,
            });
          }
        });
        setGeneratedRecipes(recipes);
      });
  };

  return (
    <div className="recipe-search-container">
      <h1>Generated Recipes</h1>
      <button onClick={generateRecipes}>Generate Recipes</button>
      <ul>
        {generatedRecipes.map((recipe, index) => (
          <li key={index}>
            <h2>{recipe.recipeName}</h2>
            <p>Ingredients: {recipe.ingredients}</p>
            <p>Calories: {recipe.calories}</p>
            <p>Preparation Time: {recipe.prepTime} mins</p>
          </li>
        ))}
      </ul>
      <button onClick={() => navigate("Inventory")}>Back to Inventory</button>
    </div>
  );
}

export default RecipeSearch;
