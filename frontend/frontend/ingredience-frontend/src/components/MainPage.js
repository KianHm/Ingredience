import React, { useState } from "react";
import Inventory from "./Inventory";
import RecipeSearch from "./RecipeSearch";

function MainPage() {
  const [currentPage, setCurrentPage] = useState("Inventory");
  const [selectedIngredients, setSelectedIngredients] = useState([]);

  const navigate = (page, ingredients = []) => {
    setSelectedIngredients(ingredients);
    setCurrentPage(page);
  };

  return (
    <div>
      {currentPage === "Inventory" && (
        <Inventory navigate={navigate} />
      )}
      {currentPage === "RecipeSearch" && (
        <RecipeSearch
          selectedIngredients={selectedIngredients}
          navigate={navigate}
        />
      )}
    </div>
  );
}

export default MainPage;
