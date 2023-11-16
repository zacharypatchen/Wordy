
package com.patchen.wordy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VictoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victory);

        // Retrieve the game word from the intent
        String gameWord = getIntent().getStringExtra("GAME_WORD");

        // Display the game word
        TextView gameWordTextView = findViewById(R.id.gameWordTextView);
        gameWordTextView.setText("Congratulations!\nYou guessed the word:\n" + gameWord);
    }

    // Button click handler to return to the main activity
    public void backToMainButtonClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Close the current activity
    }
}
