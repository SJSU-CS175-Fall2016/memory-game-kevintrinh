package com.longtrinh.memorygame;

/**
 * Created by Lucky on 9/17/2016.
 */
public class MemorySingleton {
    private static MemorySingleton instance;
    private MemorySingleton(){

    }

    public boolean gameInProgress;
    public int[] gameGrid;
    public boolean[] solvedGrid;
    public int points;

    public static MemorySingleton getInstance(){
        if(instance == null){
            instance = new MemorySingleton();
        }
        return instance;
    }
}
