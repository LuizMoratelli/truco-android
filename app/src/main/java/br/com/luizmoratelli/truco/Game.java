package br.com.luizmoratelli.truco;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class Game {
    public static Context context = null;
    private Deck deck = null;
    private static Player winner = null;
    public static Player player;
    public static Player enemy;
    public static Table table;
    private static final int initialCards = 3;
    private static Card turnedCard;
    private static Digit powerfulCard;
    public static Boolean playerTurn = false;
    public static Player playerRound = null;
    public static Boolean isBluffed = false;
    private static int playerScore = 0;
    private static int enemyScore = 0;
    public static ArrayList<Round> rounds = new ArrayList<Round>();
    public static Game instance;
    public static boolean playerCanPlay = false;

    public Game(Context context) {
        this.context = context;
        table = new Table();
        instance = this;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setPlayerTurn() {
        playerRound = player;
        playerCanPlay = true;

        // Habilitar botões e clicks nas cards
        MainActivity.ChangePlayerButtonColor(0, context, isBluffed ? R.color.yellow : R.color.green);

        MainActivity.playerActions.get(0).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (playerCanPlay && !isBluffed) {
                    isBluffed = true;
                    MainActivity.ChangePlayerButtonColor(0, context, R.color.yellow);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setEnemyTurn() {
        playerRound = enemy;
        Card playedCard = enemy.playCard(0);
        enemy.updateHand();
        table.addCard(playedCard);
        checkRound(false, playedCard, null);
        // Desabilitar botões e clicks do player, esperar uns 2~5 segundos e fazer uma jogada.
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupTurn() {
        MainActivity.playerActions.get(3).setOnClickListener(NoAction());

        for (int i = 0; i < MainActivity.roundsScore.size(); i++) {
            MainActivity.roundsScore.get(i).setImageResource(R.drawable.back);
        }

        deck = new Deck(context);
        player = new RealPlayer(deck.draw(initialCards), this);
        enemy = new IAPlayer(deck.draw(initialCards), this);
        rounds = new ArrayList<Round>();
        playerTurn = !playerTurn;
        setTurnedCard();
        player.updateHand();
        enemy.updateHand();
        table.clean();

        isBluffed = false;
        MainActivity.ResetPlayerButtonsColor(context);
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void createNewRound() {
        boolean createNewRound = true;
        boolean playerWinner = false;
        int drawRounds = (int) rounds.stream().filter(round -> round.draw).count();
        int playerRoundsWon = (int) rounds.stream().filter(round -> round.winner == player).count();
        playerCanPlay = false;

        if (rounds.size() == 2 && drawRounds < 2) {
            if (playerRoundsWon == 2 || (playerRoundsWon == 1 && drawRounds == 1)) {
                createNewRound = false;
                playerWinner = true;
            } else if (playerRoundsWon == 0) {
                createNewRound = false;
            }
        } else if (rounds.size() == 3) {
            createNewRound = false;

            if (playerRoundsWon >= 2 || (drawRounds == 2 && playerRoundsWon == 1)) {
                playerWinner = true;
            }
        }

        if (createNewRound) {
            playerRound = rounds.get(rounds.size() - 1).winner;

            if (playerRound == null) {
                playerRound = playerTurn ? player : enemy;
            }

            if (rounds.get(rounds.size() - 1).draw) {
                MainActivity.ChangePlayerButtonColor(3, context, R.color.blue);
            } else if (rounds.get(rounds.size() - 1).winner instanceof RealPlayer) {
                MainActivity.ChangePlayerButtonColor(3, context, R.color.green);
            } else {
                MainActivity.ChangePlayerButtonColor(3, context, R.color.purple);
            }

            MainActivity.playerActions.get(3).setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    MainActivity.ChangePlayerButtonColor(3, context, android.R.color.darker_gray);
                    table.clean();
                    rounds.add(new Round(powerfulCard, instance));
                    checkRound(false, null, playerRound);
                    playerCanPlay = true;
                    MainActivity.playerActions.get(3).setOnClickListener(NoAction());
                }
            });
        } else {
            int scoreToAdd = isBluffed ? 3 : 1;

            if (drawRounds < 3) {
                if (playerWinner) playerScore += scoreToAdd;
                else enemyScore += scoreToAdd;

                if (playerWinner) {
                    MainActivity.ChangePlayerButtonColor(3, context, R.color.green);
                } else {
                    MainActivity.ChangePlayerButtonColor(3, context, R.color.purple);
                }
            } else {
                MainActivity.ChangePlayerButtonColor(3, context, R.color.blue);
            }

            updateScore();

            MainActivity.playerActions.get(3).setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    nextTurn();
                    playerCanPlay = true;
                    MainActivity.playerActions.get(3).setOnClickListener(NoAction());
                }
            });
        }
    }

    public static View.OnClickListener NoAction() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void nextTurn() {
        setupTurn();
        rounds.add(new Round(powerfulCard, this));

        if (playerTurn) {
           setPlayerTurn();
        } else {
            setEnemyTurn();
        }

    }

    public void setTurnedCard() {
        turnedCard = deck.draw(1).get(0);
        MainActivity.turnedCard.setImageResource(turnedCard.image);
        powerfulCard = turnedCard.digit.next();
    }

    private void updateScore() {
        MainActivity.playerScore.setText(playerScore + "/12");
        MainActivity.enemyScore.setText(enemyScore + "/12");

        // Transicionar para Tela de vitória/derrota
        if (playerScore >= 12) {
            winner = player;
            Toast.makeText(
                    context,
                    R.string.text_win,
                    Toast.LENGTH_LONG
            ).show();

        } else if (enemyScore >= 12) {
            winner = enemy;
            Toast.makeText(
                    context,
                    R.string.text_lose,
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void checkRound(Boolean playerCard, Card card, Player playerRound) {
        if (card != null) {
            if (playerCard) {
                rounds.get(rounds.size() - 1).setPlayerCard(card);
            } else {
                rounds.get(rounds.size() - 1).setEnemyCard(card);
            }
        }

        rounds.get(rounds.size() - 1).check(playerRound);
    }
}
