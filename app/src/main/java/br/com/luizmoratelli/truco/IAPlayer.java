package br.com.luizmoratelli.truco;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

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
            if (cards.size() < i + 1) {
                MainActivity.enemyCards.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public Card playCard(int position) {
        // Decidir como serão feitas as escolhas
        // Ordenar em uma lista de mais forte para mais fraca, considerar coringas
        /*
         * Se já tem card na mesa, jogar a mais fraca que possa ganhar.
         * Se não tem:
         *   Se ganhou a primeira: jogar mais fraca;
         *   Se perdeu a primeira: jogar mais forte;
         * */
        return null;
    }

    public void sortCards() {
        // da mais forte -> mais fraca, considerar coringas
    }
}
