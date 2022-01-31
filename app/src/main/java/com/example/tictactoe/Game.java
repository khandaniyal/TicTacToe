package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Game extends AppCompatActivity implements View.OnClickListener {
    private Button reset;
    private Button[] arrayButtons;
    private int[][] winningPositions = {
            {0,1,2}, {3,4,5}, {6,7,8}, //rows
            {0,3,6}, {1,4,7}, {2,5,8}, //columns
            {0,4,8}, {2,4,6} //cross
    };
    //p1 -> 0
    //p2 -> 1
    //empty or null -> 2
    private int gameState[] = {2,2,2,2,2,2,2,2,2};
    private boolean currentPlayer;
    private int playerRoundCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        arrayButtons = new Button[9];
        reset = findViewById(R.id.btnReset);

        reset.setOnClickListener(e-> playAgain());

        //get the button id from the layout and initialise them
        for(int i = 0; i < arrayButtons.length; i++){
            String btnID = "btn" + i;
            int resourceID = getResources().getIdentifier(btnID, "id", getPackageName());
            arrayButtons[i] = (Button) findViewById(resourceID);
            arrayButtons[i].setOnClickListener(this);
        }
        playerRoundCount = 0;
        currentPlayer = true;
    }

    @Override
    public void onClick(View view) {
        //if the button was already pressed it cannot be pressed again by the two players
        if(!((Button)view).getText().toString().equals("")) return;
        Log.i("Test", "button clicked");

        String buttonID = view.getResources().getResourceEntryName(view.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

        if(currentPlayer){
            ((Button)view).setText("X");
            ((Button)view).setTextSize(50);
            ((Button)view).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;
        }else {
            ((Button)view).setText("O");
            ((Button)view).setTextSize(50);
            ((Button)view).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1;
        }
        playerRoundCount++;

        if(checkWinner() == true){
            if(currentPlayer){
                playerRoundCount++;
                Toast.makeText(this, "Player One Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }else{
                playerRoundCount++;
                Toast.makeText(this, "Player Two Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        }else if(playerRoundCount == 9){
            playAgain();
            Toast.makeText(this, "It's a Draw!", Toast.LENGTH_SHORT).show();
        }
        else{
            currentPlayer = !currentPlayer;
        }
    }

    private void playAgain() {
        playerRoundCount = 0;
        currentPlayer = true;

        for (int i = 0; i < arrayButtons.length; i++){
            gameState[i] = 2;
            arrayButtons[i].setText("");
        }
    }

    public boolean checkWinner(){
        boolean winnerResult = false;
        for (int[] winningPos : winningPositions){
            if(gameState[winningPos[0]] == gameState[winningPos[1]] && gameState[winningPos[1]] == gameState[winningPos[2]] && gameState[winningPos[0]] != 2){
                winnerResult = true;
            }
        }
        return winnerResult;
    }
}