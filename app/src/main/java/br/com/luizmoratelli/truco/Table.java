package br.com.luizmoratelli.truco;

import java.util.Stack;

public class Table {
    public static Stack<Card> cards = new Stack<Card>();
    public static boolean cardsOnTable = false;

    public void addCard(Card card) {
        cardsOnTable = true;
        cards.add(card);
        MainActivity.tableCard.setImageResource(card.image);
    }

    public void clean() {
        cardsOnTable = false;
        MainActivity.tableCard.setImageResource(R.drawable.back);
    }
}
