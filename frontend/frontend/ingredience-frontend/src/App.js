import React, { useState } from "react";
import SignUp from "./components/SignUp";
import Login from "./components/Login";
import MainPage from "./components/MainPage";
import AccountInformation from "./components/AccountInformation";

function App() {
    const [currentPage, setCurrentPage] = useState("SignUp");

    const renderPage = () => {
        switch (currentPage) {
            case "SignUp":
                return <SignUp navigate={setCurrentPage} />;
            case "Login":
                return <Login navigate={setCurrentPage} />;
            case "MainPage":
                return <MainPage navigate={setCurrentPage} />;
            case "AccountInformation":
                return <AccountInformation navigate={setCurrentPage} />;
            default:
                return <SignUp navigate={setCurrentPage} />;
        }
    };

    return <div>{renderPage()}</div>;
}

export default App;
