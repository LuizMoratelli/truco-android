package br.com.luizmoratelli.truco;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class RealPlayer implements Player {
    private ArrayList<Card> cards;

    public RealPlayer(ArrayList<Card> cards, Game game) {
        this.cards = cards;

        MainActivity.playerCards.get(0).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (Game.playerCanPlay) {
                    Card playedCard = playCard(0);

                    if (playedCard != null) {
                        Game.table.addCard(playedCard);
                        updateHand();
                        game.checkRound(true, playedCard, null);
                    }
                }
            }
        });

        MainActivity.playerCards.get(1).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (Game.playerCanPlay) {
                    Card playedCard = playCard(1);

                    if (playedCard != null) {
                        Game.table.addCard(playedCard);
                        updateHand();
                        game.checkRound(true, playedCard, null);
                    }
                }
            }
        });

        MainActivity.playerCards.get(2).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (Game.playerCanPlay) {
                    Card playedCard = playCard(2);

                    if (playedCard != null) {
                        Game.table.addCard(playedCard);
                        updateHand();
                        game.checkRound(true, playedCard, null);
                    }
                }
            }
        });
    }

    @Override
    public void updateHand() {
        for (int i = 0; i < 3; i++) {
            if (cards.size() > i) {
                MainActivity.playerCards.get(i).setVisibility(View.VISIBLE);
                MainActivity.playerCards.get(i).setImageResource(cards.get(i).image); ;
            } else {
                MainActivity.playerCards.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Card playCard(int position) {
        MainActivity.ResetPlayerButtonsColor(Game.context);

        if (Game.playerRound instanceof RealPlayer && cards.size() > position) {
            return cards.remove(position);
        }

        return null;
    }
}
