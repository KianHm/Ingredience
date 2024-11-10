import React, { useState } from "react";

function Login({ navigate }) {
    const [credentials, setCredentials] = useState({ email: "", password: "" });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setCredentials({ ...credentials, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch("http://localhost:5000/api/users/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(credentials),
            });
            const data = await response.json();
            if (response.ok) {
                alert("Login successful!");
                navigate("MainPage");
            } else {
                alert(`Error: ${data.message}`);
            }
        } catch (error) {
            alert("Error logging in. Please try again.");
        }
    };

    return (
        <div style={{ textAlign: "center", padding: "20px", backgroundColor: "#f8f9fa", height: "100vh" }}>
            <h1>Login</h1>
            <form onSubmit={handleSubmit}>
                <input
                    type="email"
                    name="email"
                    placeholder="Email"
                    value={credentials.email}
                    onChange={handleChange}
                    required
                />
                <br />
                <input
                    type="password"
                    name="password"
                    placeholder="Password"
                    value={credentials.password}
                    onChange={handleChange}
                    required
                />
                <br />
                <button type="submit">Login</button>
            </form>
        </div>
    );
}

export default Login;
