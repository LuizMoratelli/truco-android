package br.com.luizmoratelli.truco;

import android.content.Context;

// Provável que precis de Game -> Rodada -> Turno (Ou fazer no turno um for de 3 jogadas)
// Acho que vai ter que seprar pra conseguir salvar os clicks e gerar as ações pelos rounds
public class Game {
    private final Context context;
    private Deck deck = null;
    private Player winner = null;
    public static Player player;
    public static Player enemy;
    public static Table table;
    private final int initialCards = 3;
    private Card turnedCard;
    private Digit powerfulCard;
    private Boolean playerFirst = true;
    private Boolean isBluffed;
    private int playerScore = 0;
    private int enemyScore = 0;

    public Game(Context context) {
        this.context = context;
    }

    private void setupTurn() {
        deck = new Deck(context);

        player = new RealPlayer(deck.draw(initialCards));
        enemy = new IAPlayer(deck.draw(initialCards));
        table = new Table();
        playerFirst = !playerFirst;
        setTurnedCard();
        player.updateHand();
        enemy.updateHand();
        table.clean();
    }

    public void nextTurn() {
        setupTurn();
        int playerWins = 0;
        Boolean isBluffed = false;

        //for (int round = 0; round < 3; round++) {
            // if (playerWins > 2 || (playerWins == 0 && i == 2)) break;
            player.updateHand();
            enemy.updateHand();

            // Se um jogador ganhar as duas primeiras rodadas acaba o round
        // Player Joga (Truca, Corre, Aceita ou joga Card)
            // IA Joga (Mesmo)
            // Compara quem venceu e atribui os pontos
            // Resetar truco a cada "Rodada"
        //}

        int scoreToAdd = isBluffed ? 3 : 1;
        if (playerWins >= 2) playerScore += scoreToAdd;
        else enemyScore += scoreToAdd;

        updateScore();
    }

    // Comparar a última carta de jogar e determinar qual ganhou, atribuir o direito de começar ao ganhador do round anterior
    // No fim de 3 rounds, precisa recomeçar as cards, e atribuir os pontos, considerando ou não truco;

    public void setTurnedCard() {
        turnedCard = deck.draw(1).get(0);
        MainActivity.turnedCard.setImageResource(turnedCard.image);
        powerfulCard = turnedCard.digit.next();
    }

    private void updateScore() {
        MainActivity.playerScore.setText(playerScore + "/12");
        MainActivity.enemyScore.setText(enemyScore + "/12");

        if (playerScore >= 12) {
            // Player ganhou mensagem
            winner = player;
        } else if (enemyScore >= 12) {
            winner = enemy;
        } else {
            //nextTurn();
        }
    }
}
