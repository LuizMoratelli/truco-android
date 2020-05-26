package br.com.luizmoratelli.truco;

import android.content.Context;
import android.os.Build;

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
    public static Boolean playerRound = false;
    private static Boolean isBluffed;
    private static int playerScore = 0;
    private static int enemyScore = 0;
    private static ArrayList<Round> rounds = new ArrayList<Round>();

    public Game(Context context) {
        this.context = context;
    }

    public void setPlayerTurn() {
        playerTurn = true;
        playerRound = true;
        // Habilitar botões e clicks nas cards
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setEnemyTurn() {
        playerTurn = false;
        playerRound = false;
        Card playedCard = enemy.playCard(0);
        enemy.updateHand();
        table.addCard(playedCard);
        checkRound(false, playedCard);
        // Desabilitar botões e clicks do player, esperar uns 2~5 segundos e fazer uma jogada.
    }

    private void setupTurn() {
        deck = new Deck(context);

        player = new RealPlayer(deck.draw(initialCards), this);
        enemy = new IAPlayer(deck.draw(initialCards), this);
        table = new Table();
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
            } else if (rounds.get(0).draw == true && rounds.get(1).draw == false) {
                // Ganhou o rounds.get(1).winner;
                createNewRound = false;
                playerWinner = rounds.get(1).winner instanceof RealPlayer;
            }
        } else if (rounds.size() == 3) {
            int playerRoundsWinned = (int) rounds.stream().filter(round -> round.winner == player).count();
            int drawRounds = (int) rounds.stream().filter(round -> round.draw).count();

            if (drawRounds == 3) {
                // Empatou todos, ngm ganha ponto
            } else if (playerRoundsWinned >= 2) {
                // Player ganhou
                playerWinner = true;
            } else {
                // Enemy ganhou
            }
        }
        // Se alguem ganha tem que dar os pontos, e cada round e quando acabar tem que definir os próximos que começarão

        if (createNewRound) {
            playerRound = rounds.get(rounds.size() - 1).winner instanceof RealPlayer;
            rounds.add(new Round(powerfulCard, this));
        } else {
            int scoreToAdd = isBluffed ? 3 : 1;
            if (playerWinner) playerScore += scoreToAdd;
            else enemyScore += scoreToAdd;

            updateScore();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void nextTurn() {
        int playerWins = 0;
        Boolean isBluffed = false;
        setupTurn();
        rounds.add(new Round(powerfulCard, this));

        if (playerTurn) {
           setPlayerTurn();
        } else {
            setEnemyTurn();
        }

        playerTurn = !playerTurn;

        // Player Joga (Truca, Corre, Aceita ou joga Card)
            // IA Joga (Mesmo)
            // Compara quem venceu e atribui os pontos
            // Resetar truco a cada "Rodada"
        //}

    }

    // Comparar a última carta de jogar e determinar qual ganhou, atribuir o direito de começar ao ganhador do round anterior
    // No fim de 3 rounds, precisa recomeçar as cards, e atribuir os pontos, considerando ou não truco;

    public void setTurnedCard() {
        turnedCard = deck.draw(1).get(0);
        MainActivity.turnedCard.setImageResource(turnedCard.image);
        powerfulCard = turnedCard.digit.next();
    }

    private void updateScore() {
        MainActivity.playerScore.setText(playerScore + "/12");
        MainActivity.enemyScore.setText(enemyScore + "/12");

        if (playerScore >= 12) {
            // Player ganhou mensagem
            winner = player;
        } else if (enemyScore >= 12) {
            winner = enemy;
        } else {
            //nextTurn();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void checkRound(Boolean playerCard, Card card) {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if (playerCard) {
                    rounds.get(rounds.size() - 1).setPlayerCard(card);
                } else {
                    rounds.get(rounds.size() - 1).setEnemyCard(card);
                }

                rounds.get(rounds.size() - 1).check();
            }
        }, 5000);

    }
}
