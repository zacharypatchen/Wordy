package com.patchen.wordy;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

/**
 * The main activity for the Wordy app.
 */
public class MainActivity extends AppCompatActivity {

    // Array of words to be used in the game
    String[] words = {"hello", "apple", "chill", "puppy", "dream", "guard", "adult", "wound", "force", "flood"};

    // Random object for word selection
    Random r;

    /**
     * Called when the activity is first created.
     * Initializes Firebase and sets the layout.
     *
     * @param savedInstanceState A saved instance state of the application.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Set the layout for the activity
        setContentView(R.layout.activity_main);
    }

    /**
     * Opens the PlayActivity when the play button is clicked.
     * Retrieves a random word from the Firebase database and starts the game.
     *
     * @param view The view that triggers this method (Play button).
     */
    public void openPlayActivity(View view) {
        // Get reference to the Firebase database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("words");

        // Listen for a single value event from the database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Check if the database has data
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    // Create a list to store words from the database
                    ArrayList<String> wordList = new ArrayList<>();

                    // Iterate through the database entries
                    for (DataSnapshot s : snapshot.getChildren()) {
                        // Get a word from the database and add it to the list
                        String randomWord = s.getValue(String.class);
                        wordList.add(randomWord);
                    }

                    // Create a random object
                    Random r = new Random();

                    // Get a random word from the list
                    String randomWord = wordList.get(r.nextInt(wordList.size()));

                    // Create an intent to start the PlayActivity and pass the random word
                    Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                    intent.putExtra("WORD", randomWord);
                    startActivity(intent);
                } else {
                    // Show an error message if the database is empty
                    Toast.makeText(MainActivity.this, "Error: Database is empty", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Log and display an error message if there is an issue reading the database
                Log.e("DatabaseError", "Error reading database: " + error.getMessage());
                Toast.makeText(MainActivity.this, "Error reading database", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Opens the AddWordsActivity when the add words button is clicked.
     *
     * @param view The view that triggers this method (Add Words button).
     */
    public void openDBActivity(View view) {
        Intent intent = new Intent(this, AddWordsActivity.class);
        startActivity(intent);
    }
}
