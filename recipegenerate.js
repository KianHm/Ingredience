import React, { useState } from 'react';
import './recipegen.css';

function GenerateRecipe() {
    const [meals, setMeals] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [selectedMeal, setSelectedMeal] = useState(null);

    const getMealList = () => {
        fetch(`https://www.themealdb.com/api/json/v2/9973533/filter.php?i=${searchTerm}`)
            .then(response => response.json())
            .then(data => {
                if (data.meals) {
                    setMeals(data.meals);
                } else {
                    setMeals([]);
                }
            });
    };

    const getMealRecipe = (mealId) => {
        fetch(`https://www.themealdb.com/api/json/v1/1/lookup.php?i=${mealId}`)
            .then(response => response.json())
            .then(data => setSelectedMeal(data.meals[0]));
    };

    const handleSearchInputChange = (event) => {
        setSearchTerm(event.target.value);
    };

    const handleRecipeClose = () => {
        setSelectedMeal(null);
    };

    return (
        <div className="container">
            <div className="meal-wrapper">
                <div className="meal-search">
                    <h2 className="title">Ingredience Meal Generator</h2>

                    <div className="meal-search-box">
                        <input
                            type="text"
                            className="search-control"
                            placeholder="Enter Ingredients"
                            value={searchTerm}
                            onChange={handleSearchInputChange}
                        />
                        <button type="button" className="search-btn btn" onClick={getMealList}>
                            <i className="fas fa-search"></i>
                        </button>
                    </div>
                </div>

                <div className="meal-result">
                    <h2 className="title">Your Search Results:</h2>
                    <div id="meal">
                        {meals.length > 0 ? (
                            meals.map(meal => (
                                <div className="meal-item" key={meal.idMeal} onClick={() => getMealRecipe(meal.idMeal)}>
                                    <div className="meal-img">
                                        <img src={meal.strMealThumb} alt={meal.strMeal} />
                                    </div>
                                    <div className="meal-name">
                                        <h3>{meal.strMeal}</h3>
                                        <button className="recipe-btn">Get Recipe</button>
                                    </div>
                                </div>
                            ))
                        ) : (
                            <p>Sorry, we didn't find any meal!</p>
                        )}
                    </div>
                </div>

                {selectedMeal && (
                    <div className="meal-details showRecipe">
                        <button type="button" className="btn recipe-close-btn" onClick={handleRecipeClose}>
                            <i className="fas fa-times"></i>
                        </button>
                        <div className="meal-details-content">
                            <h2 className="recipe-title">{selectedMeal.strMeal}</h2>
                            <div className="recipe-instruct">
                                <h3>Instructions:</h3>
                                <p>{selectedMeal.strInstructions}</p>
                            </div>
                            <div className="recipe-meal-img">
                                <img src={selectedMeal.strMealThumb} alt={selectedMeal.strMeal} />
                            </div>
                            <div className="recipe-link">
                                <a href={selectedMeal.strYoutube} target="_blank" rel="noopener noreferrer">
                                    Watch Video
                                </a>
                            </div>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}

export default GenerateRecipe;
