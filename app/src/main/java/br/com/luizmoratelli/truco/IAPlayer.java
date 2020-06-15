package br.com.luizmoratelli.truco;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Random;

public class IAPlayer implements Player {
    private static ArrayList<Card> cards = null;
    private Game game;

    IAPlayer(ArrayList<Card> cards, Game game) {
        IAPlayer.cards = cards;
        this.game = game;
    }

    // IA decide se aceita ou não o truco
    @RequiresApi(api = Build.VERSION_CODES.N)
    static Boolean acceptBluff() {
        int goodCards = getCountOfGoodCards();
        int jokersCards = getCountOfJokersCards();
        int chanceToAccept = 50;

        if (Game.rounds.size() == 1) {
            if (jokersCards >= 2) {
                chanceToAccept = 100;
            } else if (jokersCards == 1 && goodCards >= 1) {
                chanceToAccept = 95;
            } else if (goodCards == 3) {
                chanceToAccept = 90;
            } else if (goodCards == 2) {
                chanceToAccept = 85;
            } else if (goodCards == 1) {
                chanceToAccept = 70;
            }
        } else if (Game.rounds.size() == 2) {
            if (drawMatches() == 1 || wonMatches() == 1) {
                if (jokersCards >= 1) {
                    chanceToAccept = 100;
                } else if (goodCards == 2) {
                    chanceToAccept = 85;
                } else if (goodCards == 1) {
                    chanceToAccept = 70;
                } else {
                    chanceToAccept = 40;
                }
            } else {
                if (jokersCards >= 1) {
                    chanceToAccept = 90;
                } else if (goodCards == 2) {
                    chanceToAccept = 75;
                } else if (goodCards == 1) {
                    chanceToAccept = 60;
                }
            }
        } else if (Game.rounds.size() == 3) {
            if (jokersCards == 1) {
                chanceToAccept = 100;
            } else if (goodCards == 1) {
                chanceToAccept = 80;
            }
        }

        return chanceToAccept > new Random().nextInt(100);
    }

    @Override
    public void updateHand() {
        for (int i = 0; i < 3; i++) {
            if (cards.size() > i) {
                MainActivity.enemyCards.get(i).setVisibility(View.VISIBLE);
                // Enable to see enemy cards
                // MainActivity.enemyCards.get(i).setImageResource(cards.get(i).image);
            } else {
                MainActivity.enemyCards.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    /*
    * Determina qual carta a IA vai jogar
     */
    @Override
    public Card playCard(int position) {
        int pos = 0;

        if (!Table.cardsOnTable) {
            boolean wonLast = true;

            if (Game.rounds.size() > 1) {
                wonLast = Game.rounds.get(Game.rounds.size() - 1).winner instanceof IAPlayer
                            && !Game.rounds.get(Game.rounds.size() - 1).draw;
            }

            if (wonLast) {
                pos = getHighestValuableCardPosition();
            } else {
                pos = getLowestValuableCardPosition();
            }
        } else {
            int lastCardValue = Table.cards.lastElement().getValue();

            if (canWinRound(lastCardValue)) {
                boolean canDraw = true;

                if (Game.rounds.size() > 1) {
                    canDraw = Game.rounds.get(Game.rounds.size() - 1).winner instanceof IAPlayer;
                }

                if (canDraw) {
                    pos = getLowestValuableCardGreaterThan(lastCardValue);
                } else {
                    pos = getHighestValuableCardPosition();
                }
            } else {
                pos = getLowestValuableCardPosition();
            }
        }

        Card playedCard = cards.remove(pos);
        return playedCard;
    }

    // Retornar a carta mais fraca que seja suficiente pra fazer (poder ser null caso não haja)
    private int getLowestValuableCardPosition() {
        int pos = 0;

        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getValue() < cards.get(pos).getValue()) {
                pos = i;
            }
        }

        return pos;
    }

    // Retorna a carta mais forte
    private int getHighestValuableCardPosition() {
        int pos = 0;

        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getValue() > cards.get(pos).getValue()) {
                pos = i;
            }
        }

        return pos;
    }

    // Retorna a carta mais fraca que seja maior que um valor
    private int getLowestValuableCardGreaterThan(int value) {
        int pos = -1;

        for (int i = 0; i < cards.size(); i++) {
            if (pos == -1 && cards.get(i).getValue() >= value) {
                pos = i;
            } else if (pos != -1 && cards.get(i).getValue() < cards.get(pos).getValue() && cards.get(i).getValue() >= value) {
                pos = i;
            }
        }

        return pos;
    }

    // Verifica se alguma carta pode ganhar da do oponente
    private boolean canWinRound(int value) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getValue() >= value) return true;
        }

        return false;
    }

    // Good Cards are greater or equals to K
    private static int getCountOfGoodCards() {
        int count = 0;

        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getValue() >= 7 && cards.get(i).getValue() <= 10) count++;
        }

        return count;
    }

    // Retorna a quantidade de cartas coringas na mão
    private static int getCountOfJokersCards() {
        int count = 0;

        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getValue() >= 11) count++;
        }

        return count;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static int drawMatches() {
        return (int) Game.rounds.stream().filter(round -> round.draw).count();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static int wonMatches() {
        return (int) Game.rounds.stream().filter(round -> round.winner instanceof IAPlayer).count();
    }
}
