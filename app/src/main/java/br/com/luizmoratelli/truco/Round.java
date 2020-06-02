package br.com.luizmoratelli.truco;

import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.Timer;
import java.util.TimerTask;

public class Round {
    public Boolean draw = false;
    public Player winner;
    public Card playerCard;
    public Card enemyCard;
    private Digit powerfulCard;
    private Game game;

    public void setPlayerCard(Card card) {
        playerCard = card;
    }

    public void setEnemyCard(Card card) {
        enemyCard = card;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String check(Player playerTurn) {
        if (playerTurn instanceof RealPlayer) game.setPlayerTurn();
        else if (playerTurn instanceof IAPlayer) game.setEnemyTurn();
        else if (playerCard == null && enemyCard != null) game.setPlayerTurn();
        else if (enemyCard == null && playerCard != null) game.setEnemyTurn();
        else if (playerCard != null && enemyCard != null) {
            String texto = null;
            if (playerCard.getValue(powerfulCard) > enemyCard.getValue(powerfulCard)) {
                winner = Game.player;
                texto = "Feita por você";
            } else if (enemyCard.getValue(powerfulCard) > playerCard.getValue(powerfulCard)) {
                winner = Game.enemy;
                texto = "Feita pelo oponente";
            } else {
                draw = true;
                texto = "Empate";
            }

            // Esperar 1s antes disso
            game.createNewRound();

            return texto;
        }

        return null;
    }

    public Round(Digit powerfulCard, Game game) {
       this.powerfulCard = powerfulCard;
       this.game = game;
    }
}
