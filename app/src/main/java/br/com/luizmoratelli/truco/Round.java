package br.com.luizmoratelli.truco;

import android.os.Build;

import androidx.annotation.RequiresApi;

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
        // Define o turno no começo de um turno
        if (playerTurn instanceof RealPlayer) game.setPlayerTurn();
        else if (playerTurn instanceof IAPlayer) game.setEnemyTurn();
        // Define o turno após um dos player ter jogado
        else if (playerCard == null && enemyCard != null) game.setPlayerTurn();
        else if (enemyCard == null && playerCard != null) game.setEnemyTurn();
        // Calcula o vencedor da rodada
        else if (playerCard != null && enemyCard != null) {
            if (playerCard.getValue() > enemyCard.getValue()) {
                winner = Game.player;
            } else if (enemyCard.getValue() > playerCard.getValue()) {
                winner = Game.enemy;
            } else {
                draw = true;
            }

            game.createNewRound();

           for (int i = 0; i < Game.rounds.size(); i++) {
               boolean playerWins = Game.rounds.get(i).winner instanceof RealPlayer;
               boolean draw = Game.rounds.get(i).draw;

               if (draw) {
                   MainActivity.roundsScore.get(i).setImageResource(R.drawable.back_draw);
               } else if (playerWins) {
                   MainActivity.roundsScore.get(i).setImageResource(R.drawable.back_win);
               } else {
                   MainActivity.roundsScore.get(i).setImageResource(R.drawable.back_loose);
               }
           }
        }

        return null;
    }

    public Round(Digit powerfulCard, Game game) {
       this.powerfulCard = powerfulCard;
       this.game = game;
    }
}
