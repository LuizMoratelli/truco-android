package br.com.luizmoratelli.truco;

import android.content.Context;

// Provável que precis de Game -> Rodada -> Turno (Ou fazer no turno um for de 3 jogadas)
public class Game {
    private final Context context;
    private Deck deck = null;
    private Player winner = null;
    private Player player;
    private Player enemy;
    private final int initialCards = 3;
    private Card turnedCard;
    private Digit powerfulCard;
    private Player startPlaying;

    public Game(Context context) {
        this.context = context;
        deck = new Deck(context);

        player = new RealPlayer(deck.draw(initialCards));
        enemy = new IAPlayer(deck.draw(initialCards));
        // A primeira jogada de todas é do player (talvez mudar pra random)
        startPlaying = player;
        setTurnedCard();
    }

    public Player getWinner() {
        return winner;
    }

    public void nextTurn() {
        for (int round = 0; round < 3; round++) {
            player.updateHand();
            enemy.updateHand();
            // Player Joga (Truca, Corre, Aceita ou joga Card)
            // IA Joga (Mesmo)
            // Compara quem venceu e atribui os pontos
            // Resetar truco a cada "Rodada"
        }
    }

    public void setTurnedCard() {
        turnedCard = deck.draw(1).get(0);
        MainActivity.turnedCard.setImageResource(turnedCard.image);
        powerfulCard = turnedCard.digit.next();
    }
}
