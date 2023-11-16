package com.patchen.wordy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Activity for adding words to the Firebase Realtime Database.
 */
public class AddWordsActivity extends AppCompatActivity {

    // Error message for displaying error details
    String errorMessage;

    /**
     * Called when the activity is first created.
     * Sets the content view and initializes the Firebase database reference.
     *
     * @param savedInstanceState A saved instance state of the application.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for the activity
        setContentView(R.layout.activity_add_words);

        // Initialize Firebase database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("words");
    }

    /**
     * Handles the click event of the "Submit Word" button.
     * Retrieves the word from the EditText and adds it to the database.
     *
     * @param view The view that triggers this method (Submit Word button).
     */
    public void submitWord(View view) {
        // Find the EditText for entering the word
        EditText wordET = findViewById(R.id.wordET);

        // Get the word from the EditText
        String word = wordET.getText().toString().toUpperCase();

        // Add the word to the database
        addWordToDatabase(word);
    }

    /**
     * Handles the click event of the "Clear Database" button.
     * Clears all entries in the database.
     *
     * @param view The view that triggers this method (Clear Database button).
     */
    public void clearDatabase(View view) {
        // Initialize Firebase database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("words");

        // Remove all entries from the database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myRef.removeValue();
                Toast.makeText(AddWordsActivity.this, "Database cleared", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DatabaseError", "Error clearing the database: " + databaseError.getMessage());
                Toast.makeText(AddWordsActivity.this, "Error clearing the database", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Adds the specified word to the Firebase Realtime Database.
     * Checks if the word meets certain criteria before adding it.
     *
     * @param word The word to be added to the database.
     */
    private void addWordToDatabase(String word) {
        // Check if the word meets certain criteria
        if (wordCheck(word)) {
            // Initialize Firebase database reference
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("words");

            // Check if the word already exists in the database
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean wordExists = false;

                    // Iterate through existing words in the database
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        String databaseWord = childSnapshot.getValue(String.class);

                        // Check if the word already exists
                        if (word.equalsIgnoreCase(databaseWord)) {
                            wordExists = true;
                            errorMessage = "Error: " + word + " exists in the database";
                            Toast.makeText(AddWordsActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                            break;
                        }
                    }

                    // Add the word to the database if it doesn't exist
                    if (!wordExists) {
                        myRef.push().setValue(word);
                        errorMessage = "NEW ENTRY: " + word;
                        Toast.makeText(AddWordsActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("DatabaseError", "Error checking word existence: " + databaseError.getMessage());
                }
            });
        }
    }

    /**
     * Checks if the specified word meets certain criteria.
     * Displays an error message if the word is invalid.
     *
     * @param word The word to be checked.
     * @return True if the word is valid, false otherwise.
     */
    private Boolean wordCheck(String word) {
        // Initialize Firebase database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("words");

        // Check if the word length is not 5 characters
        if (word.length() != 5) {
            errorMessage = "Error: " + word + " is not 5 characters.";
            Toast.makeText(AddWordsActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            return false;
        }

        // Check if the word contains special characters
        else if (!word.matches("^[a-zA-Z]*$")) {
            errorMessage = "Error: " + word + " contains special characters";
            Toast.makeText(AddWordsActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            return false;
        }

        // The word is valid
        return true;
    }
}
