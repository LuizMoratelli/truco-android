package br.com.luizmoratelli.truco;

import android.content.Context;
import android.content.res.Resources;
import android.media.Image;

import java.io.CharConversionException;

public class Card {
    public Suit suit;
    public Digit digit;
    public int image;

    public Card(String card, Context context) {
        image = context.getResources().getIdentifier(card, "drawable", context.getPackageName());
        char suit = card.charAt(0);
        char digit = card.charAt(1);
        try {
            setDigit(digit);
            setSuit(suit);
        } catch (Exception e) {
            System.out.println("Some card was wrong:" + card);
        }
    }

    public void setDigit(char digit) throws java.io.CharConversionException {
        switch (digit) {
            case 'a': this.digit = Digit.A; break;
            case '2': this.digit = Digit.Two; break;
            case '3': this.digit = Digit.Three; break;
            case '4': this.digit = Digit.Four; break;
            case '5': this.digit = Digit.Five; break;
            case '6': this.digit = Digit.Six; break;
            case '7': this.digit = Digit.Seven; break;
            case 'q': this.digit = Digit.Q; break;
            case 'j': this.digit = Digit.J; break;
            case 'k': this.digit = Digit.K; break;
            default: throw new CharConversionException("Wrong Digit");
        }
    }

    public void setSuit(char suit) throws java.io.CharConversionException{
        switch (suit) {
            case 'c': this.suit = Suit.Clubs; break;
            case 'd': this.suit = Suit.Diamonds; break;
            case 'h': this.suit = Suit.Hearts; break;
            case 's': this.suit = Suit.Spades; break;
            default: throw new CharConversionException("Wrong Suit");
        }
    }

    public Integer getValue(Digit powerful) {
        if (powerful == digit) {
            switch (suit) {
                case Clubs: return 14;
                case Hearts: return 13;
                case Spades: return 12;
                case Diamonds: return 11;
                default: return 0;
            }
        }

        switch (digit) {
            case Three: return 10;
            case Two: return 9;
            case A: return 8;
            case K: return 7;
            case J: return 6;
            case Q: return 5;
            case Seven: return 4;
            case Six: return 3;
            case Five: return 2;
            case Four: return 1;
            default: return 0;
        }
    }
}
