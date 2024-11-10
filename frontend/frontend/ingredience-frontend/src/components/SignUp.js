import React, { useState } from "react";

function SignUp({ navigate }) {
    const [formData, setFormData] = useState({ name: "", email: "", password: "" });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch("http://localhost:5000/api/users/signup", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(formData),
            });
            const data = await response.json();
            if (response.ok) {
                alert("User created successfully!");
                navigate("MainPage");
            } else {
                alert(`Error: ${data.message}`);
            }
        } catch (error) {
            alert("Error signing up. Please try again.");
        }
    };

    return (
        <div style={{ textAlign: "center", padding: "20px", backgroundColor: "#f0f8ff", height: "100vh" }}>
            <h1>Sign Up</h1>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    name="name"
                    placeholder="Name"
                    value={formData.name}
                    onChange={handleChange}
                    required
                />
                <br />
                <input
                    type="email"
                    name="email"
                    placeholder="Email"
                    value={formData.email}
                    onChange={handleChange}
                    required
                />
                <br />
                <input
                    type="password"
                    name="password"
                    placeholder="Password"
                    value={formData.password}
                    onChange={handleChange}
                    required
                />
                <br />
                <button type="submit">Sign Up</button>
            </form>
            <button onClick={() => navigate("Login")}>Login</button>
        </div>
    );
}

export default SignUp;
