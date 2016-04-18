package com.example.nipunmanocha.fingerdance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by Nipun Manocha on 4/10/2016.
 */

//Class for the HomePage
public class HomeActivity extends AppCompatActivity {
    Button button, button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        //Start Playing Button
        button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(HomeActivity.this, ChhotiActivity.class);
                startActivity(in);
            }
        });

        button2 = (Button)findViewById(R.id.button2a);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(HomeActivity.this, RuleActivity.class);
                startActivity(in);
            }
        });
    }

    //Custom functionality on pressing back button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finishAffinity();
        return true;
    }
}
