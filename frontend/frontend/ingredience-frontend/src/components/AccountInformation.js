import React, { useState } from "react";

function AccountInformation({ navigate }) {
    const [info, setInfo] = useState({ name: "", dietaryRestrictions: "" });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setInfo({ ...info, [name]: value });
    };

    const handleSave = async () => {
        try {
            const response = await fetch("http://localhost:5000/api/users/update", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(info),
            });
            if (response.ok) {
                alert("Information updated successfully!");
            } else {
                alert("Failed to update information.");
            }
        } catch (error) {
            alert("Error updating information.");
        }
    };

    return (
        <div style={{ textAlign: "center", padding: "20px", backgroundColor: "#e8f5e9", height: "100vh" }}>
            <h1>Account Information</h1>
            <input
                type="text"
                name="name"
                placeholder="Name"
                value={info.name}
                onChange={handleChange}
            />
            <br />
            <input
                type="text"
                name="dietaryRestrictions"
                placeholder="Dietary Restrictions"
                value={info.dietaryRestrictions}
                onChange={handleChange}
            />
            <br />
            <button onClick={handleSave}>Save</button>
        </div>
    );
}

export default AccountInformation;
