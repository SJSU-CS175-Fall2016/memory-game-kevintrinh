package com.longtrinh.memorygame;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Queue;

import java.util.Random;

/**
 * Created by Lucky on 9/17/2016.
 */
public class PlayActivity extends AppCompatActivity implements GridView.OnItemClickListener{

    private GridView playGrid;
    private TextView pointsText;

    private int points;
    private int[] playImages;

    private final int[] imageSources = {R.drawable.dog_icon, R.drawable.dog_icon_2, R.drawable.dog_icon_3,
                                        R.drawable.dog_icon_4, R.drawable.dog_icon_5, R.drawable.cat_icon,
                                        R.drawable.cat_icon_2, R.drawable.cat_icon_3, R.drawable.cat_icon_4,
                                        R.drawable.cat_icon_5, 0};

    private int[] gameGrid;
    private boolean[] solvedGrid;

    private View lastView = null;
    private int lastPosition = 0;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        playGrid = (GridView) findViewById(R.id.playGrid);
        pointsText = (TextView) findViewById(R.id.pointsText);


        /* This is only for portrait handling only */
        if(savedInstanceState != null){
            Log.d("match", "hello");
            gameGrid = savedInstanceState.getIntArray("gameGrid");
            points = savedInstanceState.getInt("points");
            solvedGrid = savedInstanceState.getBooleanArray("solvedGrid");
            reloadGame();
        }else{
            /* Let's find if our singleton has saved anything*/
            MemorySingleton singleton = MemorySingleton.getInstance();
            if(singleton.gameInProgress) {
                points = singleton.points;
                solvedGrid = singleton.solvedGrid;
                gameGrid = singleton.gameGrid;
                reloadGame();
                promptUser();
            }else{
                createNewGame();
            }


        }





    }
    protected void promptUser(){
        CharSequence colors[] = new CharSequence[] {"Yes", "No",};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Would you like to play a new game?");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
                if(which == 0){
                    createNewGame();
                }
            }
        });
        builder.show();

    }



    protected void onDestroy(){
        super.onDestroy();
        MemorySingleton singleton = MemorySingleton.getInstance();
        singleton.gameInProgress = true;
        singleton.points = points;
        singleton.solvedGrid = solvedGrid;
        singleton.gameGrid = gameGrid;
    }


    /* If gameGrid, points, and solvedGrid are saved. call this */
    protected void reloadGame(){

        for(int i=0; i<solvedGrid.length; i++){
            if(solvedGrid[i]) gameGrid[i] = 0;
        }

        ItemAdapter adapter = new ItemAdapter(this, gameGrid);
        playGrid.setAdapter(adapter);
        playGrid.setOnItemClickListener(this);
        adapter.notifyDataSetChanged();

        Log.d("match", "yes" + playGrid.getChildCount());

        updatePoints();
    }
    protected void createNewGame(){

        gameGrid = new int[(imageSources.length * 2) - 2];
        int imageCount = 0;
        for(int i=0; i<gameGrid.length; i++){
            gameGrid[i] = imageSources[imageCount++];
            if(imageCount == imageSources.length - 1) imageCount = 0;
        }
        Random rand = new Random();
        for(int i=0; i<gameGrid.length; i++){
            int index = rand.nextInt(gameGrid.length);
            int temp = gameGrid[index];
            gameGrid[index] = gameGrid[i];
            gameGrid[i] = temp;
        }

        solvedGrid = new boolean[gameGrid.length];



        ItemAdapter adapter = new ItemAdapter(this, gameGrid);
        playGrid.setAdapter(adapter);
        playGrid.setOnItemClickListener(this);


        points = 0;
        updatePoints();
    }

    protected void givePoint(){
        ++points;
        updatePoints();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putIntArray("gameGrid", gameGrid);
        savedInstanceState.putBooleanArray("solvedGrid", solvedGrid);
        savedInstanceState.putInt("points", points);

        // Always call the superclass so it can save the view hierarchy state
        Log.d("match", "called!");
        super.onSaveInstanceState(savedInstanceState);
    }

    protected void updatePoints(){
        pointsText.setText("Points: " + points);
    }


    protected void showImage(View v){
        ((ImageView)v.findViewById(R.id.hiddenSquare)).setVisibility(View.INVISIBLE);
    }
    protected void hideImage(final View v){
        new CountDownTimer(500, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                ((ImageView)v.findViewById(R.id.hiddenSquare)).setVisibility(View.VISIBLE);
            }
        }.start();

    }


    protected boolean checkImages(View one, View two){
        ImageView im1 = (ImageView) one.findViewById(R.id.showSquare);
        ImageView im2 = (ImageView) two.findViewById(R.id.showSquare);
        return im1.getTag().equals(im2.getTag());
    }

    protected void hideView(final View v){
        new CountDownTimer(500, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                v.setVisibility(View.INVISIBLE);
            }
        }.start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v,
                            int position, long id) {
        if(solvedGrid[position] == false){
            if(lastView == null){
                lastView = v;
                lastPosition = position;
                showImage(v);
            }else{
                if(lastView != v){
                    showImage(v);
                    if(checkImages(lastView, v)){
                        solvedGrid[lastPosition] = true;
                        solvedGrid[position] = true;
                        hideView(lastView);
                        hideView(v);
                        lastView = null;
                        givePoint();
                    }else{
                        hideImage(v);
                        hideImage(lastView);
                        lastView = null;
                    }

                }



            }
        }



    }
}
