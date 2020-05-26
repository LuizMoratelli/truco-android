package br.com.luizmoratelli.truco;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

public class IAPlayer implements Player {
    private ArrayList<Card> cards = null;
    private Context context;

    public IAPlayer(ArrayList<Card> cards) {
        this.cards = cards;
    }

    @Override
    public void updateHand() {
        for (int i = 0; i < 3; i++) {
            if (cards.size() < i + 1) {
                MainActivity.enemyCards.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }
}
