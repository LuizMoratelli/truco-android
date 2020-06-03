package br.com.luizmoratelli.truco;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<ImageView> playerCards = new ArrayList<ImageView>();
    public static ArrayList<ImageView> enemyCards = new ArrayList<ImageView>();
    public static ImageView turnedCard = null;
    public static ImageView tableCard = null;
    public static TextView enemyScore = null;
    public static TextView playerScore = null;
    public static ArrayList<Button> playerActions = new ArrayList<Button>();
    public static ArrayList<ImageView> roundsScore = new ArrayList<ImageView>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set Views (Change to BroadCast Receiver!?)
        playerCards.add((ImageView) findViewById(R.id.player1Card));
        playerCards.add((ImageView) findViewById(R.id.player2Card));
        playerCards.add((ImageView) findViewById(R.id.player3Card));
        enemyCards.add((ImageView) findViewById(R.id.enemy1Card));
        enemyCards.add((ImageView) findViewById(R.id.enemy2Card));
        enemyCards.add((ImageView) findViewById(R.id.enemy3Card));
        turnedCard = findViewById(R.id.turnedCard);
        tableCard = findViewById(R.id.tableCard);
        enemyScore = findViewById(R.id.enemyScore);
        playerScore = findViewById(R.id.playerScore);
        playerActions.add((Button) findViewById(R.id.buttonBluff));
        playerActions.add((Button) findViewById(R.id.buttonRun));
        playerActions.add((Button) findViewById(R.id.buttonAccept));
        playerActions.add((Button) findViewById(R.id.buttonOk));
        roundsScore.add((ImageView) findViewById(R.id.winRound1));
        roundsScore.add((ImageView) findViewById(R.id.winRound2));
        roundsScore.add((ImageView) findViewById(R.id.winRound3));

        ResetPlayerButtonsColor(this);

        Game game = new Game(this);
        game.nextTurn();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void ResetPlayerButtonsColor(Context context) {
        for (int i = 0; i < playerActions.size(); i++) {
            if (i == Game.BUTTON_BLUFF && Game.isBluffed) {
                MainActivity.ChangePlayerButtonColor(i, Game.context, R.color.yellow);
            } else {
                playerActions.get(i).setBackgroundTintList(ContextCompat.getColorStateList(context, android.R.color.darker_gray));
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void ChangePlayerButtonColor(int id, Context context, int color) {
        MainActivity.playerActions.get(id).setBackgroundTintList(ContextCompat.getColorStateList(context, color));
    }
}
