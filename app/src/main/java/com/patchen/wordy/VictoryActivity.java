package com.patchen.wordy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity displayed when the player successfully guesses the word.
 */
public class VictoryActivity extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     * Sets the content view, retrieves the game word from the intent,
     * and displays a victory message with the guessed word.
     *
     * @param savedInstanceState A saved instance state of the application.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for the activity
        setContentView(R.layout.activity_victory);

        // Retrieve the game word from the intent
        String gameWord = getIntent().getStringExtra("GAME_WORD");

        // Find the TextView for displaying the game word
        TextView gameWordTextView = findViewById(R.id.gameWordTextView);

        // Set the text to display the victory message and the guessed word
        gameWordTextView.setText("Congratulations!\nYou guessed the word:\n" + gameWord);
    }

    /**
     * Handles the click event of the "Back to Main" button.
     * Starts the MainActivity and finishes the current activity.
     *
     * @param view The view that triggers this method (Back to Main button).
     */
    public void backToMainButtonClick(View view) {
        // Create an intent to start the MainActivity
        Intent intent = new Intent(this, MainActivity.class);

        // Start the MainActivity
        startActivity(intent);

        // Finish the current activity
        finish();
    }
}
