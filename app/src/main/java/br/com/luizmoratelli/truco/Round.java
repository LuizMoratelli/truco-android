package br.com.luizmoratelli.truco;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class Round {
    public Boolean draw;
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
    public void check() {
        if (playerCard == null && enemyCard != null) Game.setPlayerTurn();
        else if (enemyCard == null && playerCard != null) Game.setEnemyTurn();

        if (playerCard != null && enemyCard != null) {
            if (playerCard.getValue(powerfulCard) > enemyCard.getValue(powerfulCard)) {
                winner = Game.player;
            } else if (enemyCard.getValue(powerfulCard) > playerCard.getValue(powerfulCard)) {
                winner = Game.enemy;
            } else {
                draw = true;
            }
        }

        game.createNewRound();
    }

    public Round(Digit powerfulCard, Game game) {
       this.powerfulCard = powerfulCard;
       this.game = game;
    }
}
