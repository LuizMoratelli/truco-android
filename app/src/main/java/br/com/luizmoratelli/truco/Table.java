package br.com.luizmoratelli.truco;

import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

public class Table {
    private Stack<Card> cards = new Stack<Card>();

    public void addCard(Card card) {
        cards.add(card);
        MainActivity.tableCard.setImageResource(card.image);
    }

    public void clean() {
        MainActivity.tableCard.setImageResource(R.drawable.back);
        // Ou deixar invisible?
    }
}
