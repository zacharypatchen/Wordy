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

public class AddWordsActivity extends AppCompatActivity {
    String errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_words);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("words");
    }

    public void submitWord(View view) {
        EditText wordET = findViewById(R.id.wordET);
        String word = wordET.getText().toString().toUpperCase();
        addWordToDatabase(word);
    }
    public void clearDatabase(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("words");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Remove all entries
                myRef.removeValue();

                // Notify the user
                Toast.makeText(AddWordsActivity.this, "Database cleared", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DatabaseError", "Error clearing the database: " + databaseError.getMessage());
                Toast.makeText(AddWordsActivity.this, "Error clearing the database", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addWordToDatabase(String word) {
        if (wordCheck(word)) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("words");

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean wordExists = false;

                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        String databaseWord = childSnapshot.getValue(String.class);

                        if (word.equalsIgnoreCase(databaseWord)) {
                            wordExists = true;
                            errorMessage = "Error: " + word + " exists in the database";
                            Toast.makeText(AddWordsActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                            break;
                        }
                    }

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


    private Boolean wordCheck(String word) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("words");

        if (word.length() != 5) {
            errorMessage = "Error: " + word + " is not 5 characters.";
            Toast.makeText(AddWordsActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            return false;
        } else if (!word.matches("^[a-zA-Z]*$")) {
            errorMessage = "Error: " + word + " contains special characters";
            Toast.makeText(AddWordsActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            return false;
        }
        // You can also add more validation checks if needed
        return true;
    }

}


