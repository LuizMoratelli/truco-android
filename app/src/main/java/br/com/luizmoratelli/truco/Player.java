package br.com.luizmoratelli.truco;

import java.util.ArrayList;

public interface Player {
    public void updateHand();
    public Card playCard(int position); // Talvez retornar a card jogada
}
