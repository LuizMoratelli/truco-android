package br.com.luizmoratelli.truco;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class Game {
    static final int BUTTON_BLUFF = 0;
    static final int BUTTON_ACCEPT = 1;
    static final int BUTTON_RUN = 2;
    static final int BUTTON_OK = 3;
    static final int initialCards = 3;

    @SuppressLint("StaticFieldLeak")
    private static Game instance;
    @SuppressLint("StaticFieldLeak")
    static Context context = null;

    private Deck deck = null;
    private static Player winner = null;
    private static Card turnedCard;
    private static int playerScore = 0;
    private static int enemyScore = 0;
    private static Boolean playerTurn = false;

    static Digit powerfulCard;
    static Player playerRound = null;
    static Boolean isBluffed = false;
    static ArrayList<Round> rounds = new ArrayList<Round>();
    static boolean playerCanPlay = false;
    static Player player;
    static Player enemy;
    static Table table;

    Game(Context context) {
        // Reset values for new Matches
        winner = null;
        playerScore = 0;
        enemyScore = 0;
        playerCanPlay = false;
        updateScore();
        //

        Game.context = context;
        table = new Table();
        instance = this;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void setPlayerTurn() {
        playerRound = player;
        playerCanPlay = true;

        // Habilitar botões e clicks nas cards
        MainActivity.ChangePlayerButtonColor(BUTTON_BLUFF, context, isBluffed ? R.color.yellow : R.color.green);

        // Botão de truco
        MainActivity.playerActions.get(BUTTON_BLUFF).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (playerCanPlay && !isBluffed) {
                    isBluffed = IAPlayer.acceptBluff();

                    if (isBluffed) {
                        MainActivity.ChangePlayerButtonColor(BUTTON_BLUFF, context, R.color.yellow);
                        Toast.makeText(
                                context,
                                "O oponente aceitou o truco",
                                Toast.LENGTH_SHORT
                        ).show();
                    } else {
                        playerScore++;
                        updateScore();
                        nextTurn();

                        Toast.makeText(
                                context,
                                "O oponente recusou o truco",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void setEnemyTurn() {
        playerRound = enemy;
        Card playedCard = enemy.playCard(0);
        enemy.updateHand();
        table.addCard(playedCard);
        checkRound(false, playedCard, null);
    }

    // Turno = cada mão. Composto de até 3 rounds
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupTurn() {
        MainActivity.playerActions.get(BUTTON_OK).setOnClickListener(NoAction());

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
    // Determina se será criado uma nova rodada ou um novo turno (3 rodadas)
    void createNewRound() {
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
                MainActivity.ChangePlayerButtonColor(BUTTON_OK, context, R.color.blue);
            } else if (rounds.get(rounds.size() - 1).winner instanceof RealPlayer) {
                MainActivity.ChangePlayerButtonColor(BUTTON_OK, context, R.color.green);
            } else {
                MainActivity.ChangePlayerButtonColor(BUTTON_OK, context, R.color.purple);
            }

            MainActivity.playerActions.get(BUTTON_OK).setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    MainActivity.ChangePlayerButtonColor(BUTTON_OK, context, android.R.color.darker_gray);
                    table.clean();
                    rounds.add(new Round(powerfulCard, instance));
                    checkRound(false, null, playerRound);
                    playerCanPlay = true;
                    MainActivity.playerActions.get(BUTTON_OK).setOnClickListener(NoAction());
                }
            });
        } else {
            int scoreToAdd = isBluffed ? 3 : 1;

            if (drawRounds < 3) {
                if (playerWinner) playerScore += scoreToAdd;
                else enemyScore += scoreToAdd;

                if (playerWinner) {
                    MainActivity.ChangePlayerButtonColor(BUTTON_OK, context, R.color.green);
                } else {
                    MainActivity.ChangePlayerButtonColor(BUTTON_OK, context, R.color.purple);
                }
            } else {
                MainActivity.ChangePlayerButtonColor(BUTTON_OK, context, R.color.blue);
            }

            updateScore();

            MainActivity.playerActions.get(BUTTON_OK).setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    nextTurn();
                    playerCanPlay = true;
                    MainActivity.playerActions.get(BUTTON_OK).setOnClickListener(NoAction());
                }
            });
        }
    }

    private static View.OnClickListener NoAction() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void nextTurn() {
        setupTurn();
        rounds.add(new Round(powerfulCard, this));

        if (playerTurn) {
           setPlayerTurn();
        } else {
            setEnemyTurn();
        }

    }

    private void setTurnedCard() {
        turnedCard = deck.draw(1).get(0);
        MainActivity.turnedCard.setImageResource(turnedCard.image);
        powerfulCard = turnedCard.digit.next();
    }

    private void updateScore() {
        MainActivity.playerScore.setText(playerScore + "/12");
        MainActivity.enemyScore.setText(enemyScore + "/12");

        if (playerScore >= 12) {
            winner = player;
        } else if (enemyScore >= 12) {
            winner = enemy;
        }

        if (winner != null) {
            Intent intent = new Intent(context, MatchResultActivity.class);
            intent.putExtra("PLAYER_WON", winner instanceof RealPlayer);
            context.startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void checkRound(Boolean playerCard, Card card, Player playerRound) {
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
