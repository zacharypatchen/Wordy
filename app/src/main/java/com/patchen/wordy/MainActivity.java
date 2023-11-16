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


public class MainActivity extends AppCompatActivity {
    String[] words ={"hello","apple","chill","puppy","dream", "guard", "adult",
            "wound", "force","flood"};

    Random r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

//        for (int i = 0; i < words.length; i++) {
//            DatabaseReference wordRef = myRef.push();
//            wordRef.setValue(words[i].toUpperCase());
//        }
    }
    public void openPlayActivity(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("words");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    ArrayList<String> wordList = new ArrayList<>();
                    for (DataSnapshot s : snapshot.getChildren()) {
                        String randomWord = s.getValue(String.class);
                        wordList.add(randomWord);
                    }

                    Random r = new Random();
                    String randomWord = wordList.get(r.nextInt(wordList.size()));

                    Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                    intent.putExtra("WORD", randomWord);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Error: Database is empty", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseError", "Error reading database: " + error.getMessage());
                Toast.makeText(MainActivity.this, "Error reading database", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openDBActivity(View view){
        Intent intent = new Intent(this, AddWordsActivity.class);
        startActivity(intent);
    }
}