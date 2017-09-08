package com.tharun.tharunreddychinthala.tictactoe;

/**
 * Created by tharunreddychinthala on 05/09/17.
 */

public enum ElementState {
    O(0),
    X(1),
    EMPTY(2),
    FILLED(3);

    private final int state;

    ElementState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    public static ElementState getElementState(int state) {
        switch (state) {
            case 0:
                return O;
            case 1:
                return X;
            case 2:
                return EMPTY;
            default:
                return FILLED;
        }
    }
}
