package com.tharun.tharunreddychinthala.tictactoe;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int MAX_ROWS = 3;
    public static final int MAX_COLUMNS = 3;
    public static final int FIRST_ROW = 0;
    public static final int FIRST_COLUMN = 0;
    public static final String GAME_OVER_MESSAGE_SKELITON = "Game Over!!! %s Please click restart.";
    public static final String WINNER_MESSAGE_SKELITION = "Winnier is %s.";
    public static final String DRAW_MESSAGE_SKELITION = "Its a DRAW!!!";
    ElementState previousElementState = ElementState.O;
    ElementState winnerElementState = ElementState.EMPTY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropIn(View view) {
        if (isGameOver()) {
            return;
        }
        ImageView currentImage = (ImageView) view;
        currentImage.setTranslationY(-1000f);
        if (getElementState(currentImage) == ElementState.X) {
            currentImage.setImageResource(R.drawable.tic_tac_toe_x);
            previousElementState = ElementState.X;
        } else if (getElementState(currentImage) == ElementState.O){
            currentImage.setImageResource(R.drawable.tic_tac_toe_o);
            previousElementState = ElementState.O;
        }
        currentImage.animate().translationYBy(1000f).setDuration(0);

        isGameOver();
    }

    /**
     * Will return the state the given imageView needs to be present.
     * @param imageView imageView
     * @return imageView desired ElementState.
     */
    private ElementState getElementState(ImageView imageView) {
        if (previousElementState == ElementState.O &&
                checkImageHasTheView(imageView, R.drawable.tic_tac_toe_x)) {
            return ElementState.X;
        } else if (previousElementState == ElementState.X &&
                checkImageHasTheView(imageView, R.drawable.tic_tac_toe_o)) {
            return ElementState.O;
        }
        return ElementState.FILLED;
    }

    private boolean checkImageHasTheView(ImageView imageView, int resource) {
        return imageView.getDrawable() == null ||
                imageView.getDrawable().getConstantState().equals(getResources().getDrawable(resource).getConstantState());
    }

    private boolean isGameOver() {
        if (!winnerElementState.equals(ElementState.EMPTY) || areAllCellsFull()) {
            displayGameOverMessage();
            return true;
        }

        GridLayout ticTacToeBox = (GridLayout) findViewById(R.id.box);
        if (checkRows(ticTacToeBox) || checkCloumns(ticTacToeBox) || checkCrosses(ticTacToeBox)) {
            displayGameOverMessage();

            return true;
        }
        return false;
    }

    private void displayGameOverMessage() {
        Toast toast = Toast.makeText(getApplicationContext(), createGameOverMessage(), Toast.LENGTH_SHORT);
        toast.show();
    }

    private String createGameOverMessage() {
        if (winnerElementState == ElementState.EMPTY) {
            return String.format(GAME_OVER_MESSAGE_SKELITON, DRAW_MESSAGE_SKELITION);
        }
        return String.format(GAME_OVER_MESSAGE_SKELITON, String.format(WINNER_MESSAGE_SKELITION, winnerElementState));
    }

    /**
     * Will check if the rows have any completion.
     */
    private boolean checkRows(GridLayout ticTacToeBox) {
        for (int row = FIRST_ROW; row < MAX_ROWS; row++) {
            ImageView imageViewRow0 = (ImageView) ticTacToeBox.getChildAt((row*3 + 0));
            ImageView imageViewRow1 = (ImageView) ticTacToeBox.getChildAt((row*3 + 1));
            ImageView imageViewRow2 = (ImageView) ticTacToeBox.getChildAt((row*3 + 2));
            if (areAllThreeEqual(imageViewRow0.getDrawable(), imageViewRow1.getDrawable(), imageViewRow2.getDrawable())) {
                //showGameCompletionLine(row);
                return true;
            }
            continue;
        }
        return false;
    }

    /**
     * Will check if the columns have any completion.
     */
    private boolean checkCloumns(GridLayout ticTacToeBox) {
        for (int column = FIRST_COLUMN; column < MAX_COLUMNS; column++) {
            ImageView imageViewColumn0 = (ImageView) ticTacToeBox.getChildAt((column + 0));
            ImageView imageViewColumn1 = (ImageView) ticTacToeBox.getChildAt((column + 3));
            ImageView imageViewColumn2 = (ImageView) ticTacToeBox.getChildAt((column + 6));
            if (areAllThreeEqual(imageViewColumn0.getDrawable(), imageViewColumn1.getDrawable(), imageViewColumn2.getDrawable())) {
                return true;
            }
            continue;
        }
        return false;
    }

    /**
     * Will check if the crosses have any completion.
     * @param ticTacToeBox
     */
    private boolean checkCrosses(GridLayout ticTacToeBox) {

        // First Cross - 0,4,8
        ImageView imageView0 = (ImageView) ticTacToeBox.getChildAt(0);
        ImageView imageView4 = (ImageView) ticTacToeBox.getChildAt(4);
        ImageView imageView8 = (ImageView) ticTacToeBox.getChildAt(8);
        if (areAllThreeEqual(imageView0.getDrawable(), imageView4.getDrawable(), imageView8.getDrawable())) {
            return true;
        }

        // First Cross - 2,4,6
        ImageView imageView2 = (ImageView) ticTacToeBox.getChildAt(2);
        ImageView imageView6 = (ImageView) ticTacToeBox.getChildAt(6);
        if (areAllThreeEqual(imageView2.getDrawable(), imageView4.getDrawable(), imageView6.getDrawable())) {
            return true;
        }

        return false;
    }

    /**
     * Checks if the given three drawables are equal. If they are equal sets the winner value as well.
     */
    private boolean areAllThreeEqual(Drawable... drawables) {
        for (Drawable drawable : drawables) {
            if (drawable == null) {
                return false;
            }
        }
        Drawable checkDrawable = drawables[0];
        for (Drawable drawable : drawables) {

            boolean sameBitMap = getBitmap(checkDrawable).sameAs(getBitmap(drawable));
            if (!checkDrawable.getConstantState().equals(drawable.getConstantState()) || !sameBitMap) {
                return false;
            }
        }
        setWinner(checkDrawable);
        return true;
    }

    private void setWinner(Drawable drawable) {
        if (drawable.getConstantState().equals(getResources().getDrawable(R.drawable.tic_tac_toe_x).getConstantState())
                || getBitmap(drawable).sameAs(getBitmap(getResources().getDrawable(R.drawable.tic_tac_toe_x)))) {
            winnerElementState = ElementState.X;
        } else {
            winnerElementState = ElementState.O;
        }
    }

    private boolean areAllCellsFull() {
        GridLayout ticTacToeBox = (GridLayout) findViewById(R.id.box);
        for (int childElement = 0; childElement < 9; childElement++) {
            ImageView imageView = (ImageView) ticTacToeBox.getChildAt(childElement);
            if (imageView.getDrawable() == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the bit map of the given drawable.
     * @param drawable drawable
     * @return bitmap of the given drawable
     */
    public static Bitmap getBitmap(Drawable drawable) {
        Bitmap result;
        if (drawable instanceof BitmapDrawable) {
            result = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            // Some drawables have no intrinsic width - e.g. solid colours.
            if (width <= 0) {
                width = 1;
            }
            if (height <= 0) {
                height = 1;
            }

            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        return result;
    }

    /**
     * Will clear all the existing values and restarts the game.
     * @param view button view.
     */
    public void restartGame(View view) {
        previousElementState = ElementState.O;
        GridLayout ticTacToeBox = (GridLayout) findViewById(R.id.box);
        for (int row = 0; row < 3; row++) {
            ImageView imageViewColumn0 = (ImageView) ticTacToeBox.getChildAt((row*3 + 0));
            imageViewColumn0.setImageDrawable(null);

            ImageView imageViewColumn1 = (ImageView) ticTacToeBox.getChildAt((row*3 + 1));
            imageViewColumn1.setImageDrawable(null);

            ImageView imageViewColumn2 = (ImageView) ticTacToeBox.getChildAt((row*3 + 2));
            imageViewColumn2.setImageDrawable(null);
        }

        winnerElementState = ElementState.EMPTY;
    }
}
