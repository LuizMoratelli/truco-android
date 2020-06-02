package br.com.luizmoratelli.truco;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class IAPlayer implements Player {
    private ArrayList<Card> cards = null;
    private Game game;

    public IAPlayer(ArrayList<Card> cards, Game game) {
        this.cards = cards;
        this.game = game;
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
        if (position >= cards.size()) return null;
        Card playedCard = cards.remove(position);
        return playedCard;
        // Decidir como serão feitas as escolhas
        // Ordenar em uma lista de mais forte para mais fraca, considerar coringas
        /*
         * Se já tem card na mesa, jogar a mais fraca que possa ganhar.
         * Se não tem:
         *   Se ganhou a primeira: jogar mais fraca;
         *   Se perdeu a primeira: jogar mais forte;
         * */
    }

    public void sortCards() {
        // da mais forte -> mais fraca, considerar coringas
    }
}
