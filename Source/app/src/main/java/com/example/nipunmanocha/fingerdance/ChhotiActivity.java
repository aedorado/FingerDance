package com.example.nipunmanocha.fingerdance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.*;

//Form Activty which takes dimensions of grid and name of 2 players as input.
public class ChhotiActivity extends AppCompatActivity {
    EditText ed1, player1, player2;
    Button but1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting dimensions of this activity to 90% of screen size
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.90);
        setContentView(R.layout.chhoti);

        getWindow().setLayout(screenWidth, WindowManager.LayoutParams.WRAP_CONTENT);

        ed1 = (EditText)findViewById(R.id.en);
        player1 = (EditText)findViewById(R.id.pl1);
        player2 = (EditText)findViewById(R.id.pl2);
        but1 = (Button)findViewById(R.id.button3);

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ensuring that dimension lies between 0-25
                if (!isNumeric(ed1.getText().toString()) || Integer.parseInt(ed1.getText().toString()) <= 0 ||
                        Integer.parseInt(ed1.getText().toString()) >= 25) {
                    Toast.makeText(getApplicationContext(), "Dimension should be a positive number less than 25", Toast.LENGTH_SHORT).show();
                }

                //ensuring that all fields are filled.
                else if (!ed1.getText().toString().equals("") && !player1.getText().toString().equals("") &&
                        !player2.getText().toString().equals("")) {
                    //firing an intent to start next activity along with the data
                    Intent in = new Intent(ChhotiActivity.this, MainActivity.class);
                    in.putExtra("n", ed1.getText().toString());
                    in.putExtra("player1", player1.getText().toString());
                    in.putExtra("player2", player2.getText().toString());
                    startActivity(in);
                }
                else
                    Toast.makeText(getApplicationContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //function to check if the string is numeric or not
    public boolean isNumeric(String str)
    {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent in = new Intent(ChhotiActivity.this, HomeActivity.class);
        startActivity(in);
        return true;
    }
}
