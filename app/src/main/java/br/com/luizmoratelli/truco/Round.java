package br.com.luizmoratelli.truco;

import android.os.Build;

import androidx.annotation.RequiresApi;

class Round {
    Boolean draw = false;
    Player winner;
    private Card playerCard;
    private Card enemyCard;
    private Digit powerfulCard;
    private Game game;

    void setPlayerCard(Card card) {
        playerCard = card;
    }

    void setEnemyCard(Card card) {
        enemyCard = card;
    }

    /*
    * Verificar se algum Player precisa jogar, caso contrário calcula o vencedor do round
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    String check(Player playerTurn) {
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

            // Define os ícones de pontuação dos rounds
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

    Round(Digit powerfulCard, Game game) {
       this.powerfulCard = powerfulCard;
       this.game = game;
    }
}
