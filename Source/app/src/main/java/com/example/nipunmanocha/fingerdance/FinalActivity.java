package com.example.nipunmanocha.fingerdance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Nipun Manocha on 4/11/2016.
 */
public class FinalActivity extends AppCompatActivity {
    String player1, player2, loser, string_n;
    int tch1, tch2, n;
    TextView txt1, txt2;
    Button but1, but2, but_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.75);
        setContentView(R.layout.finalact);
        getWindow().setLayout(screenWidth, WindowManager.LayoutParams.WRAP_CONTENT);

        Intent in = getIntent();
        player1 = in.getStringExtra("player1");
        player2 = in.getStringExtra("player2");
        loser = in.getStringExtra("loser");
        tch1 = in.getIntExtra("succ1", 0);
        tch2 = in.getIntExtra("succ2", 0);
        n = in.getIntExtra("en", 0);
        string_n = String.valueOf(n);

        txt1 = (TextView)findViewById(R.id.res1);
        txt2 = (TextView)findViewById(R.id.res2);
        but1 = (Button)findViewById(R.id.button);
        but2 = (Button)findViewById(R.id.button5);
        but_home = (Button)findViewById(R.id.button4);

        txt1.setText(loser + " loses the game.");
        txt2.setText("Successful touches\n " + player1 + " : " + tch1
        + "\n" + player2 + " : " + tch2);

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(FinalActivity.this, MainActivity.class);
                in.putExtra("n", string_n);
                in.putExtra("player1", player1);
                in.putExtra("player2", player2);
                startActivity(in);
            }
        });

        but_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(FinalActivity.this, HomeActivity.class);
                startActivity(in);
            }
        });

        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                //System.exit(0);
            }
        });
    }
}
