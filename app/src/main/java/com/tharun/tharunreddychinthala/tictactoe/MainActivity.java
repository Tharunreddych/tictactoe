package com.tharun.tharunreddychinthala.tictactoe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int MAX_ROWS = 3;
    public static final int MAX_COLUMNS = 3;
    public static final int FIRST_ROW = 0;
    public static final int FIRST_COLUMN = 0;
    public static final String GAME_OVER_MESSAGE_SKELITON = "Game Over!!! %s Please click restart.";
    public static final String WINNER_MESSAGE_SKELITION = "Winnier is %s.";
    public static final String DRAW_MESSAGE_SKELITION = "Its a DRAW!!!";
    private ElementState previousElementState = ElementState.O;
    private ElementState winnerElementState = ElementState.EMPTY;

    private BotPlayer botPlayer = new BotPlayer(previousElementState);
    private TicTacToeBoard ticTacToeBoard;


    private int xWins = 0;
    private int oWins = 0;
    private int draws = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ticTacToeBoard = new TicTacToeBoard((TextView) findViewById(R.id.xWinsCount),
                (TextView) findViewById(R.id.oWinsCount),
                (TextView) findViewById(R.id.drawCount));
    }

    /**
     * Fills the position which the user chose and will let the bot play only if user play was a success.
     *
     * @param view Image View of the position user chose.
     */
    public void dropIn(View view) {
        GridLayout ticTacToeBox = (GridLayout) findViewById(R.id.box);
        Context applicationContext = getApplicationContext();

        // Human Play.
        int position = Integer.parseInt(view.getTag().toString());
        boolean userPlaySuccess = ticTacToeBoard.fillPosition(position, ticTacToeBox, applicationContext);

        if (!ticTacToeBoard.isGameOver(applicationContext) && userPlaySuccess) {
            // Bot Play.
            ticTacToeBoard.fillPosition(botPlayer.getPosition(
                    ticTacToeBoard.vacantPositions()), ticTacToeBox, applicationContext);
        }
    }

    /**
     * Will clear all the existing values and restarts the game.
     * @param view button view.
     */
    public void restartGame(View view) {
        GridLayout ticTacToeBox = (GridLayout) findViewById(R.id.box);
        ticTacToeBoard.restartGame(ticTacToeBox);
    }


    private void deadCodeForExamples() {
//        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.sound);
//        final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//
//        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
//        seekBar.setMax(maxVolume);
//        seekBar.setProgress(currentVolume);
//
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
//
//                mediaPlayer.seekTo(progress);
//
//                mediaPlayer.getDuration();
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
    }
}
