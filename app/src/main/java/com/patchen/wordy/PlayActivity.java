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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Intent intent = getIntent();
        gameWord = getIntent().getStringExtra("WORD");
        gW = breakApartString(gameWord);
        EditText r1c1 = findViewById(R.id.r1c1ET);
        EditText r1c2 = findViewById(R.id.r1c2ET);
        EditText r1c3 = findViewById(R.id.r1c3ET);
        EditText r1c4 = findViewById(R.id.r1c4ET);
        EditText r1c5 = findViewById(R.id.r1c5ET);
        EditText r2c1 = findViewById(R.id.r2c1ET);
        EditText r2c2 = findViewById(R.id.r2c2ET);
        EditText r2c3 = findViewById(R.id.r2c3ET);
        EditText r2c4 = findViewById(R.id.r2c4ET);
        EditText r2c5 = findViewById(R.id.r2c5ET);
        EditText r3c1 = findViewById(R.id.r3c1ET);
        EditText r3c2 = findViewById(R.id.r3c2ET);
        EditText r3c3 = findViewById(R.id.r3c3ET);
        EditText r3c4 = findViewById(R.id.r3c4ET);
        EditText r3c5 = findViewById(R.id.r3c5ET);
        EditText r4c1 = findViewById(R.id.r4c1ET);
        EditText r4c2 = findViewById(R.id.r4c2ET);
        EditText r4c3 = findViewById(R.id.r4c3ET);
        EditText r4c4 = findViewById(R.id.r4c4ET);
        EditText r4c5 = findViewById(R.id.r4c5ET);
        EditText r5c1 = findViewById(R.id.r5c1ET);
        EditText r5c2 = findViewById(R.id.r5c2ET);
        EditText r5c3 = findViewById(R.id.r5c3ET);
        EditText r5c4 = findViewById(R.id.r5c4ET);
        EditText r5c5 = findViewById(R.id.r5c5ET);
        EditText r6c1 = findViewById(R.id.r6c1ET);
        EditText r6c2 = findViewById(R.id.r6c2ET);
        EditText r6c3 = findViewById(R.id.r6c3ET);
        EditText r6c4 = findViewById(R.id.r6c4ET);
        EditText r6c5 = findViewById(R.id.r6c5ET);
        //r1c1.addTextChangedListener(new MyTextWatcher());
        //r1c2.addTextChangedListener(new MyTextWatcher());
        //r1c3.addTextChangedListener(new MyTextWatcher());
       // r1c4.addTextChangedListener(new MyTextWatcher());
        r1c5.addTextChangedListener(new MyTextWatcher());
       // r2c1.addTextChangedListener(new MyTextWatcher());
        //r2c2.addTextChangedListener(new MyTextWatcher());
        //r2c3.addTextChangedListener(new MyTextWatcher());
       // r2c4.addTextChangedListener(new MyTextWatcher());
        r2c5.addTextChangedListener(new MyTextWatcher());
        //r3c1.addTextChangedListener(new MyTextWatcher());
        //r3c2.addTextChangedListener(new MyTextWatcher());
       //r3c3.addTextChangedListener(new MyTextWatcher());
        //r3c4.addTextChangedListener(new MyTextWatcher());
        r3c5.addTextChangedListener(new MyTextWatcher());
        //r4c1.addTextChangedListener(new MyTextWatcher());
        //r4c2.addTextChangedListener(new MyTextWatcher());
        //r4c3.addTextChangedListener(new MyTextWatcher());
        //r4c4.addTextChangedListener(new MyTextWatcher());
        r4c5.addTextChangedListener(new MyTextWatcher());
        //r5c1.addTextChangedListener(new MyTextWatcher());
        //r5c2.addTextChangedListener(new MyTextWatcher());
        //r5c3.addTextChangedListener(new MyTextWatcher());
        //r5c4.addTextChangedListener(new MyTextWatcher());
        r5c5.addTextChangedListener(new MyTextWatcher());
        //r6c1.addTextChangedListener(new MyTextWatcher());
        //r6c2.addTextChangedListener(new MyTextWatcher());
        //r6c3.addTextChangedListener(new MyTextWatcher());
        //r6c4.addTextChangedListener(new MyTextWatcher());
        r6c5.addTextChangedListener(new MyTextWatcher());
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
        EditText r1c1 = findViewById(R.id.r1c1ET);
        EditText r1c2 = findViewById(R.id.r1c2ET);
        EditText r1c3 = findViewById(R.id.r1c3ET);
        EditText r1c4 = findViewById(R.id.r1c4ET);
        EditText r1c5 = findViewById(R.id.r1c5ET);
        EditText r2c1 = findViewById(R.id.r2c1ET);
        EditText r2c2 = findViewById(R.id.r2c2ET);
        EditText r2c3 = findViewById(R.id.r2c3ET);
        EditText r2c4 = findViewById(R.id.r2c4ET);
        EditText r2c5 = findViewById(R.id.r2c5ET);
        EditText r3c1 = findViewById(R.id.r3c1ET);
        EditText r3c2 = findViewById(R.id.r3c2ET);
        EditText r3c3 = findViewById(R.id.r3c3ET);
        EditText r3c4 = findViewById(R.id.r3c4ET);
        EditText r3c5 = findViewById(R.id.r3c5ET);
        EditText r4c1 = findViewById(R.id.r4c1ET);
        EditText r4c2 = findViewById(R.id.r4c2ET);
        EditText r4c3 = findViewById(R.id.r4c3ET);
        EditText r4c4 = findViewById(R.id.r4c4ET);
        EditText r4c5 = findViewById(R.id.r4c5ET);
        EditText r5c1 = findViewById(R.id.r5c1ET);
        EditText r5c2 = findViewById(R.id.r5c2ET);
        EditText r5c3 = findViewById(R.id.r5c3ET);
        EditText r5c4 = findViewById(R.id.r5c4ET);
        EditText r5c5 = findViewById(R.id.r5c5ET);
        EditText r6c1 = findViewById(R.id.r6c1ET);
        EditText r6c2 = findViewById(R.id.r6c2ET);
        EditText r6c3 = findViewById(R.id.r6c3ET);
        EditText r6c4 = findViewById(R.id.r6c4ET);
        EditText r6c5 = findViewById(R.id.r6c5ET);


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

            switch (round) {
                case 1:
                    if (!r1c1.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r1c2.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r1c3.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r1c4.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r1c5.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    // Trigger your method when 5 EditTexts have values
                    if (nonEmptyCount == 5) {
                        for (int i = 0; i < gW.size(); i++) {
                            if (r1c1.getText().toString().toUpperCase().equals((String.valueOf(gW.get(0))))) {
                                r1c1.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r1c1.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r1c1.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r1c2.getText().toString().toUpperCase().equals((String.valueOf(gW.get(1))))) {
                                r1c2.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r1c2.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r1c2.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r1c3.getText().toString().toUpperCase().equals((String.valueOf(gW.get(2))))) {
                                r1c3.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r1c3.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r1c3.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r1c4.getText().toString().toUpperCase().equals((String.valueOf(gW.get(3))))) {
                                r1c4.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r1c4.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r1c4.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r1c5.getText().toString().toUpperCase().equals((String.valueOf(gW.get(4))))) {
                                r1c5.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r1c5.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r1c5.setBackgroundColor(Color.YELLOW);//green
                            }
                        }
                        r2c1.setVisibility(View.VISIBLE);
                        r2c2.setVisibility(View.VISIBLE);
                        r2c3.setVisibility(View.VISIBLE);
                        r2c4.setVisibility(View.VISIBLE);
                        r2c5.setVisibility(View.VISIBLE);
                        round++;
                    }
                    break;
                case 2:
                    if (!r2c1.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r2c2.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r2c3.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r2c4.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r2c5.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (nonEmptyCount == 5) {
                        for (int i = 0; i < gW.size(); i++) {
                            if (r2c1.getText().toString().toUpperCase().equals((String.valueOf(gW.get(0))))) {
                                r2c1.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r2c1.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r2c1.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r2c2.getText().toString().toUpperCase().equals((String.valueOf(gW.get(1))))) {
                                r2c2.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r2c2.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r2c2.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r2c3.getText().toString().toUpperCase().equals((String.valueOf(gW.get(2))))) {
                                r2c3.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r2c3.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r2c3.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r2c4.getText().toString().toUpperCase().equals((String.valueOf(gW.get(3))))) {
                                r2c4.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r2c4.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r2c4.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r2c5.getText().toString().toUpperCase().equals((String.valueOf(gW.get(4))))) {
                                r2c5.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r2c5.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r2c5.setBackgroundColor(Color.YELLOW);//green
                            }

                        }
                        r3c1.setVisibility(View.VISIBLE);
                        r3c2.setVisibility(View.VISIBLE);
                        r3c3.setVisibility(View.VISIBLE);
                        r3c4.setVisibility(View.VISIBLE);
                        r3c5.setVisibility(View.VISIBLE);
                        round++;
                    }
                    break;
                case 3:
                    if (!r3c1.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r3c2.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r3c3.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r3c4.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r3c5.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (nonEmptyCount == 5) {
                        for (int i = 0; i < gW.size(); i++) {
                            if (r3c1.getText().toString().toUpperCase().equals((String.valueOf(gW.get(0))))) {
                                r3c1.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r3c1.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r3c1.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r3c2.getText().toString().toUpperCase().equals((String.valueOf(gW.get(1))))) {
                                r3c2.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r3c2.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r3c2.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r3c3.getText().toString().toUpperCase().equals((String.valueOf(gW.get(2))))) {
                                r3c3.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r3c3.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r3c3.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r3c4.getText().toString().toUpperCase().equals((String.valueOf(gW.get(3))))) {
                                r3c4.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r3c4.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r3c4.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r3c5.getText().toString().toUpperCase().equals((String.valueOf(gW.get(4))))) {
                                r3c5.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r3c5.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r3c5.setBackgroundColor(Color.YELLOW);//green
                            }
                        }
                        r4c1.setVisibility(View.VISIBLE);
                        r4c2.setVisibility(View.VISIBLE);
                        r4c3.setVisibility(View.VISIBLE);
                        r4c4.setVisibility(View.VISIBLE);
                        r4c5.setVisibility(View.VISIBLE);
                    }
                    round++;
                    break;
                case 4:
                    if (!r4c1.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r4c2.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r4c3.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r4c4.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r4c5.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (nonEmptyCount == 5) {
                        for (int i = 0; i < gW.size(); i++) {
                            if (r4c1.getText().toString().toUpperCase().equals((String.valueOf(gW.get(0))))) {
                                r4c1.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r4c1.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r4c1.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r4c2.getText().toString().toUpperCase().equals((String.valueOf(gW.get(1))))) {
                                r4c2.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r4c2.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r4c2.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r4c3.getText().toString().toUpperCase().equals((String.valueOf(gW.get(2))))) {
                                r4c3.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r4c3.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r4c3.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r4c4.getText().toString().toUpperCase().equals((String.valueOf(gW.get(3))))) {
                                r4c4.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r4c4.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r4c4.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r4c5.getText().toString().toUpperCase().equals((String.valueOf(gW.get(4))))) {
                                r4c5.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r4c5.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r4c5.setBackgroundColor(Color.YELLOW);//green
                            }
                        }
                        r5c1.setVisibility(View.VISIBLE);
                        r5c2.setVisibility(View.VISIBLE);
                        r5c3.setVisibility(View.VISIBLE);
                        r5c4.setVisibility(View.VISIBLE);
                        r5c5.setVisibility(View.VISIBLE);
                    }
                    round++;
                    break;
                case 5:
                    if (!r5c1.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r5c2.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r5c3.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r5c4.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r5c5.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (nonEmptyCount == 5) {
                        for (int i = 0; i < gW.size(); i++) {
                            if (r5c1.getText().toString().toUpperCase().equals((String.valueOf(gW.get(0))))) {
                                r5c1.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r5c1.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r5c1.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r5c2.getText().toString().toUpperCase().equals((String.valueOf(gW.get(1))))) {
                                r5c2.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r5c2.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r5c2.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r5c3.getText().toString().toUpperCase().equals((String.valueOf(gW.get(2))))) {
                                r5c3.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r5c3.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r5c3.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r5c4.getText().toString().toUpperCase().equals((String.valueOf(gW.get(3))))) {
                                r5c4.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r5c4.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r5c4.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r5c5.getText().toString().toUpperCase().equals((String.valueOf(gW.get(4))))) {
                                r5c5.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r5c5.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r5c5.setBackgroundColor(Color.YELLOW);//green
                            }
                        }
                        r6c1.setVisibility(View.VISIBLE);
                        r6c2.setVisibility(View.VISIBLE);
                        r6c3.setVisibility(View.VISIBLE);
                        r6c4.setVisibility(View.VISIBLE);
                        r6c5.setVisibility(View.VISIBLE);

                    }
                    round++;
                    break;
                case 6:
                    if (!r6c1.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r6c2.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r6c3.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r6c4.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (!r6c5.getText().toString().toUpperCase().trim().isEmpty()) nonEmptyCount++;
                    if (nonEmptyCount == 5) {
                        for (int i = 0; i < gW.size(); i++) {
                            if (r6c1.getText().toString().toUpperCase().equals((String.valueOf(gW.get(0))))) {
                                r6c1.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r6c1.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r6c1.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r6c2.getText().toString().toUpperCase().equals((String.valueOf(gW.get(1))))) {
                                r6c2.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r6c2.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r6c2.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r6c3.getText().toString().toUpperCase().equals((String.valueOf(gW.get(2))))) {
                                r6c3.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r6c3.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r6c3.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r6c4.getText().toString().toUpperCase().equals((String.valueOf(gW.get(3))))) {
                                r6c4.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r6c4.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r6c4.setBackgroundColor(Color.YELLOW);//green
                            }
                            if (r6c5.getText().toString().toUpperCase().equals((String.valueOf(gW.get(4))))) {
                                r6c5.setBackgroundColor(Color.GREEN); //yellow
                            } else if (r6c5.getText().toString().toUpperCase().equals((String.valueOf(gW.get(i))))) {
                                r6c5.setBackgroundColor(Color.YELLOW);//green
                            }
                        }
                    }
                    break;
                default:
                    // code block
            }
        }

    }
}