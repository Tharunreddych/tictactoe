package com.tharun.tharunreddychinthala.tictactoe;

import android.widget.GridLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Bot player who plays against human.
 */
public class BotPlayer {

    // Symbol that bot player needs to use.
    private final ElementState botPlayerSymbol;

    BotPlayer(ElementState botPlayerSymbol) {
        this.botPlayerSymbol = botPlayerSymbol;
    }
    /**
     * Bot player chance.
     *
     * Will get an open spot and randomly fill that spot.
     */
    public void play(GridLayout ticTacToeBox) {
        Integer randomInt = spotToPlay(ticTacToeBox);

        ImageView botImageView = (ImageView) ticTacToeBox.getChildAt(randomInt);

        switch (botPlayerSymbol) {
            case O:
                botImageView.setImageResource(R.drawable.tic_tac_toe_o);
                break;
            case X:
                botImageView.setImageResource(R.drawable.tic_tac_toe_x);
                break;
            default:
                //Do Nothing.
        }

    }

    private Integer spotToPlay(GridLayout gridLayout) {
        List<Integer> emptySpots = getEmptySpots(gridLayout);

        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(emptySpots.size());

        return emptySpots.get(index);
    }

    private List<Integer> getEmptySpots(GridLayout gridLayout) {
        List<Integer> emptySpots = new ArrayList<>();
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            if (((ImageView) gridLayout.getChildAt(i)).getDrawable() == null) {
                emptySpots.add(i);
            }
        }

        return emptySpots;
    }
}
