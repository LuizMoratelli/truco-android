package br.com.luizmoratelli.truco;

import android.view.View;

import java.util.ArrayList;

public class IAPlayer implements Player {
    private ArrayList<Card> cards = null;
    private Game game;

    public IAPlayer(ArrayList<Card> cards, Game game) {
        this.cards = cards;
        this.game = game;
    }

    // IA decide se aceita ou não
    public static Boolean acceptBluff() {
        return false;
    }

    @Override
    public void updateHand() {
        for (int i = 0; i < 3; i++) {
            if (cards.size() > i) {
                MainActivity.enemyCards.get(i).setVisibility(View.VISIBLE);
                // Enable to see enemy cards
                // MainActivity.enemyCards.get(i).setImageResource(cards.get(i).image);
            } else {
                MainActivity.enemyCards.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public Card playCard(int position) {
        int pos = 0;

        if (!Table.cardsOnTable) {
            boolean wonLast = true;

            if (Game.rounds.size() > 1) {
                wonLast = Game.rounds.get(Game.rounds.size() - 1).winner instanceof IAPlayer
                            && !Game.rounds.get(Game.rounds.size() - 1).draw;
            }

            if (wonLast) {
                pos = getHighestValuableCardPosition();
            } else {
                pos = getLowestValuableCardPosition();
            }
        } else {
            int lastCardValue = Table.cards.lastElement().getValue();

            if (canWinRound(lastCardValue)) {
                boolean canDraw = true;

                if (Game.rounds.size() > 1) {
                    canDraw = Game.rounds.get(Game.rounds.size() - 1).winner instanceof IAPlayer;
                }

                if (canDraw) {
                    pos = getLowestValuableCardGreaterThan(lastCardValue);
                } else {
                    pos = getHighestValuableCardPosition();
                }
            } else {
                pos = getLowestValuableCardPosition();
            }
        }

        Card playedCard = cards.remove(pos);
        return playedCard;
    }

    // Retornar a carta mais fraca que seja suficiente pra fazer (poder ser null caso não haja)
    public int getLowestValuableCardPosition() {
        int pos = 0;

        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getValue() < cards.get(pos).getValue()) {
                pos = i;
            }
        }

        return pos;
    }

    public int getHighestValuableCardPosition() {
        int pos = 0;

        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getValue() > cards.get(pos).getValue()) {
                pos = i;
            }
        }

        return pos;
    }

    public int getLowestValuableCardGreaterThan(int value) {
        int pos = -1;

        for (int i = 0; i < cards.size(); i++) {
            if (pos == -1 && cards.get(i).getValue() >= value) {
                pos = i;
            } else if (pos != -1 && cards.get(i).getValue() < cards.get(pos).getValue() && cards.get(i).getValue() >= value) {
                pos = i;
            }
        }

        return pos;
    }

    public boolean canWinRound(int value) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getValue() >= value) return true;
        }

        return false;
    }
}
