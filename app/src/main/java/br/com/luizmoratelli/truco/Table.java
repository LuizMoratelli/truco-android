package br.com.luizmoratelli.truco;

import java.util.Stack;

class Table {
    static Stack<Card> cards = new Stack<Card>();
    static boolean cardsOnTable = false;

    void addCard(Card card) {
        cardsOnTable = true;
        cards.add(card);
        MainActivity.tableCard.setImageResource(card.image);
    }

    void clean() {
        cardsOnTable = false;
        MainActivity.tableCard.setImageResource(R.drawable.back);
    }
}
