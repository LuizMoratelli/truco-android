package br.com.luizmoratelli.truco;

import android.content.Context;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;

public class Deck {
    private Stack cards;

    public Deck(final Context context) {
        cards = new Stack<Card>(){{
            add(new Card("ca", context));
            add(new Card("da", context));
            add(new Card("ha", context));
            add(new Card("sa", context));
            add(new Card("c2", context));
            add(new Card("d2", context));
            add(new Card("h2", context));
            add(new Card("s2", context));
            add(new Card("c3", context));
            add(new Card("d3", context));
            add(new Card("h3", context));
            add(new Card("s3", context));
            add(new Card("c4", context));
            add(new Card("d4", context));
            add(new Card("h4", context));
            add(new Card("s4", context));
            add(new Card("c5", context));
            add(new Card("d5", context));
            add(new Card("h5", context));
            add(new Card("s5", context));
            add(new Card("c6", context));
            add(new Card("d6", context));
            add(new Card("h6", context));
            add(new Card("s6", context));
            add(new Card("c7", context));
            add(new Card("d7", context));
            add(new Card("h7", context));
            add(new Card("s7", context));
            add(new Card("cq", context));
            add(new Card("dq", context));
            add(new Card("hq", context));
            add(new Card("sq", context));
            add(new Card("cj", context));
            add(new Card("dj", context));
            add(new Card("hj", context));
            add(new Card("sj", context));
            add(new Card("ck", context));
            add(new Card("dk", context));
            add(new Card("hk", context));
            add(new Card("sk", context));
        }};
    }

    public ArrayList<Card> draw (int quantity) {
        ArrayList<Card> drawnCards = new ArrayList<Card>();

        for (int i = 0; i <= quantity; i++) {
            int size = cards.size();
            int randomPosition = new Random().nextInt(size);
            Card drawnCard = (Card) cards.remove(randomPosition);
            drawnCards.add(drawnCard);
        }

        return drawnCards;
    }
}
