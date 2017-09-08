package com.tharun.tharunreddychinthala.tictactoe;

import android.widget.GridLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Bot player who plays against human.
 */
class BotPlayer {

    // Symbol that bot player needs to use.
    private final ElementState botPlayerSymbol;

    BotPlayer(ElementState botPlayerSymbol) {
        this.botPlayerSymbol = botPlayerSymbol;
    }

    /**
     * Will return the position that the bot player wants to play.
     * @param vacantPositions - vacant positions where the bot can play.
     * @return position where bot wants to play.
     */
    int getPosition(List<Integer> vacantPositions) {
        return spotToPlay(vacantPositions);
    }

    private Integer spotToPlay(List<Integer> emptySpots) {

        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(emptySpots.size());

        return emptySpots.get(index);
    }
}
