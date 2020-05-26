package br.com.luizmoratelli.truco;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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
        //

        Game game = new Game(this);
        game.nextTurn();
    }
}
