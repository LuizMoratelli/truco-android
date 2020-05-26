package br.com.luizmoratelli.truco;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

// Provável que precis de Game -> Rodada -> Turno (Ou fazer no turno um for de 3 jogadas)
// Acho que vai ter que seprar pra conseguir salvar os clicks e gerar as ações pelos rounds
public class Game {
    private final Context context;
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
    private static Boolean isBluffed = false;
    private static int playerScore = 0;
    private static int enemyScore = 0;
    private static ArrayList<Round> rounds = new ArrayList<Round>();

    public Game(Context context) {
        this.context = context;
    }

    public void setPlayerTurn() {
        playerRound = player;
        // Habilitar botões e clicks nas cards
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

    private void setupTurn() {
        deck = new Deck(context);
        player = new RealPlayer(deck.draw(initialCards), this);
        enemy = new IAPlayer(deck.draw(initialCards), this);
        table = new Table();
        rounds = new ArrayList<Round>();
        playerTurn = !playerTurn;
        setTurnedCard();
        player.updateHand();
        enemy.updateHand();
        table.clean();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void createNewRound() {
        Boolean createNewRound = true;
        Boolean playerWinner = false;

        if (rounds.size() == 2) {
            if (rounds.get(0).winner == player && rounds.get(1).winner == player) {
                // Acabou Player ganhou
                createNewRound = false;
                playerWinner = true;
            } else if (rounds.get(0).winner == enemy && rounds.get(1).winner == enemy) {
                // Acabou Enemy ganhou
                createNewRound = false;
            } else if (rounds.get(0).draw && !rounds.get(1).draw) {
                // Ganhou o rounds.get(1).winner;
                createNewRound = false;
                playerWinner = rounds.get(1).winner instanceof RealPlayer;
            }
        } else if (rounds.size() == 3) {
            int playerRoundsWinned = (int) rounds.stream().filter(round -> round.winner == player).count();
            int drawRounds = (int) rounds.stream().filter(round -> round.draw).count();

            if (drawRounds == 3) {
                // Empatou todos, ngm ganha ponto
                createNewRound = false;
            } else if (playerRoundsWinned >= 2) {
                // Player ganhou
                createNewRound = false;
                playerWinner = true;
            } else {
                // Enemy ganhou
                createNewRound = false;
            }
        }

        if (createNewRound) {
            playerRound = rounds.get(rounds.size() - 1).winner;
            if (playerRound == null) {
                playerRound = playerTurn ? player : enemy;
            }
            rounds.add(new Round(powerfulCard, this));

            checkRound(false, null, playerRound);
        } else {
            int scoreToAdd = isBluffed ? 3 : 1;
            if (playerWinner) playerScore += scoreToAdd;
            else enemyScore += scoreToAdd;

            updateScore();
            nextTurn();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void nextTurn() {
        Boolean isBluffed = false;
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

        if (playerScore >= 12) {
            winner = player;
            Toast.makeText(
                    context,
                    "Você ganhou a partida!",
                    Toast.LENGTH_LONG
            ).show();

        } else if (enemyScore >= 12) {
            winner = enemy;
            Toast.makeText(
                    context,
                    "Você perdeu a partida!",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void checkRound(Boolean playerCard, Card card, Player playerRound) {
        //Timer t = new Timer();
        //t.schedule(new TimerTask() {
        //    @Override
        //    public void run() {
                if (card != null) {
                    if (playerCard) {
                        rounds.get(rounds.size() - 1).setPlayerCard(card);
                    } else {
                        rounds.get(rounds.size() - 1).setEnemyCard(card);
                    }
                }
//2
                String texto = rounds.get(rounds.size() - 1).check(playerRound);

                if (texto != null) {
                    Toast.makeText(
                            context,
                            texto,
                            Toast.LENGTH_SHORT
                    ).show();
                }
        //    }
        //}, 1000);

    }
}
