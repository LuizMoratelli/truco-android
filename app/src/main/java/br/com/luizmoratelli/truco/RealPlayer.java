package br.com.luizmoratelli.truco;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.ArrayList;

public class RealPlayer implements Player {
    private ArrayList<Card> cards = null;

    public RealPlayer(ArrayList<Card> cards) {
        this.cards = cards;

        MainActivity.playerCards.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game.table.addCard(playCard(0));
                updateHand();
            }
        });

        MainActivity.playerCards.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game.table.addCard(playCard(1));
                updateHand();
            }
        });

        MainActivity.playerCards.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game.table.addCard(playCard(2));
                updateHand();
            }
        });
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

    @Override
    public Card playCard(int position) {
        if (cards.size() > position) {
            return cards.remove(position);
        }

        return null;
    }
}
