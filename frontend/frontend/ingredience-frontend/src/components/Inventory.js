import React, { useState, useEffect } from "react";
import Papa from "papaparse"; // For CSV parsing
import "./Inventory.css";

function Inventory({ navigate }) {
  const [searchTerm, setSearchTerm] = useState("");
  const [ingredients, setIngredients] = useState([]);
  const [filteredIngredients, setFilteredIngredients] = useState([]);
  const [selectedIngredients, setSelectedIngredients] = useState([]);

  useEffect(() => {
    // Load and parse CSV file
    fetch("/data/Food Ingredients and Recipe Dataset with Image Name Mapping.csv")
      .then((response) => response.text())
      .then((data) => {
        Papa.parse(data, {
          header: true,
          complete: (result) => setIngredients(result.data),
        });
      });
  }, []);

  useEffect(() => {
    // Filter ingredients based on search term
    setFilteredIngredients(
      ingredients.filter((item) =>
        item.ingredients?.toLowerCase().includes(searchTerm.toLowerCase())
      )
    );
  }, [searchTerm, ingredients]);

  const addToInventory = (ingredient) => {
    setSelectedIngredients((prev) => [...prev, ingredient]);
  };

  const handleSearch = (e) => {
    setSearchTerm(e.target.value);
  };

  return (
    <div className="inventory-container">
      <h1>Manage Your Inventory</h1>
      <input
        type="text"
        placeholder="Search ingredients..."
        value={searchTerm}
        onChange={handleSearch}
        className="search-bar"
      />
      <ul className="ingredient-list">
        {filteredIngredients.map((item, index) => (
          <li key={index}>
            <span>{item.ingredients}</span>
            <button onClick={() => addToInventory(item.ingredients)}>
              Add
            </button>
          </li>
        ))}
      </ul>
      <h2>Selected Ingredients:</h2>
      <ul>
        {selectedIngredients.map((ingredient, index) => (
          <li key={index}>{ingredient}</li>
        ))}
      </ul>
      <button onClick={() => navigate("RecipeSearch")}>
        Generate Recipes
      </button>
    </div>
  );
}

export default Inventory;
