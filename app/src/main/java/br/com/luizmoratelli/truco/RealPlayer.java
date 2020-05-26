package br.com.luizmoratelli.truco;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.ArrayList;

public class RealPlayer implements Player {
    private ArrayList<Card> cards = null;
    private Game game;

    public RealPlayer(ArrayList<Card> cards, Game game) {
        this.cards = cards;
        this.game = game;

        MainActivity.playerCards.get(0).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Card playedCard = playCard(0);

                if (playedCard != null) {
                    Game.table.addCard(playedCard);
                    updateHand();
                    game.checkRound(true, playedCard);
                }
            }
        });

        MainActivity.playerCards.get(1).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Card playedCard = playCard(1);

                if (playedCard != null) {
                    Game.table.addCard(playedCard);
                    updateHand();
                    game.checkRound(true, playedCard);
                }
            }
        });

        MainActivity.playerCards.get(2).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Card playedCard = playCard(2);

                if (playedCard != null) {
                    Game.table.addCard(playedCard);
                    updateHand();
                    game.checkRound(true, playedCard);
                }
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Card playCard(int position) {
        if (!Game.playerRound) return null;

        if (cards.size() > position) {
            return cards.remove(position);
        }

        return null;
    }
}
