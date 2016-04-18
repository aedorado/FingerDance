package com.example.nipunmanocha.fingerdance;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

//Main Activity where game is played
public class MainActivity extends AppCompatActivity implements View.OnTouchListener, Runnable {
    GridLayout myGridLayout;
    TextView text1;
    View myViews[];
    ArrayList available;
    String currentPlayer, player1, player2;
    String[] assign;

    int n;
    int gameover, reqindex, touch1, touch2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //flag to tell if the game is over or not
        gameover = 0;

        //Retreiving the data sent by last activity
        Intent in = getIntent();
        n = Integer.parseInt(in.getStringExtra("n"));
        player1 = in.getStringExtra("player1");
        player2 = in.getStringExtra("player2");

        myGridLayout = (GridLayout)findViewById(R.id.mygrid);
        text1 = (TextView)findViewById(R.id.txt1);

        //Dynamically making a n x n board using the grid layout
        myGridLayout.setColumnCount(n);
        myGridLayout.setRowCount(n);

        int numOfCol = myGridLayout.getColumnCount();
        final int numOfRow = myGridLayout.getRowCount();
        myViews = new View[numOfCol * numOfRow];
        available = new ArrayList(numOfCol * numOfRow);
        assign = new String[n*n];

        for (int i = 0; i < (n*n); i++) {
            available.add(i);
        }

        int xPos, yPos;
        for(yPos = 0; yPos < numOfRow; yPos++){
            for(xPos = 0; xPos < numOfCol; xPos++){
                View tView = new View(this);
                tView.setBackgroundColor(Color.BLUE);
                myViews[(yPos * numOfRow) + xPos] = tView;
                myGridLayout.addView(tView);
            }
        }

        myGridLayout.getViewTreeObserver().addOnGlobalLayoutListener(
            new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    final int MARGIN = 5;
                    int pWidth = myGridLayout.getWidth();
                    int pHeight = myGridLayout.getHeight();
                    int numOfCol = myGridLayout.getColumnCount();
                    int numOfRow = myGridLayout.getRowCount();
                    int w = pWidth / numOfCol;
                    int h = pHeight / numOfRow;

                    for (int yPos = 0; yPos < numOfRow; yPos++) {
                        for (int xPos = 0; xPos < numOfCol; xPos++) {
                            GridLayout.LayoutParams params =
                                    (GridLayout.LayoutParams) myViews[yPos * numOfRow + xPos].getLayoutParams();
                            params.width = w - 2 * MARGIN;
                            params.height = h - 2 * MARGIN;
                            params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
                            myViews[yPos * numOfRow + xPos].setLayoutParams(params);
                        }
                    }
                }
            }
        );

        //Starting the game with current player as the first player
        currentPlayer = player1;
        text1.setText(currentPlayer + "'s turn");
        int x = nextTile();
        int tile = (int)available.get(x);
        available.remove(x);
        assign[tile] = currentPlayer;
        myViews[tile].setBackgroundColor(Color.GREEN);
        myViews[tile].setOnTouchListener(this);
    }

    //function to generate a random tile when called.
    private int nextTile() {
        System.out.println();
        System.out.println("Size of arraylist = " + available.size());
        System.out.println("Contents of al: " + available);
        int rnd = new Random().nextInt(available.size());
        System.out.println("Random index = " + rnd + " element removed = " + available.get(rnd).toString());
        return rnd;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (gameover == 0) {
            //If a view is clicked, then generating next random tile along with changing the current player
            if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                if (!available.isEmpty()) {
                    if (currentPlayer.equals(player1)) {
                        currentPlayer = player2;
                    }
                    else if (currentPlayer.equals(player2)) {
                        currentPlayer = player1;
                    }

                    v.setBackgroundColor(Color.YELLOW);
                    text1.setText(currentPlayer + "'s turn");
                    int x = nextTile();
                    int tile = (int) available.get(x);
                    available.remove(x);
                    assign[tile] = currentPlayer;
                    myViews[tile].setBackgroundColor(Color.GREEN);
                    myViews[tile].setOnTouchListener(this);
                }
            }

            //if finger is lifted from any tile, that player is assigned as the loser
            //and gameover flag is set. A new thread is started which tells the scores
            //of both the players
            else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                reqindex = 0;
                gameover = 1;
                touch1 = 0;
                touch2 = 0;
                for (int i = 0; i < (n * n); i++) {
                    if (v == myViews[i]) {
                        reqindex = i;
                        break;
                    }
                }
                text1.setText(assign[reqindex] + " loses");
                v.setBackgroundColor(Color.RED);
                Thread t = new Thread(this, "Result");
                t.start();

                //counting the number of successful touches by both the players
                touch1 = 0;
                touch2 = 0;
                for (int i = 0; i < (n*n); i++) {
                    if (player1.equals(assign[i])) touch1++;
                    else if (player2.equals(assign[i])) touch2++;
                }

                if (assign[reqindex].equals(player1)) touch1--;
                else if (assign[reqindex].equals(player2)) touch2--;
            }
        }
        return true;
    }

    //Thread that fires an intent to the activity that shows the results of the game.
    @Override
    public void run() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent in = new Intent(MainActivity.this, FinalActivity.class);
        in.putExtra("loser", assign[reqindex]);
        in.putExtra("player1", player1);
        in.putExtra("player2", player2);
        in.putExtra("succ1", touch1);
        in.putExtra("succ2", touch2);
        in.putExtra("en", n);
        startActivity(in);
    }

    //custom functionality on pressing back button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent in = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(in);
        return true;
    }
}
