package com.patchen.wordy;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Activity for playing the game.
 */
public class PlayActivity extends AppCompatActivity {
    // The word for the current game
    String gameWord;
    // List to store the characters of the game word
    ArrayList<Character> gW = new ArrayList<>();
    // Current round number
    int round = 1;
    // List to store rows of EditTexts for the game board
    private List<List<EditText>> editTextRows = new ArrayList<>();

    /**
     * Called when the activity is first created.
     * Sets the content view and initializes the game board.
     *
     * @param savedInstanceState A saved instance state of the application.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // Get the word from the intent
        Intent intent = getIntent();
        gameWord = getIntent().getStringExtra("WORD");

        // Break the word into characters and store in the list
        gW = breakApartString(gameWord);

        // Set up the clear button and create the game board
        Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearEditTextValues();
                recreate();
            }
        });

        createETArray();
        createETObjects();
    }

    // Method to create a 2D array of EditText objects
    private void createETArray() {
        for (int i = 1; i <= 6; i++) {
            List<EditText> row = new ArrayList<>();
            for (int j = 1; j <= 5; j++) {
                int editTextId = getResources().getIdentifier("r" + i + "c" + j + "ET", "id", getPackageName());
                EditText editText = findViewById(editTextId);
                editText.setBackgroundColor(Color.LTGRAY);
                row.add(editText);
            }
            editTextRows.add(row);
        }
    }

    // Method to create EditText objects and set up TextWatchers
    private void createETObjects() {
        EditText[][] editTextArray = new EditText[6][5];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                String editTextID = "r" + (i + 1) + "c" + (j + 1) + "ET";
                int resID = getResources().getIdentifier(editTextID, "id", getPackageName());
                editTextArray[i][j] = findViewById(resID);
                editTextArray[i][j].addTextChangedListener(new MyTextWatcher());
            }
        }
    }

    // Method to clear all EditText values and reset the game board
    private void clearEditTextValues() {
        for (int i = 0; i < editTextRows.size(); i++) {
            List<EditText> row = editTextRows.get(i);
            for (int j = 0; j < row.size(); j++) {
                EditText editText = row.get(j);
                editText.setText("");
                editText.setBackgroundColor(Color.LTGRAY);
                editText.setBackgroundResource(R.drawable.underline);
            }
        }
        round = 1;
        //setNextRowVisible();
    }

    // Method to break apart a string into a list of characters
    private ArrayList<Character> breakApartString(String inputString) {
        ArrayList<Character> characters = new ArrayList<>();
        if (inputString != null) {
            for (char c : inputString.toCharArray()) {
                characters.add(c);
            }
        }
        return characters;
    }

    /**
     * Custom TextWatcher for handling user input in the game.
     */
    private class MyTextWatcher implements TextWatcher {
        // List to store rows of EditTexts for the game board
        private List<List<EditText>> editTextRows;

        // Constructor to initialize the list of EditText rows
        public MyTextWatcher() {
            editTextRows = new ArrayList<>();
            for (int i = 1; i <= 6; i++) {
                List<EditText> row = new ArrayList<>();
                for (int j = 1; j <= 5; j++) {
                    int editTextId = getResources().getIdentifier("r" + i + "c" + j + "ET", "id", getPackageName());
                    EditText editText = findViewById(editTextId);
                    row.add(editText);
                }
                editTextRows.add(row);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Not needed for this example
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Not needed for this example
        }

        @Override
        public void afterTextChanged(Editable editable) {
            int nonEmptyCount = 0;

            // Count non-empty EditTexts
            for (EditText editText : editTextRows.get(round - 1)) {
                if (!editText.getText().toString().toUpperCase().trim().isEmpty()) {
                    nonEmptyCount++;
                }
            }

            // Trigger your method when 5 EditTexts have values
            if (nonEmptyCount == 5) {
                boolean allGreen = true;

                // Check user input against the correct word
                for (int i = 0; i < gW.size(); i++) {
                    for (EditText editText : editTextRows.get(round - 1)) {
                        String editTextValue = editText.getText().toString().toUpperCase();
                        String gWValue = String.valueOf(gW.get(i));

                        if (editTextValue.equals(gW.get(editTextRows.get(round - 1).indexOf(editText)).toString())) {
                            editText.setBackgroundColor(Color.GREEN);
                        } else if (editTextValue.equals(gWValue)) {
                            editText.setBackgroundColor(Color.YELLOW);
                        } else {
                            allGreen = false;
                        }
                    }
                }

                // Start a new activity with the victory screen if all characters are correct
                if (allGreen) {
                    Intent intent = new Intent(PlayActivity.this, VictoryActivity.class);
                    intent.putExtra("GAME_WORD", gameWord);
                    startActivity(intent);
                } else {
                    // If not all characters are correct, proceed to the next round
                    if (round < 6) {
                        setNextRowVisible();
                    }
                    round++;
                }
            }
        }
    }

    // Method to set visibility for the next row of EditTexts
    public void setNextRowVisible() {
        for (EditText editText : editTextRows.get(round)) {
            editText.setVisibility(View.VISIBLE);
            //editText.setBackgroundColor(Color.LTGRAY);
        }
    }

    // Method to handle the "New Word" button click
    public void newWord(View view) {
        getNewWordFromDatabase();
        clearEditTextValues();
        createETArray();
    }

    // Method to fetch a new word from the database
    private void getNewWordFromDatabase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("words");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> wordList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String word = snapshot.getValue(String.class);
                    wordList.add(word);
                }
                if (!wordList.isEmpty()) {
                    // Randomly select a new word from the database
                    String newWord = wordList.get(new Random().nextInt(wordList.size()));
                    startNewRound(newWord);
                } else {
                    Toast.makeText(PlayActivity.this, "No words available in the database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DatabaseError", "Error fetching new word: " + databaseError.getMessage());
            }
        });
    }

    // Method to start a new round with a given word
    private void startNewRound(String newWord) {
        round = 1;
        gameWord = newWord;
        gW = breakApartString(gameWord);

    }
}
