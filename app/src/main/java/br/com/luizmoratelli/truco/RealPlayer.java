package br.com.luizmoratelli.truco;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RealPlayer implements Player {
    private ArrayList<Card> cards = null;

    public RealPlayer(ArrayList<Card> cards) {
        this.cards = cards;
    }

    @Override
    public void updateHand() {
        for (int i = 0; i < 3; i++) {
            if (cards.size() > i + 1) {
                MainActivity.playerCards.get(i).setImageResource(cards.get(i).image); ;
            } else {
                MainActivity.playerCards.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }
}
