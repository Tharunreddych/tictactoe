package com.tharun.tharunreddychinthala.tictactoe;

import android.content.Context;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * TicTacToeBoard which will represent the GridLayoutBoard in the App.
 */
class TicTacToeBoard {

    private static final String GAME_OVER_MESSAGE_SKELITON = "Game Over!!! %s Please click restart.";
    private static final String WINNER_MESSAGE_SKELITION = "Winnier is %s.";
    private static final String DRAW_MESSAGE_SKELITION = "Its a DRAW!!!";

    private static final int winningPositions[][] =
            {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    private ElementState previousElementState = ElementState.O;
    private ElementState winnerElementState = ElementState.EMPTY;

    private TextView xWinsCountTextView;
    private TextView oWinsCountTextView;
    private TextView drawsCountTextView;
    private int xWins = 0;
    private int oWins = 0;
    private int draws = 0;

    private int currentPositions[] = {ElementState.EMPTY.getState(),
            ElementState.EMPTY.getState(), ElementState.EMPTY.getState(), ElementState.EMPTY.getState(),
            ElementState.EMPTY.getState(), ElementState.EMPTY.getState(), ElementState.EMPTY.getState(),
            ElementState.EMPTY.getState(), ElementState.EMPTY.getState()};
    private boolean isThereAWinner;
    private boolean isGameDraw;

    TicTacToeBoard(TextView xWinsCountTextView, TextView oWinsCountTextView, TextView drawsCountTextView) {
        this.xWinsCountTextView = xWinsCountTextView;
        this.oWinsCountTextView = oWinsCountTextView;
        this.drawsCountTextView = drawsCountTextView;
    }

    /**
     * Will fill the position in the tictactoe board with the given state.
     * @param position position on the board.
     * @param gridLayout layout of the board.
     * @param applicationContext coontext.
     */
    void fillPosition(int position, GridLayout gridLayout, Context applicationContext) {
        if (isGameOver(applicationContext)) {
            return;
        }

        if (position > currentPositions.length) {
            throw new IllegalArgumentException("Given position is greater than positions allowed.");
        }

        if (currentPositions[position] == 2) {
            fillGivenPosition(position, gridLayout);
        }
        isGameOver(applicationContext);
    }

    /**
     * Will fill the current position based on the previous element filled.
     */
    private void fillGivenPosition(int position, GridLayout gridLayout) {
        switch (previousElementState) {
            case X:
                currentPositions[position] = ElementState.O.getState();
                ((ImageView)gridLayout.getChildAt(position)).setImageResource(R.drawable.tic_tac_toe_o);
                previousElementState = ElementState.O;
                break;
            case O:
                currentPositions[position] = ElementState.X.getState();
                ((ImageView)gridLayout.getChildAt(position)).setImageResource(R.drawable.tic_tac_toe_x);
                previousElementState = ElementState.X;
                break;
            default:
                throw new IllegalArgumentException("Illegal state previousElementState must be either O,X");
        }
    }

    public boolean isGameOver(Context applicationContext) {
        if (isThereAWinner || isGameDraw || isThereAWinner() || isGameDraw()) {
            displayGameOverMessage(applicationContext);
            return true;
        }
        return false;
    }

    private boolean isThereAWinner() {
        for (int[] winningPosition : winningPositions) {
            isThereAWinner = areAllSame(currentPositions[winningPosition[0]],
                    currentPositions[winningPosition[1]], currentPositions[winningPosition[2]])
                    // Check to see if the board is not empty.
                    && !areAllSame(currentPositions[winningPosition[0]], ElementState.EMPTY.getState());
            if (isThereAWinner) {
                winnerElementState = ElementState.getElementState(currentPositions[winningPosition[0]]);
                increaseCount();
                return isThereAWinner;
            }
        }

        return isThereAWinner;
    }

    private boolean isGameDraw() {
        isGameDraw = true;
        for (int position : currentPositions) {
            if (position == ElementState.EMPTY.getState()) {
                isGameDraw = false;
                return isGameDraw;
            }
        }
        increaseCount();
        return isGameDraw;
    }

    private void displayGameOverMessage(Context applicationContext) {
        Toast toast = Toast.makeText(applicationContext, createGameOverMessage(), Toast.LENGTH_SHORT);
        toast.show();
    }

    private String createGameOverMessage() {
        if (isGameDraw) {
            return String.format(GAME_OVER_MESSAGE_SKELITON, DRAW_MESSAGE_SKELITION);
        }
        return String.format(GAME_OVER_MESSAGE_SKELITON, String.format(WINNER_MESSAGE_SKELITION, winnerElementState));
    }

    /**
     * Will increase count of xWins, oWins, draws
     */
    private void increaseCount() {
        switch (winnerElementState) {
            case X:
                xWins++;
                xWinsCountTextView.setText(Integer.toString(xWins));
                break;
            case O:
                oWins++;
                oWinsCountTextView.setText(Integer.toString(oWins));
                break;
            case EMPTY:
                draws++;
                drawsCountTextView.setText(Integer.toString(draws));
                break;
            default:
                // Do nothing.
        }
    }

    /**
     * Will clear all the existing values and restarts the game.
     * @param ticTacToeBox The Grid Layout of TicTacToe Box.
     */
    void restartGame(GridLayout ticTacToeBox) {
        previousElementState = ElementState.O;

        for (int child = 0; child < ticTacToeBox.getChildCount(); child++) {
            ((ImageView) ticTacToeBox.getChildAt(child)).setImageResource(0);
            currentPositions[child] = ElementState.EMPTY.getState();
        }

        winnerElementState = ElementState.EMPTY;
        isGameDraw = false;
        isThereAWinner = false;
    }

    private boolean areAllSame(int... positions) {
        for (int position : positions) {
            if (positions[0] != position) {
                return false;
            }
        }
        return true;
    }

    List<Integer> vacantPositions() {
        List<Integer> vacantPositions = new ArrayList<>();
        for (int i = 0; i < currentPositions.length; i++) {
            if (currentPositions[i] == ElementState.EMPTY.getState()) {
                vacantPositions.add(i);
            }
        }
        return vacantPositions;
    }
}
