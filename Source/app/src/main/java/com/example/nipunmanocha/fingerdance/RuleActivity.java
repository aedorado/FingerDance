package com.example.nipunmanocha.fingerdance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Nipun Manocha on 4/16/2016.
 */
public class RuleActivity extends Activity {
    TextView txt1;
    Button play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.9);
        setContentView(R.layout.rules);
        getWindow().setLayout(screenWidth, WindowManager.LayoutParams.WRAP_CONTENT);

        txt1 = (TextView)findViewById(R.id.ruletext);
        txt1.setText("1. The board contains n*n different colored F (Finomena) tiles.\n" +
                "2. At the start, the user selects the value of N.\n" +
                "3. The game starts with a random F-tile highlighted.\n" +
                "4. The player would touch and hold the highlighted F-tile, " +
                "after which the next random tile would automatically get highlighted.\n" +
                "5. The 2nd player would touch and hold the next highlighted tile.\n" +
                "6. This would go on until:\n" +
                "- The player clicks on a wrong tile.\n" +
                "- The player lifts the finger from a previously selected tile.\n");

        play = (Button)findViewById(R.id.buttonr1);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(RuleActivity.this, ChhotiActivity.class);
                startActivity(in);
            }
        });
    }
}
