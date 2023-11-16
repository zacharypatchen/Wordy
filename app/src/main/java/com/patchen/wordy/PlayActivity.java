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

public class PlayActivity extends AppCompatActivity {
    String randomWord;
    String gameWord;
    ArrayList<Character> gW = new ArrayList<>();
    //EditText r1c1 = findViewById(R.id.r1c1ET);
//EditText r1c2 = findViewById(R.id.r1c2ET);
//EditText r1c3 = findViewById(R.id.r1c3ET);
//EditText r1c4 = findViewById(R.id.r1c4ET);
//EditText r1c5 = findViewById(R.id.r1c5ET);
//ArrayList <Character> wordToGuess = new ArrayList<>();
    int round = 1;
    private List<List<EditText>> editTextRows = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Intent intent = getIntent();
        gameWord = getIntent().getStringExtra("WORD");
        for (int i = 1; i <= 6; i++) {
            List<EditText> row = new ArrayList<>();
            for (int j = 1; j <= 5; j++) {
                int editTextId = getResources().getIdentifier("r" + i + "c" + j + "ET", "id", getPackageName());
                EditText editText = findViewById(editTextId);
                row.add(editText);
            }
            editTextRows.add(row);
        }
        Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Reset the activity
                clearEditTextValues();
                recreate();
            }
        });
        gW = breakApartString(gameWord);
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
    private void clearEditTextValues() {
        for (int i = 0; i < editTextRows.size(); i++) {
            List<EditText> row = editTextRows.get(i);
            for (int j = 0; j < row.size(); j++) {
                EditText editText = row.get(j);
                editText.setText("");  // Clear the text
                editText.setBackgroundColor(Color.TRANSPARENT);// Reset background color
                editText.setBackgroundResource(R.drawable.underline);
            }
        }

        // Reset round and set visibility for the first row
        round = 1;
        setNextRowVisible();
    }

        private ArrayList<Character> breakApartString(String inputString) {
        ArrayList<Character> characters = new ArrayList<>();
        if (inputString != null) {
            for (char c : inputString.toCharArray()) {
                characters.add(c);
            }
        }
        return characters;
    }


    private class MyTextWatcher implements TextWatcher {
        private List<List<EditText>> editTextRows;

        // Initialize and organize EditTexts into rows
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
                boolean allGreen = true; // Flag to check if all EditTexts are green

                // Iterate through the selected row
                for (int i = 0; i < gW.size(); i++) {
                    for (EditText editText : editTextRows.get(round - 1)) {
                        String editTextValue = editText.getText().toString().toUpperCase();
                        String gWValue = String.valueOf(gW.get(i));

                        if (editTextValue.equals(gW.get(editTextRows.get(round - 1).indexOf(editText)).toString())) {
                            editText.setBackgroundColor(Color.GREEN);
                        } else if (editTextValue.equals(gWValue)) {
                            editText.setBackgroundColor(Color.YELLOW);
                        } else {
                            allGreen = false; // Set flag to false if any EditText is not green
                        }
                    }
                }

                // Check if all EditTexts are green
                if (allGreen) {
                    // Start a new activity with the victory screen
                    Intent intent = new Intent(PlayActivity.this, VictoryActivity.class);
                    intent.putExtra("GAME_WORD", gameWord);
                    startActivity(intent);
                } else {
                    // Set visibility for the next row
                    if (round < 6) {
                        setNextRowVisible();
                    }

                    round++;
                }
            }
        }


        // Set visibility for the next row

    }
    public void setNextRowVisible() {
        for (EditText editText : editTextRows.get(round)) {
            editText.setVisibility(View.VISIBLE);
        }
    }
    public void newWord(View view) {
        // Clear the EditText values and reset the activity
        clearEditTextValues();

        // Get a new word from the database
        getNewWordFromDatabase();
    }

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
                    // Pick a random word from the list
                    String newWord = wordList.get(new Random().nextInt(wordList.size()));

                    // Start a new round with the new word
                    startNewRound(newWord);
                } else {
                    // Handle the case when the database is empty
                    Toast.makeText(PlayActivity.this, "No words available in the database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DatabaseError", "Error fetching new word: " + databaseError.getMessage());
            }
        });
    }

    private void startNewRound(String newWord) {
        // Reset the round and set visibility for the first row
        round = 1;
        //setNextRowVisible();

        // Update the gameWord with the new word
        gameWord = newWord;
        gW = breakApartString(gameWord);

        // Optionally, update UI to display the new word or perform any other necessary actions
    }


}