package com.example.ingredience_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.util.Log;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import org.bson.Document;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private MongoClient mongoClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Link to activity_main.xml layout

        // Set padding for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        // Set up MongoDB connection
        initializeMongoDB();

        // Set up login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (validateInput(email, password)) {
                    checkUserCredentials(email, password);
                }
            }
        });
    }

    /**
     * Initializes MongoDB client connection.
     */
    private void initializeMongoDB() {
        String connectionString = getString(R.string.mongo_connection_string); // Connection string from strings.xml
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
        mongoClient = MongoClients.create(settings);
    }

    /**
     * Validates the email and password inputs.
     *
     * @param email The email input from the user.
     * @param password The password input from the user.
     * @return True if both inputs are valid, otherwise false.
     */
    private boolean validateInput(String email, String password) {
        if (email.isEmpty()) {
            emailEditText.setError("Email is required");
            return false;
        }
        if (password.isEmpty()) {
            passwordEditText.setError("Password is required");
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email");
            return false;
        }
        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters");
            return false;
        }
        return true;
    }

    /**
     * Checks user credentials against MongoDB database.
     *
     * @param email    The user's email.
     * @param password The user's password.
     */
    private void checkUserCredentials(String email, String password) {
        // Connect to the MongoDB database and collection
        MongoDatabase database = mongoClient.getDatabase("Ingredience"); // Replace with your actual database name
        MongoCollection<Document> usersCollection = database.getCollection("users"); // Uses the "users" collection

        // Query for matching email and password
        Document userDocument = usersCollection.find(Filters.and(
                Filters.eq("email", email),
                Filters.eq("password", password)
        )).first();

        if (userDocument != null) {
            // Successful login
            Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
            Log.d("LOGIN", "User found: " + userDocument.toJson());
        } else {
            // Failed login
            Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
